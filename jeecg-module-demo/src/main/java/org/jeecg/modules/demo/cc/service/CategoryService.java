package org.jeecg.modules.demo.cc.service;



import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 15:38:36
 */
public interface CategoryService extends IService<CategoryEntity> {



    List<CategoryEntity> listWithTree();

    void removeMenuByIds(List<Long> asList);

    /**
     * 找到该三级分类的完整路径
     * @param categorygId
     * @return
     */
    Long[] findCatelogPathById(Long categorygId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevel1Catagories();


}

