package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.cache.GitPrCache;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.github.mapper.GitHubPrMapper;
import com.teamflow.Planus.dto.PrDTO;
import com.teamflow.Planus.util.TokenEncryptor;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.PrVO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GitHubPrServiceImpl implements GitHubPrService {

    @Value("${github.token}")
    private String TOKEN;

    private final GitHubPrMapper gitHubPrMapper;


    @Override
    @PostConstruct
    @Scheduled(fixedRate = 300000) // 5분 = 300,000밀리초
    public void prListSchedule() {

        List<GroupVO> groupVOList = gitHubPrMapper.getGroup();
        List<PrVO> prVOList = new ArrayList<>();

        List<PrVO> cachedList = GitPrCache.getInstance().getCachedList();
        if (cachedList == null || cachedList.isEmpty()) {
            cachedList = gitHubPrMapper.getPrList();
        }
        List<PrVO> prListCache = new ArrayList<>(cachedList);

        groupVOList =
                groupVOList.stream()
                        .filter(vo -> vo.getGitHubOwner() != null && !vo.getGitHubOwner().isBlank())
                        .filter(vo -> vo.getGitHubRepo() != null && !vo.getGitHubRepo().isBlank())
                        .toList();

        if(!groupVOList.isEmpty()){

            for (GroupVO groupVO : groupVOList) {

                String gitHubToken;

                if (groupVO.getGitHubToken() != null
                        && !groupVO.getGitHubToken().isBlank()
                        && groupVO.getGitHubTokenDate() != null
                        && groupVO.getGitHubTokenDate().isBefore(LocalDateTime.now())) {
                    gitHubToken = TokenEncryptor.decrypt(groupVO.getGitHubToken());
                }else {
                    gitHubToken =TOKEN;
                }

                String owner = groupVO.getGitHubOwner();
                String repoName = groupVO.getGitHubRepo();

                try {
                    GitHub gitHub = new GitHubBuilder().withOAuthToken(gitHubToken).build();
                    gitHub.checkApiUrlValidity();
                    GHRepository repo = gitHub.getRepository( owner + "/" + repoName);

                    PagedIterable<GHPullRequest> pullRequests = repo.queryPullRequests()
                        .state(GHIssueState.ALL)
                        .list();

                    for (GHPullRequest pr : pullRequests) {
                        PrVO prVO = PrVO.builder()
                            .prId(String.valueOf(pr.getId()))
                            .prTitle(pr.getTitle())
                            .userName(pr.getUser().getLogin())
                            .userEmail(pr.getUser().getEmail()) // nullable
                            .prDate(pr.getCreatedAt()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime())
                            .prStatus(pr.getMergedAt() != null ? "merged" : pr.getState().toString().toLowerCase())
                            .prURL(pr.getHtmlUrl().toString())
                            .groupId(groupVO.getGroupId())
                            .build();

                        prVOList.add(prVO);
                        prListCache.add(prVO);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            gitHubPrMapper.insertPr(prVOList);
            GitPrCache.getInstance().setPrList(prListCache);
        }

    }

    @Override
    public List<PrDTO> getPrList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long groupId = customUserDetails.getGroupId();

        List<PrDTO> prDTOList = new ArrayList<>();

        List<PrVO> prVOList = GitPrCache.getInstance().getCachedList();
        if (prVOList == null || prVOList.isEmpty()) {
            prVOList = gitHubPrMapper.getPrList();
            GitPrCache.getInstance().setPrList(prVOList);
        }

        log.info("prVOList: {}", prVOList);

        prVOList =
                prVOList.stream()
                        .filter(vo -> vo.getGroupId().equals(groupId))
                        .toList();

        for (PrVO prVO : prVOList) {
            PrDTO prDTO = PrDTO.builder()
                .prId(prVO.getPrId())
                .prTitle(prVO.getPrTitle())
                .userName(prVO.getUserName())
                .userEmail(prVO.getUserEmail())
                .prDate(prVO.getPrDate())
                .prStatus(prVO.getPrStatus())
                .prURL(prVO.getPrURL())
                .build();

            prDTOList.add(prDTO);
        }

        log.info("prDTOList: {}", prDTOList);

        return prDTOList;
    }
}
