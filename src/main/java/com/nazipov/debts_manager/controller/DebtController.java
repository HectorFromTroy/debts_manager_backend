package com.nazipov.debts_manager.controller;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    private SampleResponseDto<?> noAccessOnDebt() {
        return new SampleResponseDto.Builder<>()
                .setStatus(false)
                .setError("У вас нет доступа к этому долгу")
                .build();
    }

    private SampleResponseDto<?> noSuchDebt() {
        return new SampleResponseDto.Builder<>()
                .setStatus(false)
                .setError("Нет такого долга")
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
            @RequestParam(required = true) long debtshipId,
            @RequestParam(required = true) boolean active,
            @RequestParam(required = true) int page,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        Optional<Debtship> debtshipOptional = debtshipService.getDebtshipById(debtshipId);
        if (debtshipOptional.isPresent()) {
            Debtship debtship = debtshipOptional.get();
            if (!debtship.getUser().getId().equals(springUser.getUser().getId())) {
                return new SampleResponseDto.Builder<>()
                        .setStatus(false)
                        .setError("Отношение не принадлежит пользователю")
                        .build();
            }

            List<Debt> debts = null;

            if (active) {
                debts = debtService.findAllByDebtshipAndAndIsPaidOff(
                        debtship,
                        false,
                        PageRequest.of(page, 2, Sort.by("date").descending())
                );
            } else {
                debts = debtService.findAllByDebtship(
                        debtship,
                        PageRequest.of(page, 2, Sort.by("date").descending())
                );
            }

            return new SampleResponseDto.Builder<>()
                    .setStatus(true)
                    .setData(debts)
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
        LocalDate date = addDebtRequest.getDate();
        int sum = addDebtRequest.getSum();

        List<Debt> debtsToAdd = new ArrayList<>();

        for (Debtship debtship : debtshipService.getAllDebtshipById(addDebtRequest.getDebtship_id())) {
            // мне должны или я должен не настоящему юзеру
            if (checkAccessOnDebt(springUser, debtship)) {
                Debt debt = new Debt();
                debt.setDebtship(debtship);
                debt.setDescription(description);
                debt.setDate(date);
                debt.setSum(sum);
                debtsToAdd.add(debt);
            } else {
                return new SampleResponseDto.Builder<>()
                        .setStatus(false)
                        .setError("Одно или несколько отношений не принадлежат пользователю")
                        .build();
            }
        }

        debtService.saveAllDebts(debtsToAdd);
        return SampleResponseDto.statusTrue();
    }

    @GetMapping("/delete_debt")
    public SampleResponseDto<?> deleteDebt(
            @RequestParam(required = true) long debtId,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        Optional<Debt> debtOptional = debtService.getDebtById(debtId);

        if (debtOptional.isPresent()) {
            Debt debt = debtOptional.get();
            if (!checkAccessOnDebt(springUser, debt.getDebtship())) {
                return noAccessOnDebt();
            }
            debtService.deleteDebt(debt);
            return SampleResponseDto.statusTrue();
        } else {
            return noSuchDebt();
        }
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
