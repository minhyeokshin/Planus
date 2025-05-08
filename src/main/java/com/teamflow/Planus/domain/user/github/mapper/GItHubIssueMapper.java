package com.teamflow.Planus.domain.user.github.mapper;

import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.IssueVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface GitHubIssueMapper {
    List<GroupVO> getGroup();
    int insertIssue(@RequestParam("list") List<IssueVO> list);
    List<IssueVO> getIssueList();
}
