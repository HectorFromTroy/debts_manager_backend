package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.service.debt.AddDebtRequest;
import com.nazipov.debts_manager.service.debt.DebtService;
import com.nazipov.debts_manager.service.debt.RepayDebtRequest;
import com.nazipov.debts_manager.service.debt.RepaySpecificDebt;
import com.nazipov.debts_manager.service.debtship.DebtshipService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private boolean checkNoAccessOnDebt(SpringUser springUser, Debt debt) {
        MyUser user = debt.getDebtship().getUser();
        return !user.getId()
                .equals(springUser.getUser().getId())
                && user.getUsername() != null;
    }

    @GetMapping("/get_debts")
    public SampleResponseDto<?> getAllActiveDebts(
            @RequestParam(required = true) long debtshipId,
            @RequestParam(required = true) boolean active,
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
                debts = debtship.getActiveDebts();
            } else {
                debts = debtship.getDebts();
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

        long currentUserId = springUser.getUser().getId();
        String description = addDebtRequest.getDescription();
        LocalDate date = addDebtRequest.getDate();
        int sum = addDebtRequest.getSum();

        long[] debtshipIds = addDebtRequest.getDebtship_id();

        List<Debt> debtsToAdd = new ArrayList<>();

        for (long debtshipId : debtshipIds) {
            Optional<Debtship> debtshipOptional = debtshipService
                    .getDebtshipById(debtshipId);
            if (debtshipOptional.isPresent()) {
                Debtship debtship = debtshipOptional.get();
                // мне должны или я должен не настоящему юзеру
                if (debtship.getUser().getId().equals(currentUserId)
                        || (debtship.getDebtor().getId().equals(currentUserId)
                            && debtship.getUser().getUsername() == null
                        )
                ) {
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
            } else {
                return new SampleResponseDto.Builder<>()
                        .setStatus(false)
                        .setError("Нет такого отношения между пользователем и должником")
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

            if (checkNoAccessOnDebt(springUser, debt)) {
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
            @RequestBody RepaySpecificDebt repaySpecificDebt,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        Optional<Debt> debtOptional = debtService.getDebtById(repaySpecificDebt.getDebtId());

        if (debtOptional.isPresent()) {
            Debt debt = debtOptional.get();

            if (checkNoAccessOnDebt(springUser, debt)) {
                return noAccessOnDebt();
            }

            debt.setRepaySum(debt.getSum());
            debt.setRepayDescription(repaySpecificDebt.getRepayDescription());
            debt.setRepayDate(repaySpecificDebt.getRepayDate());
            debtService.saveDebt(debt);
            return SampleResponseDto.statusTrue();
        } else {
            return noSuchDebt();
        }
    }
}
