package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.Debtship;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.DetailsService;
import com.nazipov.debts_manager.service.SpringUser;
import com.nazipov.debts_manager.service.debtship.DebtshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @GetMapping("/")
    public String authorization() {
        return "SOOQA";
    }

    @GetMapping("/login")
    public SampleResponseDto<?> login(HttpServletResponse response) {
        // if spring security allows to get here => login was successful
        response.setStatus(HttpServletResponse.SC_OK);
        SampleResponseDto<Long> responseDto = new SampleResponseDto<>();
        responseDto.setStatus(true);
        return responseDto;
    }

    private final DetailsService detailsService;
    private final DebtshipService debtshipService;

    @Autowired
    MainController(
            DetailsService detailsService,
            DebtshipService debtshipService) {
        this.detailsService = detailsService;
        this.debtshipService = debtshipService;
    }

    @GetMapping("/user")
    public SampleResponseDto<MyUser> getUser() {
        Optional<MyUser> u = detailsService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return new SampleResponseDto.Builder<MyUser>()
                .setStatus(true)
                .setData(u.get())
                .build();
    }

    @GetMapping("/get_debtors")
    public SampleResponseDto<?> getDebtors(@AuthenticationPrincipal SpringUser springUser) {
        MyUser user = springUser.getUser();
        Optional<Set<Debtship>> debtshipsOpt = debtshipService.getUserDebtorsById(user.getId());
        // even if there is no debtors, optional will contain empty set anyway
        Set<Debtship> debtships = debtshipsOpt.get();
        Set<MyUser> debtors = debtships.stream().map(Debtship::getDebtor).collect(Collectors.toSet());
        return new SampleResponseDto.Builder<Set<MyUser>>()
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

        return new SampleResponseDto.Builder<>()
                .setStatus(true)
                .build();
    }

    public static class AddDebtorRequest {
        private final int debtorId;
        private final String name;
        public AddDebtorRequest(int debtorId, String name) {
            this.debtorId = debtorId;
            this.name = name;
        }

        public int getDebtorId() {
            return debtorId;
        }
        public String getName() {
            return name;
        }
    }
}
