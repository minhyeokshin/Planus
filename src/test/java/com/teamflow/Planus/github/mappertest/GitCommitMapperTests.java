package com.teamflow.Planus.github.mappertest;

import com.teamflow.Planus.domain.user.github.mapper.GitHubCommitMapper;
import com.teamflow.Planus.vo.CommitVO;
import com.teamflow.Planus.vo.GroupVO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.github.mapper")
public class GitCommitMapperTests {

    @Autowired
    private GitHubCommitMapper gitHubCommitMapper;

    @Test
    @DisplayName("그룹 정보 조회 테스트")
    @Transactional
    public void testGetGroup() {

        List<GroupVO> groups = gitHubCommitMapper.getGroup();
        log.info("getGroup() = " + groups);
        log.info("getGroup().size() = " + groups.size());
    }

    @Test
    @DisplayName("커밋 정보 저장 및 조회 테스트")
    @Transactional
    public void testInsertAndGetCommitList() {
        // given
        List<CommitVO> commits = new ArrayList<>();
        CommitVO commit = CommitVO.builder()
                .commitId("test-commit-id")
                .commitMsg("test commit message")
                .groupId(177687393623609344L)
                .userName("test-user")
                .userEmail("test@example.com")
                .commitDate(LocalDateTime.now())
                .commitURL("https://github.com/test/repo/commit/test-commit-id")
                .build();
        commits.add(commit);

        // when
        int insertResult = gitHubCommitMapper.insertCommit(commits);
        List<CommitVO> commitList = gitHubCommitMapper.getCommitList();

        // then
        assertTrue(insertResult > 0);
        assertNotNull(commitList);
        assertFalse(commitList.isEmpty());
        
        // 커밋 목록에서 방금 추가한 커밋 찾기
        CommitVO foundCommit = commitList.stream()
                .filter(c -> c.getCommitId().equals("test-commit-id"))
                .findFirst()
                .orElse(null);
                
        assertNotNull(foundCommit);
        assertEquals("test commit message", foundCommit.getCommitMsg());
        assertEquals("test-user", foundCommit.getUserName());
        assertEquals("test@example.com", foundCommit.getUserEmail());
    }
}
