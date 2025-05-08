package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.dto.PrDTO;

import java.util.List;

public interface GitHubPrService {
    void prListSchedule();

    List<PrDTO> getPrList();
}
