package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debt;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.service.debt.AddDebtRequest;
import com.nazipov.debts_manager.service.debt.DebtService;
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
        MyUser user = springUser.getUser();
        Optional<Debtship> debtshipOptional = debtshipService
                .getDebtshipById(addDebtRequest.getDebtship_id());
        if (debtshipOptional.isPresent()) {
            Debtship debtship = debtshipOptional.get();
            if (debtship.getUser().getId()
                    .equals(user.getId())) {
                Debt debt = new Debt();
                debt.setDebtship(debtship);
                debt.setDescription(addDebtRequest.getDescription());
                debt.setDate(addDebtRequest.getDate());
                debt.setSum(addDebtRequest.getSum());
                debtService.saveDebt(debt);
                return new SampleResponseDto.Builder<>()
                        .setStatus(true)
                        .build();
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
}
