package com.revature.IssueTrackerBackend.controller;

import com.revature.IssueTrackerBackend.dto.JwtResponse;
import com.revature.IssueTrackerBackend.dto.UserSummeryDTO;
import com.revature.IssueTrackerBackend.entity.AppUser;
import com.revature.IssueTrackerBackend.service.JwtService;
import com.revature.IssueTrackerBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //💧 PUBLIC ROUTES
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody AppUser user){
        AppUser savedUser = userService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String role = userDetails.getAuthorities().stream()
                        .findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElse("USER");

                String token = jwtService.generateToken(user.getUsername(), role);
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    //💧 USER ROUTES

    @GetMapping("/user")
    public ResponseEntity<UserSummeryDTO> getCurrentUser(Authentication authentication){
        String username = authentication.getName();
        UserSummeryDTO user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //💧 ADMIN ROUTES
    @GetMapping("/admin")
    public ResponseEntity<UserSummeryDTO> getAdminUser(Authentication authentication){
        String username = authentication.getName();
        UserSummeryDTO user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("admin/users")
    public ResponseEntity<List<UserSummeryDTO>> getAllUsers(){
        List<UserSummeryDTO> users = userService.getUsersByRole("USER");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try{
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
