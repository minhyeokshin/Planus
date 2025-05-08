package com.teamflow.Planus;

import com.teamflow.Planus.domain.user.github.service.GitHubCommitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitHubTest {

    @Autowired
    private GitHubCommitService gitHubCommitService;

    @Test
    public void getCommits() {
        gitHubCommitService.commitListSchedule();
    }


}
