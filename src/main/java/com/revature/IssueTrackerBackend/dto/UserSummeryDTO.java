package com.revature.IssueTrackerBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummeryDTO {
    private Long id;
    private String username;
    private String role;
}
