package org.jeecg.modules.demo.TechDataClassify.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.TechDataClassify.entity.TechDataClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 科学技术-多级学科
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */

@Mapper
public interface TechDataClassifyMapper extends BaseMapper<TechDataClassify> {

    int logicDeleteById(@Param("mainId") String mainId);

}
