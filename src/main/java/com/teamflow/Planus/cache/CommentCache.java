package com.teamflow.Planus.cache;

import com.teamflow.Planus.vo.CommentVO;

import java.util.List;

public class CommentCache {

    private static final CommentCache instance = new CommentCache();
    private List<CommentVO> commentVOList;

    private CommentCache() {
    }

    public static CommentCache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("List is not initialized. Call getInstance(List<CommentVO>) first.");
        }
        return instance;
    }

    public List<CommentVO> getcommentVOList() {
        return commentVOList;
    }

    public void setCommentVOList(List<CommentVO> commentVOList) {
        this.commentVOList = commentVOList;
    }
}
