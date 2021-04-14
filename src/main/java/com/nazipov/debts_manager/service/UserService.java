package com.nazipov.debts_manager.service;

import com.nazipov.debts_manager.entities.MyUser;

public interface UserService {
    MyUser read(long id);
    MyUser findByUsername(String name);
}
