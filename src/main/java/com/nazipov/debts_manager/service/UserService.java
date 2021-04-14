package com.nazipov.debts_manager.service;

import com.nazipov.debts_manager.entities.User;

public interface UserService {
    User read(long id);
    User findByUsername(String name);
}
