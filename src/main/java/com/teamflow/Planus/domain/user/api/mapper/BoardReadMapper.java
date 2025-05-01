package com.teamflow.Planus.domain.user.api.mapper;

import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardReadMapper {
    BoardVO findById(int writeId);
    List<CommentVO> getComment(int writeId);
    int writeComment(String content, Long boardId, String userId);
    int deleteBoard(int writeId);
    int deleteComment(int commentId);

}
