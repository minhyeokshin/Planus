package com.teamflow.Planus.domain.user.github.service;

import com.teamflow.Planus.cache.GitCommitCache;
import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.github.mapper.GitHubCommitMapper;
import com.teamflow.Planus.dto.CommitDTO;
import com.teamflow.Planus.util.TokenEncryptor;
import com.teamflow.Planus.vo.CommitVO;
import com.teamflow.Planus.vo.GroupVO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GitHubCommitServiceImpl implements GitHubCommitService {

    @Value("${github.token}")
    private String TOKEN;

    private final GitHubCommitMapper gitHubCommitMapper;
    private final TokenEncryptor tokenEncryptor;

    @Override
    @PostConstruct
    @Scheduled(fixedRate = 300000) // 5분 = 300,000밀리초
    public void commitListSchedule() {

        List<GroupVO> groupVOList = gitHubCommitMapper.getGroup();
        List<CommitVO> commitVOList = new ArrayList<>();

        List<CommitVO> cachedList = GitCommitCache.getInstance().getCommitVOList();
        if (cachedList == null || cachedList.isEmpty()) {
            cachedList = gitHubCommitMapper.getCommitList();
        }
        List<CommitVO> commitListCache = new ArrayList<>(cachedList);

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

            repo.listCommits().withPageSize(100).forEach(commit -> {
                try {

                    CommitVO commitVO = CommitVO.builder()
                            .commitId(commit.getSHA1())
                            .commitMsg(commit.getCommitShortInfo().getMessage())
                            .groupId(groupVO.getGroupId())
                            .userName(commit.getCommitShortInfo().getAuthor().getName())
                            .userEmail(commit.getCommitShortInfo().getAuthor().getEmail())
                            .commitDate(commit.getCommitShortInfo().getAuthor().getDate()
                                .atZone(ZoneId.of("Asia/Seoul"))
                                .toLocalDateTime())
                            .commitURL(String.valueOf(commit.getHtmlUrl()))
                            .build();
                    commitVOList.add(commitVO);
                    commitListCache.add(commitVO);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        gitHubCommitMapper.insertCommit(commitVOList);
        GitCommitCache.getInstance().setCommitVOList(commitListCache);
    }
    }

    @Override
    public List<CommitDTO> getCommitList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        Long groupId = currentUser.getGroupId();
        List<CommitVO> commitVOList = GitCommitCache.getInstance().getCommitVOList();
        List<CommitDTO> commitDTOList = new ArrayList<>();
        if(commitVOList.isEmpty()){
            commitVOList = gitHubCommitMapper.getCommitList();
            GitCommitCache.getInstance().setCommitVOList(commitVOList);
        }
        commitVOList =
                commitVOList.stream()
                        .filter(vo -> vo.getGroupId().equals(groupId))
                        .sorted((c1, c2) -> c2.getCommitDate().compareTo(c1.getCommitDate()))
                        .toList();

        for(CommitVO commitVO : commitVOList){
            CommitDTO commitDTO = CommitDTO.builder()
                    .commitId(commitVO.getCommitId())
                    .commitMsg(commitVO.getCommitMsg())
                    .userName(commitVO.getUserName())
                    .commitURL(commitVO.getCommitURL())
                    .commitDate(commitVO.getCommitDate())
                    .build();
            commitDTOList.add(commitDTO);
        }
        
        
        return commitDTOList;
    }
}
