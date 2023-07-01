package org.jeecg.modules.demo.chapterManage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 用章管理
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */

@Mapper
public interface ChapterManageMapper extends BaseMapper<ChapterManage> {

    String maxRoundCode();

    String queryDepartmentByCode(@Param("orgCode") String orgCode);

    public String queryChapterNumber(@Param("processInstanceId") String processInstanceId);

}
