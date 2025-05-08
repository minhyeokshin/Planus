package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.CommitVO;

import java.util.List;

public class GitCommitCache {

    private static final GitCommitCache instance = new GitCommitCache();
    private List<CommitVO> commitVOList;

    private GitCommitCache() {
    }

    public static GitCommitCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(List<CommitVO>) first.");
        }
        return instance;
    }

    public List<CommitVO> getCommitVOList() {
        return commitVOList;
    }

    public void setCommitVOList(List<CommitVO> commitVOList) {
        this.commitVOList = commitVOList;
    }
}
