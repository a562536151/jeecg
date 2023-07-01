package org.jeecg.modules.demo.cc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.cc.entity.Aa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author jiangjian
 * @date 2023/06/11
 */
@Mapper
public interface AaMapper extends BaseMapper<Aa> {
    void updateData(Aa aa);


}
