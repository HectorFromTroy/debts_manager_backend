package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.AddDebtRequest;
import com.nazipov.debts_manager.dto.DebtsAndSumDto;
import com.nazipov.debts_manager.dto.RepayDebtRequest;
import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.service.debt.*;
import com.nazipov.debts_manager.service.debtship.DebtshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class DebtController {
    private final DebtshipService debtshipService;
    private final DebtService debtService;

    @Autowired
    DebtController(DebtshipService debtshipService, DebtService debtService) {
        this.debtshipService = debtshipService;
        this.debtService = debtService;
    }

    private PageRequest getPageRequest(int page) {
        return PageRequest.of(page, 7, Sort.by("date").descending());
    }

    private SampleResponseDto<?> noAccessOnDebt() {
        return new SampleResponseDto.Builder<>()
                .setStatus(false)
                .setError("У вас нет доступа к этому долгу")
                .build();
    }

    private boolean checkAccessOnDebt(SpringUser springUser, Debtship debtship) {
        MyUser user = debtship.getUser();
        long currentUserId = springUser.getUser().getId();
        // Есть доступ, если должны мне или я должен не настоящему
        return user.getId() == currentUserId
                || (debtship.getDebtor().getId() == currentUserId
                    && user.getUsername() == null);
    }

    @GetMapping("/get_debts")
    public SampleResponseDto<?> getAllActiveDebts(
            @RequestParam(required = true) long debtorId,
            @RequestParam(required = true) boolean active,
            @RequestParam(required = true) int page,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        Optional<Debtship> debtshipOptional = debtshipService.getDebtshipByUserAndDebtorId(
                springUser.getUser().getId(),
                debtorId
        );
        if (debtshipOptional.isPresent()) {
            Debtship debtship = debtshipOptional.get();
            List<Debt> debts = null;

            if (active) {
                debts = debtService.findAllByDebtshipAndIsPaidOff(
                        debtship,
                        false,
                        getPageRequest(page)
                );
            } else {
                debts = debtService.findAllByDebtship(
                        debtship,
                        getPageRequest(page)
                );
            }

            Optional<Integer> sumOptional = debtService.getDebtsSum(debtship.getId());
            DebtsAndSumDto debtsAndSumDto = new DebtsAndSumDto(
                    debts,
                    sumOptional.orElse(0)
            );

            return new SampleResponseDto.Builder<>()
                    .setStatus(true)
                    .setData(debtsAndSumDto)
                    .build();
        } else {
            return new SampleResponseDto.Builder<>()
                    .setStatus(false)
                    .setError("Нет такого отношения")
                    .build();
        }
    }

    @PostMapping("/add_debt")
    public SampleResponseDto<?> addDebt(
            @RequestBody AddDebtRequest addDebtRequest,
            @AuthenticationPrincipal SpringUser springUser) {
        String description = addDebtRequest.getDescription();
        LocalDateTime date = addDebtRequest.getDate();
        int sum = addDebtRequest.getSum();

        List<Debt> debtsToAdd = new ArrayList<>();

        for (Debtship debtship : debtshipService.getDebtshipsByDebtorIds(
                springUser.getUser().getId(),
                addDebtRequest.getDebtorIds())
        ) {
            Debt debt = new Debt();
            debt.setDebtship(debtship);
            debt.setDescription(description);
            debt.setDate(date);
            debt.setSum(sum);
            debtsToAdd.add(debt);
        }

        debtService.saveAllDebts(debtsToAdd);
        return SampleResponseDto.statusTrue();
    }

    @PostMapping("/delete_debt")
    public SampleResponseDto<?> deleteDebt(
            @RequestBody Map<String, Long[]> payload,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        List<Debt> debtsToDelete = new ArrayList<>();
        for (Debt debt : debtService.getAllDebtsById(payload.get("debtId"))) {
            if (!checkAccessOnDebt(springUser, debt.getDebtship())) {
                return noAccessOnDebt();
            }
            debtsToDelete.add(debt);
        }
        debtService.deleteDebts(debtsToDelete);
        return SampleResponseDto.statusTrue();
    }

    @PostMapping("/repay_debt")
    public SampleResponseDto<?> repayDebt(
            @RequestBody RepayDebtRequest repayDebtRequest,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        List<Debt> debtsToRepay = new ArrayList<>();
        for (Debt debt : debtService.getAllDebtsById(repayDebtRequest.getDebtIds())) {
            if (!checkAccessOnDebt(springUser, debt.getDebtship())) {
                return noAccessOnDebt();
            }
            debt.setPaidOff(true);
            debt.setRepayDescription(repayDebtRequest.getRepayDescription());
            debt.setRepayDate(repayDebtRequest.getRepayDate());
            debtsToRepay.add(debt);
        }
        debtService.saveAllDebts(debtsToRepay);
        return SampleResponseDto.statusTrue();
    }
}
