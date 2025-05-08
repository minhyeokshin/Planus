package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.dto.IssueDTO;

import java.util.List;

public interface GitHubIssueService {
    void issueListSchedule();

    List<IssueDTO> getIssueList();
}
