package com.revature.IssueTrackerBackend.controller;

import com.revature.IssueTrackerBackend.dto.JwtResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
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
    @PostMapping("/register")
    public ResponseEntity<AppUser> register(@RequestBody AppUser user){
        AppUser savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AppUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUsername());
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


    //💧 USER ROUTES
    // TODO: /user/profile // get mapping

    //💧 ADMIN ROUTES
    // TODO: /admin/profile get mapping
    // TODO: /admin/users get mapping
    // TODO: /admin/users{id} // delete mapping

    @GetMapping("admin/users")
    public ResponseEntity<List<AppUser>> getAllUsers(){
        List<AppUser> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
