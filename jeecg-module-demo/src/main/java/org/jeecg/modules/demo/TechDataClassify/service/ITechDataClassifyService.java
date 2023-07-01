package org.jeecg.modules.demo.TechDataClassify.service;

import org.jeecg.modules.demo.TechDataClassify.entity.TechDataClassify;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 科学技术-多级学科
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface ITechDataClassifyService extends IService<TechDataClassify> {

    List<TechDataClassify> listWithTree(String parentName);

    void addCategory(TechDataClassify category);

    void deleteCategory(String categoryId);

    void updateCategory(TechDataClassify category);

    List<List<TechDataClassify>> getTreePathList(List<TechDataClassify> entity);


    int logicDeleteById(String mainId);




}
