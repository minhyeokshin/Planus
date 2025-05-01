package com.teamflow.Planus.domain.user.api.service;

public interface BoardWriteService {
    int write(String title,
                       String content,
                       Long boardId);
}
