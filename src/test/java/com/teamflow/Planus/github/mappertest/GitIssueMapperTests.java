package com.teamflow.Planus.github.mappertest;

import com.teamflow.Planus.domain.user.github.mapper.GitHubIssueMapper;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.IssueVO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MapperScan(basePackages = "com.teamflow.Planus.domain.user.github.mapper")
public class GitIssueMapperTests {

    @Autowired
    private GitHubIssueMapper gitHubIssueMapper;

    @Test
    @DisplayName("그룹 정보 조회 테스트")
    @Transactional
    public void testGetGroup() {
        List<GroupVO> groups = gitHubIssueMapper.getGroup();
        log.info("getGroup() = " + groups);
        log.info("getGroup().size() = " + groups.size());
    }

    @Test
    @DisplayName("이슈 정보 저장 및 조회 테스트")
    @Transactional
    public void testInsertAndGetIssueList() {
        // given
        List<IssueVO> issues = new ArrayList<>();
        IssueVO issue = IssueVO.builder()
                .issueId("test-issue-id")
                .issueTitle("test issue title")
                .groupId(177687393623609344L)
                .userName("test-user")
                .userEmail("test@example.com")
                .issueDate(LocalDateTime.now())
                .issueStatus("open")
                .issueURL("https://github.com/test/repo/issues/test-issue-id")
                .build();
        issues.add(issue);

        // when
        int insertResult = gitHubIssueMapper.insertIssue(issues);
        List<IssueVO> issueList = gitHubIssueMapper.getIssueList();

        // then
        log.info("insertResult = " + insertResult);
        log.info("issueList = " + issueList);
        
        // 이슈 목록에서 방금 추가한 이슈 찾기
        IssueVO foundIssue = issueList.stream()
                .filter(i -> i.getIssueId().equals("test-issue-id"))
                .findFirst()
                .orElse(null);
                
        assertNotNull(foundIssue);
        assertEquals("test issue title", foundIssue.getIssueTitle());
        assertEquals("test-user", foundIssue.getUserName());
        assertEquals("test@example.com", foundIssue.getUserEmail());
        assertEquals("open", foundIssue.getIssueStatus());
    }
}
