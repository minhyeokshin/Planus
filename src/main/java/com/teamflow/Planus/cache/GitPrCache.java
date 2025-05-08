package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.PrVO;

import java.util.List;

public class GitPrCache {

    private static final GitPrCache instance = new GitPrCache();
    private List<PrVO> prList;

    private GitPrCache() {
    }

    public static GitPrCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(List<PrVO>) first.");
        }
        return instance;
    }

    public List<PrVO> getCachedList() {
        return prList;
    }

    public void setPrList(List<PrVO> prList) {
        this.prList = prList;
    }
}
