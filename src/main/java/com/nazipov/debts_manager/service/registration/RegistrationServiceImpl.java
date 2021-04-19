package com.nazipov.debts_manager.service.registration;

import com.nazipov.debts_manager.dto.RegistrationRequest;
import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final DetailsService detailsService;

    @Autowired
    public RegistrationServiceImpl(DetailsService detailsService) {
        this.detailsService = detailsService;
    }
    public boolean register(RegistrationRequest registrationRequest) {
        MyUser user = new MyUser();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(registrationRequest.getPassword());
        user.setName(registrationRequest.getName());
        MyUser savedUser = detailsService.saveUser(user);
        return savedUser != null;
    }
}
