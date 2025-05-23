package com.teamflow.Planus.domain.user.api.mapper;

import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.BoardViewLogVO;
import com.teamflow.Planus.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BoardReadMapper {
    BoardVO findById(int writeId);
    List<CommentVO> getComment();
    int writeComment(String content, Long boardId, String userId,LocalDateTime now,Long groupId);
    int deleteBoard(int writeId, LocalDateTime now,String userId);
    int deleteComment(int commentId,LocalDateTime now,String userId);
    int viewLog(String viewId,int writeId,String userId,LocalDateTime now);
    List<BoardViewLogVO> getViewLog();

}
