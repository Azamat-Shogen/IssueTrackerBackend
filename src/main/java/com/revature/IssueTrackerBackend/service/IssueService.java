package com.revature.IssueTrackerBackend.service;

import com.revature.IssueTrackerBackend.dto.IssueSummaryDTO;
import com.revature.IssueTrackerBackend.entity.AppUser;
import com.revature.IssueTrackerBackend.entity.Issue;
import com.revature.IssueTrackerBackend.repository.IssueRepo;
import com.revature.IssueTrackerBackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final IssueRepo issueRepo;
    private final UserRepo userRepo;

    @Autowired
    public IssueService(IssueRepo issueRepo, UserRepo userRepo) {
        this.issueRepo = issueRepo;
        this.userRepo = userRepo;
    }

    public IssueSummaryDTO createIssue(Issue issue, String username) {
        AppUser reporter = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        issue.setReportedBy(reporter);
        if (issue.getStatus() == null){
            issue.setStatus("PENDING");
        }
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());

        Issue savedIssue = issueRepo.save(issue);
        // Map to DTO
        IssueSummaryDTO dto = new IssueSummaryDTO();
        dto.setId(savedIssue.getId());
        dto.setTitle(savedIssue.getTitle());
        dto.setDescription(savedIssue.getDescription());
        dto.setStatus(savedIssue.getStatus());
        dto.setCreatedAt(savedIssue.getCreatedAt());
        dto.setUpdatedAt(savedIssue.getUpdatedAt());
        dto.setReportedByUsername(savedIssue.getReportedBy().getUsername());

        return dto;
    }

    public List<IssueSummaryDTO> getIssuesByUsername(String username) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Issue> issues = issueRepo.findAllByReportedBy(user);

        return issues.stream().map(issue -> {
            IssueSummaryDTO dto = new IssueSummaryDTO();
            dto.setId(issue.getId());
            dto.setTitle(issue.getTitle());
            dto.setDescription(issue.getDescription());
            dto.setStatus(issue.getStatus());
            dto.setCreatedAt(issue.getCreatedAt());
            dto.setUpdatedAt(issue.getUpdatedAt());
            dto.setReportedByUsername(issue.getReportedBy().getUsername());
            return dto;
        }).collect(Collectors.toList());

    }

    public IssueSummaryDTO getIssueById(long id) {
        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue with ID " + id + " not found"));

        // Map to DTO
        IssueSummaryDTO dto = new IssueSummaryDTO();
        dto.setId(issue.getId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setStatus(issue.getStatus());
        dto.setCreatedAt(issue.getCreatedAt());
        dto.setUpdatedAt(issue.getUpdatedAt());
        dto.setReportedByUsername(issue.getReportedBy().getUsername());

        return dto;
    }

    public List<IssueSummaryDTO> getAllIssues() {
        List<Issue> issues = issueRepo.findAll();

        return issues.stream().map(issue -> {
            IssueSummaryDTO dto = new IssueSummaryDTO();
            dto.setId(issue.getId());
            dto.setTitle(issue.getTitle());
            dto.setDescription(issue.getDescription());
            dto.setStatus(issue.getStatus());
            dto.setCreatedAt(issue.getCreatedAt());
            dto.setUpdatedAt(issue.getUpdatedAt());
            dto.setReportedByUsername(issue.getReportedBy().getUsername());
            return dto;
        }).collect(Collectors.toList());
    }

    public IssueSummaryDTO updateIssueStatus(long id, String status) {
        Issue issue = issueRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue with ID " + id + " not found"));
        issue.setStatus(status);
        issue.setUpdatedAt(LocalDateTime.now());

        Issue updatedIssue = issueRepo.save(issue);
        // Map to DTO
        IssueSummaryDTO dto = new IssueSummaryDTO();
        dto.setId(updatedIssue.getId());
        dto.setTitle(updatedIssue.getTitle());
        dto.setDescription(updatedIssue.getDescription());
        dto.setStatus(updatedIssue.getStatus());
        dto.setCreatedAt(updatedIssue.getCreatedAt());
        dto.setUpdatedAt(updatedIssue.getUpdatedAt());
        dto.setReportedByUsername(updatedIssue.getReportedBy().getUsername());

        return dto;
    }

    public void deleteIssue(long id) {
        issueRepo.deleteById(id);
    }
}
