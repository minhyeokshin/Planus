package com.teamflow.Planus.github.mappertest;

import com.teamflow.Planus.domain.user.github.mapper.GitHubPrMapper;
import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.PrVO;
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
public class GitPrMapperTests {

    @Autowired
    private GitHubPrMapper gitHubPrMapper;

    @Test
    @DisplayName("그룹 정보 조회 테스트")
    @Transactional
    public void testGetGroup() {
        List<GroupVO> groups = gitHubPrMapper.getGroup();
        log.info("getGroup() = " + groups);
        log.info("getGroup().size() = " + groups.size());
    }

    @Test
    @DisplayName("PR 정보 저장 및 조회 테스트")
    @Transactional
    public void testInsertAndGetPrList() {
        // given
        List<PrVO> prs = new ArrayList<>();
        PrVO pr = PrVO.builder()
                .prId("test-pr-id")
                .prTitle("test PR title")
                .groupId(177687393623609344L)
                .userName("test-user")
                .userEmail("test@example.com")
                .prDate(LocalDateTime.now())
                .prStatus("open")
                .prURL("https://github.com/test/repo/pull/test-pr-id")
                .build();
        prs.add(pr);

        // when
        int insertResult = gitHubPrMapper.insertPr(prs);
        List<PrVO> prList = gitHubPrMapper.getPrList();

        // then
        log.info("insertResult = " + insertResult);
        log.info("prList = " + prList);
        
        // PR 목록에서 방금 추가한 PR 찾기
        PrVO foundPr = prList.stream()
                .filter(p -> p.getPrId().equals("test-pr-id"))
                .findFirst()
                .orElse(null);
                
        assertNotNull(foundPr);
        assertEquals("test PR title", foundPr.getPrTitle());
        assertEquals("test-user", foundPr.getUserName());
        assertEquals("test@example.com", foundPr.getUserEmail());
        assertEquals("open", foundPr.getPrStatus());
    }
}
