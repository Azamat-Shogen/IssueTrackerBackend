package com.revature.IssueTrackerBackend.service;

import com.revature.IssueTrackerBackend.entity.AppUser;
import com.revature.IssueTrackerBackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }


    // tells Spring how to fetch users (from DB via repo)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Wrap your AppUser in UserPrincipal
        return new UserPrincipal(user);
    }

    // CRUD OPERATIONS
    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

    public AppUser saveUser(AppUser user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
