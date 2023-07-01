package org.jeecg.modules.demo.chapterManage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 用章管理附件
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */

@Mapper
public interface ChapterManageFileMapper extends BaseMapper<ChapterManageFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<ChapterManageFile> selectByMainId(@Param("mainId") String mainId);

}
