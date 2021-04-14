package com.nazipov.debts_manager.service.registration;

import com.nazipov.debts_manager.entities.User;
import com.nazipov.debts_manager.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final DetailsService detailsService;

    @Autowired
    public RegistrationService(DetailsService detailsService) {
        this.detailsService = detailsService;
    }
    public boolean register(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setName(registrationRequest.getName());
        User savedUser = detailsService.saveUser(user);
        return savedUser != null;
    }
}
