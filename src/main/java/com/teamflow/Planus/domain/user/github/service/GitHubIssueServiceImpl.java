package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.cache.GitIssueCache;
import com.teamflow.Planus.domain.user.github.mapper.GitHubIssueMapper;
import com.teamflow.Planus.dto.IssueDTO;
import com.teamflow.Planus.util.TokenEncryptor;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.IssueVO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class GitHubIssueServiceImpl implements GitHubIssueService {


    @Value("${github.token}")
    private String TOKEN;

    private final GitHubIssueMapper githubIssueMapper;

    @Override
    @PostConstruct
    @Scheduled(fixedRate = 300000) // 5분 = 300,000밀리초
    public void issueListSchedule() {
        List<GroupVO> groupVOList = githubIssueMapper.getGroup();
        List<IssueVO> issueVOList = new ArrayList<>();

        List<IssueVO> cachedList = GitIssueCache.getInstance().getIssueVOList();
        if (cachedList == null || cachedList.isEmpty()) {
            cachedList = githubIssueMapper.getIssueList();
        }
        List<IssueVO> issueListCache = new ArrayList<>(cachedList);

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

                    PagedIterable<GHIssue> issues = repo.queryIssues()
                            .state(GHIssueState.ALL)
                            .list();

                    for (GHIssue issue : issues) {
                        IssueVO issueVO = IssueVO.builder()
                                .issueId(String.valueOf(issue.getId()))
                                .issueTitle(issue.getTitle())
                                .userName(issue.getUser().getLogin())
                                .userEmail(issue.getUser().getEmail()) // nullable
                                .issueDate(issue.getCreatedAt()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime())
                                .issueStatus(issue.getState().toString().toLowerCase())
                                .issueURL(issue.getHtmlUrl().toString())
                                .groupId(groupVO.getGroupId())
                                .build();

                        issueVOList.add(issueVO);
                        issueListCache.add(issueVO);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            githubIssueMapper.insertIssue(issueVOList);
            GitIssueCache.getInstance().setIssueVOList(issueListCache);
        }
    }

    @Override
    public List<IssueDTO> getIssueList() {
        List<IssueDTO> issueDTOList = new ArrayList<>();
        List<IssueVO> issueVOList = githubIssueMapper.getIssueList();
        
        for (IssueVO issueVO : issueVOList) {
            IssueDTO issueDTO = IssueDTO.builder()
                .issueId(issueVO.getIssueId())
                .issueTitle(issueVO.getIssueTitle())
                .groupId(issueVO.getGroupId())
                .issueStatus(issueVO.getIssueStatus())
                .issueURL(issueVO.getIssueURL())
                .userName(issueVO.getUserName())
                .userEmail(issueVO.getUserEmail())
                .issueDate(issueVO.getIssueDate())
                .build();
            
            issueDTOList.add(issueDTO);
        }
        
        return issueDTOList;
    }
}
