package com.revature.IssueTrackerBackend.service;

import com.revature.IssueTrackerBackend.dto.UserSummeryDTO;
import com.revature.IssueTrackerBackend.entity.AppUser;
import com.revature.IssueTrackerBackend.exceptions.InvalidInputException;
import com.revature.IssueTrackerBackend.exceptions.UserAlreadyExistsException;
import com.revature.IssueTrackerBackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserSummeryDTO> getAllUsers() {
        List<AppUser> users = userRepo.findAll();

        return users.stream()
                .map(user -> new UserSummeryDTO(
                        user.getId(), user.getUsername(), user.getRole()
                )).collect(Collectors.toList());
    }

    public AppUser saveUser(AppUser user){
        if (userRepo.existsByUsername(user.getUsername())){
            throw new UserAlreadyExistsException("User with username '" + user.getUsername() + "' already exists.");
        }
        if (user.getPassword().length() < 4) {
            throw new InvalidInputException("Invalid password");
        }
        // can check for input validations with different exceptions
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public UserSummeryDTO getUserByUsername(String username) {
        AppUser appUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserSummeryDTO dto = new UserSummeryDTO();
        dto.setId(appUser.getId());
        dto.setUsername(appUser.getUsername());
        dto.setRole(appUser.getRole());

        return dto;
    }

    public List<UserSummeryDTO> getUsersByRole(String role){
        List<AppUser> users = userRepo.findAllByRole(role);
        return users.stream()
                .map(user -> new UserSummeryDTO(
                        user.getId(), user.getUsername(), user.getRole()
                )).collect(Collectors.toList());
    }

    public void deleteUserById(long id) {
        AppUser user = userRepo.findById(id).orElseThrow(
                () -> new RuntimeException("User with ID: " + id + " not found")
        );
        // can't delete an admin user
        if ("ADMIN".equals(user.getRole())){
            throw new RuntimeException("Cannot delete a user with role ADMIN");
        }

        userRepo.deleteById(id);

    }
}
