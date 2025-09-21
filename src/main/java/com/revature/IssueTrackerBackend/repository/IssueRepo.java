package com.revature.IssueTrackerBackend.repository;

import com.revature.IssueTrackerBackend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Long> {

}
