package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.IssueVO;

import java.time.LocalDateTime;
import java.util.List;

public class GitIssueCache {

    private static final GitIssueCache instance = new GitIssueCache();
    private List<IssueVO> issueVOList;
    private LocalDateTime lastUpdate;

    private GitIssueCache() {
    }

    public static GitIssueCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(List<IssueVO>) first.");
        }
        return instance;
    }

    public List<IssueVO> getIssueVOList() {
        return issueVOList;
    }

    public void setIssueVOList(List<IssueVO> issueVOList) {
        this.issueVOList = issueVOList;
        this.lastUpdate = LocalDateTime.now();
    }

}
