package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.dto.CommitDTO;

import java.util.List;

public interface GitHubCommitService {
    void commitListSchedule();

    List<CommitDTO> getCommitList();
}
