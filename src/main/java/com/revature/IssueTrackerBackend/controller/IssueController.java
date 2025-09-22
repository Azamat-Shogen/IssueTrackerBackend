package com.revature.IssueTrackerBackend.controller;

import com.revature.IssueTrackerBackend.dto.IssueSummaryDTO;
import com.revature.IssueTrackerBackend.entity.Issue;
import com.revature.IssueTrackerBackend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // 👤 USER ENDPOINTS
    @PostMapping
    public ResponseEntity<IssueSummaryDTO> createIssue(@RequestBody Issue issue, Authentication authentication){
        String username = authentication.getName();
        IssueSummaryDTO savedIssue = issueService.createIssue(issue, username);
        return new ResponseEntity<>(savedIssue, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IssueSummaryDTO>> getAllIssuesByUser(Authentication authentication){
        String username = authentication.getName();
        List<IssueSummaryDTO> issues = issueService.getIssuesByUsername(username);
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueSummaryDTO> getIssueById(@PathVariable long id){
        IssueSummaryDTO issue = issueService.getIssueById(id);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    // 🛡️ ADMIN ENDPOINTS

    @GetMapping("/admin")
    public ResponseEntity<List<IssueSummaryDTO>> getAllIssues(){
        List<IssueSummaryDTO> issues = issueService.getAllIssues();
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<IssueSummaryDTO> getAdminIssueById(@PathVariable long id){
        IssueSummaryDTO issue = issueService.getIssueById(id);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<IssueSummaryDTO> updateIssueStatus(@PathVariable long id, @RequestBody Issue issue ){
        IssueSummaryDTO updatedIssue = issueService.updateIssueStatus(id, issue.getStatus());
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteIssue(@PathVariable long id){
        try{
            issueService.deleteIssue(id);
            return new ResponseEntity<>("Issue deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}