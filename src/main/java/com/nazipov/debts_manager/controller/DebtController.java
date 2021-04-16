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

import java.time.LocalDate;
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

    // TODO refactor with add_debt
    @GetMapping("/get_all_debts")
    public SampleResponseDto<List<Debt>> getAllDebts(
            @RequestParam(required = true) long debtorId,
            @AuthenticationPrincipal SpringUser springUser
    ) {
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
            response.setError("Нет такого отношения между пользователем и должником");
        }
        return response;
    }

    @GetMapping("/get_all_active_debts")
    public SampleResponseDto<?> getAllActiveDebts(
            @RequestParam(required = true) long debtshipId,
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
            List<Debt> activeDebts = debtship.getActiveDebts();
            // TODO json ignore debtship on debt entity
            return new SampleResponseDto.Builder<>()
                    .setStatus(true)
                    .setData(activeDebts)
                    .build();
        } else {
            return new SampleResponseDto.Builder<>()
                    .setStatus(false)
                    .setError("Нет такого отношения")
                    .build();
        }
    }

    // TODO refactor with get all debts
    @PostMapping("/add_debt")
    public SampleResponseDto<?> addDebt(
            @RequestBody AddDebtRequest addDebtRequest,
            @AuthenticationPrincipal SpringUser springUser) {

        long currentUserId = springUser.getUser().getId();
        String description = addDebtRequest.getDescription();
        LocalDate date = addDebtRequest.getDate();
        int sum = addDebtRequest.getSum();

        long[] debtshipIds = addDebtRequest.getDebtship_id();

        for (long debtshipId : debtshipIds) {
            Optional<Debtship> debtshipOptional = debtshipService
                    .getDebtshipById(debtshipId);
            if (debtshipOptional.isPresent()) {
                Debtship debtship = debtshipOptional.get();
                if (debtship.getUser().getId()
                        .equals(currentUserId)) {
                    Debt debt = new Debt();
                    debt.setDebtship(debtship);
                    debt.setDescription(description);
                    debt.setDate(date);
                    debt.setSum(sum);
                    debtService.saveDebt(debt);
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
