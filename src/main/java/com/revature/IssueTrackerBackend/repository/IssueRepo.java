package com.revature.IssueTrackerBackend.repository;

import com.revature.IssueTrackerBackend.entity.AppUser;
import com.revature.IssueTrackerBackend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Long> {

    List<Issue> findAllByReportedBy(AppUser user);

}
