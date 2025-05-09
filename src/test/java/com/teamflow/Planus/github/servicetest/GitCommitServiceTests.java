package com.teamflow.Planus.github.servicetest;

import com.teamflow.Planus.domain.auth.login.security.CustomUserDetails;
import com.teamflow.Planus.domain.user.github.service.GitHubCommitService;
import com.teamflow.Planus.dto.CommitDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@SpringBootTest
public class GitCommitServiceTests {

    @Autowired
    private GitHubCommitService gitHubCommitService;

    @Test
    @DisplayName("커밋 목록 스케줄링 테스트")
    @Transactional
    public void testCommitListSchedule() {
        // when
        gitHubCommitService.commitListSchedule();

        // then
        // 스케줄링이 정상적으로 실행되었는지 확인
        // 실제로는 GitHub API 호출이 필요하므로, 예외가 발생하지 않는지만 확인
    }

    @Test
    @DisplayName("커밋 목록 조회 테스트")
    @Transactional
    public void testGetCommitList() {
        CustomUserDetails mockUser = new CustomUserDetails(
            "test-id",
            "test@example.com",
            "encoded-password",
            "test-nickname",
            "ROLE_USER",
            1L
        );
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        List<CommitDTO> commitList = gitHubCommitService.getCommitList();
        
        // then
        log.info("commitList = " + commitList);
        log.info("commitList.size() = " + commitList.size());
        
        // 커밋 목록이 null이 아닌지 확인
        assertNotNull(commitList);
        
        // 커밋 목록이 비어있지 않다면, 각 커밋의 필수 필드가 존재하는지 확인
        if (!commitList.isEmpty()) {
            CommitDTO firstCommit = commitList.get(0);
            assertNotNull(firstCommit.getCommitId());
            assertNotNull(firstCommit.getCommitMsg());
            assertNotNull(firstCommit.getUserName());
            assertNotNull(firstCommit.getCommitDate());
            assertNotNull(firstCommit.getCommitURL());
        }
    }
}
