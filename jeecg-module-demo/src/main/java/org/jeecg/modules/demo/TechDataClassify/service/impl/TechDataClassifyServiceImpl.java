package org.jeecg.modules.demo.TechDataClassify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.TechDataClassify.entity.TechDataClassify;
import org.jeecg.modules.demo.TechDataClassify.mapper.TechDataClassifyMapper;
import org.jeecg.modules.demo.TechDataClassify.service.ITechDataClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 科学技术-多级学科
 * @Author: jeecg-boot
 * @Date: 2023-06-01
 * @Version: V1.0
 */
@Service
public class TechDataClassifyServiceImpl extends ServiceImpl<TechDataClassifyMapper, TechDataClassify> implements ITechDataClassifyService {

    @Autowired
    private TechDataClassifyMapper techDataClassifyMapper;


    @Transactional(rollbackFor = Exception.class)
    public void addCategory(TechDataClassify category) {
        category.setIzDeleted("0");
        // 插入菜单实体
        techDataClassifyMapper.insert(category);

        // 如果有父菜单ID，则需要将新插入菜单实体添加为其子菜单
        if (category.getParentId() != null && !"0".equals(category.getParentId())) {
            // 获取父菜单实体
            TechDataClassify parentCategory = techDataClassifyMapper.selectById(category.getParentId());
            // 更新父菜单的子菜单列表
            List<TechDataClassify> children = parentCategory.getChildren();
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(category);
            parentCategory.setChildren(children);
            // 更新父菜单实体
            techDataClassifyMapper.updateById(parentCategory);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(String categoryId) {
        // 查询菜单实体
        TechDataClassify category = techDataClassifyMapper.selectById(categoryId);
        if (category != null) {
            // 删除所有子菜单
            List<TechDataClassify> children = category.getChildren();
            if (children != null && children.size() > 0) {
                for (TechDataClassify child : children) {
                    deleteCategory(child.getId());
                }
            }
            // 删除菜单实体
            techDataClassifyMapper.logicDeleteById(categoryId);
            // 更新父菜单的子菜单列表
            if (category.getParentId() != null) {
                TechDataClassify parentCategory = techDataClassifyMapper.selectById(category.getParentId());
                List<TechDataClassify> newChildren = new ArrayList<>();
                for (TechDataClassify child : parentCategory.getChildren()) {
                    if (!child.getId().equals(categoryId)) {
                        newChildren.add(child);
                    }
                }
                parentCategory.setChildren(newChildren);
                // 更新父菜单实体
                techDataClassifyMapper.updateById(parentCategory);
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(TechDataClassify category) {
        String categoryId = category.getId();
        // 查询菜单实体
        TechDataClassify oldCategory = techDataClassifyMapper.selectById(categoryId);
        oldCategory.setName(category.getName());
        oldCategory.setSort(category.getSort());
        oldCategory.setIzDeleted(category.getIzDeleted());
        // 更新菜单实体
        techDataClassifyMapper.updateById(oldCategory);
        // 更新父菜单的子菜单列表
        if (category.getParentId() != null) {
            TechDataClassify parentCategory = techDataClassifyMapper.selectById(category.getParentId());
            List<TechDataClassify> newChildren = new ArrayList<>();
            for (TechDataClassify child : parentCategory.getChildren()) {
                if (child.getId().equals(categoryId)) {
                    newChildren.add(oldCategory);
                } else {
                    newChildren.add(child);
                }
            }
            parentCategory.setChildren(newChildren);
            // 更新父菜单实体
            techDataClassifyMapper.updateById(parentCategory);
        }
    }


    @Override
    public List<TechDataClassify> listWithTree(String parentName) {
        // 先查询出指定一级菜单的ID

        QueryWrapper<TechDataClassify> wrapper = new QueryWrapper<>();
        wrapper.eq("name", parentName);
        wrapper.eq("iz_deleted", "0");
        TechDataClassify parentEntity = baseMapper.selectOne(wrapper);
        List<TechDataClassify> entities = baseMapper.selectList(null);

        List<TechDataClassify> collect = entities.stream()
                .filter(item -> Optional.ofNullable(parentEntity).map(e -> item.getParentId().equals(e.getId())).orElse(item.getParentId().equals("0")))
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted(Comparator.comparing(menu -> Optional.ofNullable(menu.getSort()).orElse(0)))
                .collect(Collectors.toList());

        return collect;
    }


    private List<TechDataClassify> getChildrens(TechDataClassify categoryEntity, List<TechDataClassify> entities) {
        List<TechDataClassify> collect = entities.stream()
                .filter(item -> item.getParentId().equals(categoryEntity.getId()))
                .map(menu -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }


    public List<List<TechDataClassify>> getTreePathList(List<TechDataClassify> entity) {
        List<List<TechDataClassify>> pathList = new ArrayList<>(); // 用于存储节点的路径数据
        if (entity != null && !entity.isEmpty()) {
            for (TechDataClassify node : entity) {
                if (node != null) {
                    List<TechDataClassify> path = new ArrayList<>(); // 用于存储当前节点的路径数据
                    path.add(node); // 将当前节点添加到路径数据中
                    getChildTreePathList(node, pathList, path); // 递归遍历子节点
                }
            }
        }
        return pathList;
    }

    @Override
    public int logicDeleteById(String mainId) {
        return techDataClassifyMapper.logicDeleteById(mainId);
    }

    private void getChildTreePathList(TechDataClassify node, List<List<TechDataClassify>> pathList, List<TechDataClassify> path) {
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            for (TechDataClassify child : node.getChildren()) {
                List<TechDataClassify> childPath = new ArrayList<>(path); // 复制上一级节点的路径数据
                childPath.add(child); // 将当前节点添加到路径数据中
                getChildTreePathList(child, pathList, childPath); // 递归遍历子节点
            }
        } else {
            pathList.add(path); // 将当前路径数据添加到路径集合中
        }
    }
}
