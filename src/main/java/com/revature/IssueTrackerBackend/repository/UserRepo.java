package com.revature.IssueTrackerBackend.repository;

import com.revature.IssueTrackerBackend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    List<AppUser> findAllByRole(String role);
}
