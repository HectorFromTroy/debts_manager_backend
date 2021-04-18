package com.nazipov.debts_manager.service;

import com.nazipov.debts_manager.entities.MyUser;
import com.nazipov.debts_manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()){
            throw new UsernameNotFoundException(username + " was not found");
        }

        MyUser user = userOpt.get();
        return new SpringUser(user);
    }

    public Optional<MyUser> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<MyUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public MyUser saveUser(MyUser user) {
        Optional<MyUser> userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb.isPresent()) {
            // user already exists
            return null;
        }
        // encode password
        return userRepository.save(user);
    }

    public void deleteUser(MyUser user) {
        userRepository.delete(user);
    }
}
