package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.BoardVO;

import java.util.List;

public class BoardCache {

    private static final BoardCache instance = new BoardCache();
    private List<BoardVO> boardVOList;

    private BoardCache() {
    }

    public static BoardCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(List<BoardVO>) first.");
        }
        return instance;
    }

    public List<BoardVO> getBoardVOList() {
        return boardVOList;
    }

    public void setBoardVOList(List<BoardVO> boardVOList) {
        this.boardVOList = boardVOList;
    }
}
