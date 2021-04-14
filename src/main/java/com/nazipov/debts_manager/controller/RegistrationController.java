package com.nazipov.debts_manager.controller;

import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.service.registration.RegistrationRequest;
import com.nazipov.debts_manager.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup")
    public SampleResponseDto<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        boolean isSuccessAdding = registrationService.register(registrationRequest);
        SampleResponseDto<?> responseDto = new SampleResponseDto<>();
        responseDto.setStatus(isSuccessAdding);
        return responseDto;
    }

}
