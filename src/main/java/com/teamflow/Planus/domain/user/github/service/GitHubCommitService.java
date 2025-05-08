package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.vo.CommitVO;

import java.util.List;

public interface GitHubCommitService {
    void commitListSchedule();

    List<CommitVO> getCommitList();
}
