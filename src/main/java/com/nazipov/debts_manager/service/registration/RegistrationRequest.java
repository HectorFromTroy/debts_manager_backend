package com.nazipov.debts_manager.service.registration;

public class RegistrationRequest {
    private final String username;
    private final String password;
    private final String name;

    public RegistrationRequest(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
