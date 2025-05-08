package com.teamflow.Planus;

import com.teamflow.Planus.domain.user.github.service.GitHubCommitService;
import com.teamflow.Planus.domain.user.github.service.GitHubPrService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GitHubTest {

    @Autowired
    private GitHubCommitService gitHubCommitService;

    @Autowired
    private GitHubPrService gitHubPrService;

    @Test
    public void getCommits() {
        gitHubCommitService.commitListSchedule();
    }

    @Test
    public void getPr(){
        gitHubPrService.prListSchedule();
    }


}
