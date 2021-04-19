package com.nazipov.debts_manager.service.registration;

import com.nazipov.debts_manager.dto.RegistrationRequest;

public interface RegistrationService {
    boolean register(RegistrationRequest registrationRequest);
}
