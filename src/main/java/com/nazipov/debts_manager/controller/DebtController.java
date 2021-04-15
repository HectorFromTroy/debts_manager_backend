package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.service.debt.AddDebtRequest;
import com.nazipov.debts_manager.service.debt.DebtService;
import com.nazipov.debts_manager.service.debt.RepayDebtRequest;
import com.nazipov.debts_manager.service.debtship.DebtshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
                .setError("You have no permits on this debt")
                .build();
    }

    private SampleResponseDto<?> noSuchDebt() {
        return new SampleResponseDto.Builder<>()
                .setStatus(false)
                .setError("No such debt")
                .build();
    }

    private boolean checkNoAccessOnDebt(SpringUser springUser, Debt debt) {
        MyUser user = debt.getDebtship().getUser();
        return !user.getId()
                .equals(springUser.getUser().getId())
                && user.getUsername() != null;
    }

    // TODO refactor with add_debt
    @GetMapping("/get_all_debts")
    public SampleResponseDto<List<Debt>> getAllDebts(
            @RequestParam(required = true) long debtorId,
            @AuthenticationPrincipal SpringUser springUser) {
        MyUser user = springUser.getUser();
        // mb get debtship by id and then check user id
        Optional<Debtship> debtshipOptional = debtshipService
                .getDebtshipByUserAndDebtorId(user.getId(), debtorId);
        boolean isPresent = debtshipOptional.isPresent();
        SampleResponseDto<List<Debt>> response = new SampleResponseDto<>();
        response.setStatus(isPresent);
        if (isPresent) {
            List<Debt> debts = debtshipOptional.get().getDebts();
            response.setData(debts);
        } else {
            response.setError("No such relation between user and debtor");
        }
        return response;
    }

    // TODO refactor with get all debts
    @PostMapping("/add_debt")
    public SampleResponseDto<?> addDebt(
            @RequestBody AddDebtRequest addDebtRequest,
            @AuthenticationPrincipal SpringUser springUser) {
        Optional<Debtship> debtshipOptional = debtshipService
                .getDebtshipById(addDebtRequest.getDebtship_id());
        if (debtshipOptional.isPresent()) {
            Debtship debtship = debtshipOptional.get();
            if (debtship.getUser().getId()
                    .equals(springUser.getUser().getId())) {
                Debt debt = new Debt();
                debt.setDebtship(debtship);
                debt.setDescription(addDebtRequest.getDescription());
                debt.setDate(addDebtRequest.getDate());
                debt.setSum(addDebtRequest.getSum());
                debtService.saveDebt(debt);
                return SampleResponseDto.statusTrue();
            } else {
                return new SampleResponseDto.Builder<>()
                        .setStatus(false)
                        .setError("Not current users relation")
                        .build();
            }
        } else {
            return new SampleResponseDto.Builder<>()
                    .setStatus(false)
                    .setError("No such relation between user and debtor")
                    .build();
        }
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
            // TODO static true response factory
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
        Optional<Debt> debtOptional = debtService.getDebtById(repayDebtRequest.getDebtId());

        if (debtOptional.isPresent()) {
            Debt debt = debtOptional.get();

            if (checkNoAccessOnDebt(springUser, debt)) {
                return noAccessOnDebt();
            }

            debt.setRepaySum(debt.getSum());
            debt.setRepayDescription(repayDebtRequest.getRepayDescription());
            debt.setRepayDate(repayDebtRequest.getRepayDate());
            debtService.saveDebt(debt);
            return SampleResponseDto.statusTrue();
        } else {
            return noSuchDebt();
        }
    }
}