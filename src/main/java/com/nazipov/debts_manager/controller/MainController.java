package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.entities.User;
import com.nazipov.debts_manager.service.DetailsService;
import com.nazipov.debts_manager.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

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

    @Autowired
    MainController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @GetMapping("/user")
    public SampleResponseDto<User> getUser() {
        Optional<User> u = detailsService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return new SampleResponseDto.Builder<User>()
                .setStatus(true)
                .setData(u.get())
                .build();
    }

    @PostMapping("/add_debtor")
    public SampleResponseDto<?> addDebtor(
            @RequestBody AddDebtorRequest addDebtorRequest) {
        long currentUserId = detailsService.getCurrentUserId();
        // create dumb user if not real
//        int userId = addDebtorRequest.getUserId();
//        User savedUser = null;
//        if (userId == 0) {
//            User user = new User();
//            user.setName(addDebtorRequest.getName());
//            savedUser = detailsService.saveUser(user);
//        }
//        if (savedUser == null) {
//
//        }
        // add in debtship
        return new SampleResponseDto<>();
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
