package org.jeecg.modules.demo.cc.service.impl;

import org.jeecg.modules.demo.cc.entity.Aa;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;
import org.jeecg.modules.demo.cc.mapper.AaMapper;
import org.jeecg.modules.demo.cc.service.IAaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 销售管理
 * @Author: jeecg-boot
 * @Date:   2023-05-01
 * @Version: V1.0
 */
@Service
public class AaServiceImpl extends ServiceImpl<AaMapper, Aa> implements IAaService {
    @Autowired
    private AaMapper aaMapper;


    public void addCategory(Aa category) {
        // 插入菜单实体
        aaMapper.insert(category);
        // 获取新插入菜单实体的ID
        Long categoryId = Long.valueOf(category.getId());
        // 如果有父菜单ID，则需要将新插入菜单实体添加为其子菜单
        if (category.getParentCid() != null) {
            // 获取父菜单实体
            Aa parentCategory = aaMapper.selectById(category.getParentCid());
            // 更新父菜单的子菜单列表
            List<Aa> children = parentCategory.getChildren();
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(category);
            parentCategory.setChildren(children);
            // 更新父菜单实体
            aaMapper.updateById(parentCategory);
        }
    }





        public void deleteCategory(String categoryId) {
        // 查询菜单实体
        Aa category = aaMapper.selectById(categoryId);
        if (category != null) {
            // 删除所有子菜单
            List<Aa> children = category.getChildren();
            if (children != null && children.size() > 0) {
                for (Aa child : children) {
                    deleteCategory(child.getId());
                }
            }
            // 删除菜单实体
            aaMapper.deleteById(categoryId);
            // 更新父菜单的子菜单列表
            if (category.getParentCid() != null) {
                Aa parentCategory = aaMapper.selectById(category.getParentCid());
                List<Aa> newChildren = new ArrayList<>();
                for (Aa child : parentCategory.getChildren()) {
                    if (!child.getId().equals(categoryId)) {
                        newChildren.add(child);
                    }
                }
                parentCategory.setChildren(newChildren);
                // 更新父菜单实体
                aaMapper.updateById(parentCategory);
            }
        }
    }




    public void updateCategory(Aa category) {
        String categoryId = category.getId();
        // 查询菜单实体
        Aa oldCategory = aaMapper.selectById(categoryId);
        // 更新菜单实体
        oldCategory.setName(category.getName());
/*        oldCategory.setIcon(category.getIcon());
        oldCategory.setProductUnit(category.getProductUnit());
        oldCategory.setProductCount(category.getProductCount());*/
        oldCategory.setSort(category.getSort());
/*        oldCategory.setShowStatus(category.getShowStatus());*/
        // 更新菜单实体
        aaMapper.updateById(oldCategory);
        // 更新父菜单的子菜单列表
        if (category.getParentCid() != null) {
            Aa parentCategory = aaMapper.selectById(category.getParentCid());
            List<Aa> newChildren = new ArrayList<>();
            for (Aa child : parentCategory.getChildren()) {
                if (child.getId().equals(categoryId)) {
                    newChildren.add(oldCategory);
                } else {
                    newChildren.add(child);
                }
            }
            parentCategory.setChildren(newChildren);
            // 更新父菜单实体
            aaMapper.updateById(parentCategory);
        }
    }

    @Override
    public List<Aa> listWithTree() {
        List<Aa> entities = baseMapper.selectList(null);
        List<Aa> collect = entities.stream()
                .filter(item->item.getParentCid().equals("0"))
                .map(menu->{
                    menu.setChildren(getChildrens(menu,entities));
                    return menu;
                })
                .sorted((menu1,menu2)->{
                    return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
                return collect;
    }




    private List<Aa> getChildrens(Aa categoryEntity, List<Aa> entities) {
        List<Aa> collect = entities.stream()
                .filter(item -> item.getParentCid().equals(categoryEntity.getId()))
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }
}
