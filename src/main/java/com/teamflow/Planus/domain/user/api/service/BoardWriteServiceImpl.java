package com.teamflow.Planus.domain.user.api.service;

import com.teamflow.Planus.cache.BoardCache;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.api.mapper.BoardWriteMapper;
import com.teamflow.Planus.domain.user.board.mapper.BoardMapper;
import com.teamflow.Planus.dto.PostDTO;
import com.teamflow.Planus.util.MailService;
import com.teamflow.Planus.vo.BoardVO;
import com.teamflow.Planus.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardWriteServiceImpl implements BoardWriteService {

    private final BoardWriteMapper boardWriteMapper;
    private final BoardMapper boardMapper;
    private final MailService mailService;

    @Override
    public int write(String title, String content, Long boardId) {
        log.info("BoardWriteServiceImpl");
        log.info("boardId: {}", boardId);
        log.info("title: {}", title);
        log.info("content: {}", content);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();

        List<BoardVO> boardVOList = BoardCache.getInstance().getBoardVOList();
        if (boardVOList == null){
            boardVOList = boardMapper.getBoardList();
            BoardCache.getInstance().setBoardVOList(boardVOList);
        }
        log.info("boardVOList: {}", boardVOList);

        int size = 0;
        int nextWriteId = 1;
        if(!boardVOList.isEmpty()){
            Optional<BoardVO> optionalBoardVO = boardVOList.stream()
                    .max(Comparator.comparing(BoardVO::getWriteId));
            nextWriteId = optionalBoardVO.get().getWriteId() +1;
        }


        BoardVO boardVO = BoardVO.builder()
                .writeId(nextWriteId)
                .boardId(String.valueOf(boardId))
                .groupId(currentUser.getGroupId())
                .userId(currentUser.getUserId())
                .userName(currentUser.getName())
                .createdAt(LocalDateTime.now())
                .title(title)
                .content(content)
                .status(0)
                .build();

        log.info("boardVO: {}", boardVO);
        boardVOList.add(boardVO);
        BoardCache.getInstance().setBoardVOList(boardVOList);
        LocalDateTime now = LocalDateTime.now();
        int result = boardWriteMapper.write(title, content, boardId,currentUser.getUserId(),now,currentUser.getGroupId());
        List<UserVO> userEmailList = boardWriteMapper.getuserEmailList();

        userEmailList =
                userEmailList.stream()
                        .filter(vo -> vo.getGroupId() != null && vo.getGroupId().equals(currentUser.getGroupId()))
                        .toList();

        String to;
        if (result > 0){
            for (UserVO email : userEmailList) {
                to = email.getEmail();
                PostDTO postDTO = PostDTO.builder()
                        .title(title)
                        .content(content)
                        .createdAt(now)
                        .author(currentUser.getUsername())
                        .writeId(nextWriteId)
                        .build();
                mailService.sendPostNotification(to, postDTO);
            }
        }


        return result;
    }
}
