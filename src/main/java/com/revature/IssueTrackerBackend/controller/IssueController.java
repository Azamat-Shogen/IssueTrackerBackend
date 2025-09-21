package com.revature.IssueTrackerBackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController {

    // 👤 USER ENDPOINTS
    // TODO: (top level - issues) fetch all issues
    // TODO: /create - post an issue
    // TODO: /{id} fetch a single issue
    // TODO: /create post

    // 🛡️ ADMIN ENDPOINTS
    // TODO: /admin - view all issues
    // TODO: /admin{id}/status - put - update status
    // TODO: /admin{id} - get - fetch a single issue
    // TODO: /admin{id} - delete - delete a single issue

}



//package com.revature.IssueTrackerBackend.controller;
//
//import com.revature.IssueTrackerBackend.entity.Issue;
//import com.revature.IssueTrackerBackend.service.IssueService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//        import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/issues")
//public class IssueController {
//
//    private final IssueService issueService;
//
//    public IssueController(IssueService issueService) {
//        this.issueService = issueService;
//    }
//
//    // ================================
//    // 👤 USER ENDPOINTS (from JWT)
//    // ================================
//
//    // Fetch all issues submitted by the logged-in user
//    @GetMapping
//    public ResponseEntity<List<Issue>> getMyIssues(Principal principal) {
//        List<Issue> issues = issueService.getIssuesByUser(principal.getName());
//        return ResponseEntity.ok(issues);
//    }
//
//    // Fetch a single issue (only if it belongs to the logged-in user)
//    @GetMapping("/{id}")
//    public ResponseEntity<Issue> getMyIssueById(@PathVariable Long id, Principal principal) {
//        Issue issue = issueService.getIssueByIdForUser(id, principal.getName());
//        return ResponseEntity.ok(issue);
//    }
//
//    // Create a new issue
//    @PostMapping("/create")
//    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue, Principal principal) {
//        Issue newIssue = issueService.createIssue(issue, principal.getName());
//        return new ResponseEntity<>(newIssue, HttpStatus.CREATED);
//    }
//
//    // ================================
//    // 🛡️ ADMIN ENDPOINTS
//    // ================================
//
//    // View ALL issues (system-wide)
//    @GetMapping("/admin")
//    public ResponseEntity<List<Issue>> getAllIssues() {
//        List<Issue> issues = issueService.getAllIssues();
//        return ResponseEntity.ok(issues);
//    }
//
//    // Update issue status (Approve / Resolve / Deny)
//    @PutMapping("/admin/{id}/status")
//    public ResponseEntity<Issue> updateIssueStatus(
//            @PathVariable Long id,
//            @RequestParam String status) {
//        Issue updatedIssue = issueService.updateStatus(id, status);
//        return ResponseEntity.ok(updatedIssue);
//    }
//
//    // Delete an issue
//    @DeleteMapping("/admin/{id}")
//    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
//        issueService.deleteIssue(id);
//        return ResponseEntity.noContent().build();
//    }
//}
