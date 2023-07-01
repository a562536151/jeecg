package org.jeecg.modules.demo.cc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;

/**
 * 商品三级分类
 * 
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 15:38:36
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
