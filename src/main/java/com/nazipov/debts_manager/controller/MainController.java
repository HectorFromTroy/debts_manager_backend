package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.DetailsService;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.dto.AddDebtorRequest;
import com.nazipov.debts_manager.service.debtship.DebtshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    private final DetailsService detailsService;
    private final DebtshipService debtshipService;

    @Autowired
    MainController(
            DetailsService detailsService,
            DebtshipService debtshipService) {
        this.detailsService = detailsService;
        this.debtshipService = debtshipService;
    }

    @GetMapping("/login")
    public SampleResponseDto<?> login(
            HttpServletResponse response,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        // if spring security allows to get here => login was successful
        return new SampleResponseDto.Builder<MyUser>()
                .setStatus(true)
                .setData(springUser.getUser())
                .build();
    }

    @GetMapping("/get_debtors")
    public SampleResponseDto<?> getDebtors(@AuthenticationPrincipal SpringUser springUser) {
        MyUser user = springUser.getUser();
        List<MyUser> debtors = detailsService.getUserDebtorsById(user.getId());
        return new SampleResponseDto.Builder<List<MyUser>>()
                .setStatus(true)
                .setData(debtors)
                .build();
    }

    @PostMapping("/add_debtor")
    public SampleResponseDto<?> addDebtor(
            @RequestBody AddDebtorRequest addDebtorRequest,
            @AuthenticationPrincipal SpringUser springUser) {
        MyUser user = springUser.getUser();
        Optional<MyUser> debtorOpt = detailsService.findUserById(
                addDebtorRequest.getDebtorId()
        );
        MyUser debtor = null;
        if (debtorOpt.isPresent()) {
            debtor = debtorOpt.get();
        } else {
            debtor = new MyUser();
            debtor.setName(addDebtorRequest.getName());
            debtor = detailsService.saveUser(debtor);
        }

        debtshipService.saveDebtship(user, debtor);

        return SampleResponseDto.statusTrue();
    }

    @GetMapping("/delete_debtor")
    public SampleResponseDto<?> deletedDebtor(
            @RequestParam(required = true) long debtorId,
            @AuthenticationPrincipal SpringUser springUser
    ) {
        Optional<Debtship> debtshipOptional = debtshipService.getDebtshipByUserAndDebtorId(
                springUser.getUser().getId(),
                debtorId
        );

        if (debtshipOptional.isPresent()) {
            Debtship debtship = debtshipOptional.get();
            debtshipService.deleteDebtship(debtship);

            // ???????? ?????????????? ???? ?????????????????? ????????????????????????, ???? ?? ?????? ??????????????
            MyUser debtor = debtship.getDebtor();
            if (debtor.getUsername() == null) {
                detailsService.deleteUser(debtor);
            }

            return SampleResponseDto.statusTrue();
        } else {
            return new SampleResponseDto.Builder<>()
                    .setStatus(false)
                    .setError("?????? ???????????? ??????????????????")
                    .build();
        }
    }
}
