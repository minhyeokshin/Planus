package com.teamflow.Planus.domain.user.github.mapper;

import com.teamflow.Planus.vo.CommitVO;
import com.teamflow.Planus.vo.GroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface GitHubCommitMapper {

    List<GroupVO> getGroup();
    int insertCommit(@RequestParam("list") List<CommitVO> list);
    List<CommitVO> getCommitList();
}
