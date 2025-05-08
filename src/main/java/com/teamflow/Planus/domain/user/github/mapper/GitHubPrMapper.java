package com.teamflow.Planus.domain.user.github.mapper;

import com.teamflow.Planus.vo.GroupVO;
import com.teamflow.Planus.vo.PrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface GitHubPrMapper {

    List<GroupVO> getGroup();
    int insertPr(@RequestParam("list") List<PrVO> list);
    List<PrVO> getPrList();
}
