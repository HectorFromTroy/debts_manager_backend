package com.nazipov.debts_manager.service;

import com.nazipov.debts_manager.entities.MyUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SpringUser extends User {
    private MyUser user;
    public SpringUser(MyUser user) {
        super(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("USER")
        );
        this.user = user;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
