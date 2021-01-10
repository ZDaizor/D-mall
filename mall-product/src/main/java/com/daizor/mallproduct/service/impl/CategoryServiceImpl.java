package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.CategoryDao;
import com.daizor.mallproduct.entity.CategoryEntity;
import com.daizor.mallproduct.service.CategoryBrandRelationService;
import com.daizor.mallproduct.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : zhouxingzuo
 * @version : 1.0.0
 * @description : 分类service impl
 * @createTime : 2020/10/21 16:22:29
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listByTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        return categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0L)
                //peek : 接收的是Consumer类型，没有返回值 不改变流元素中本身的类型
                .peek(menu -> menu.setSubCategory(findSubCategory(menu.getCatId(), categoryEntities)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
    }

    private List<CategoryEntity> findSubCategory(Long parentId, List<CategoryEntity> menu) {

        return menu.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(parentId))
                .peek(categoryEntity -> categoryEntity.setSubCategory(findSubCategory(categoryEntity.getCatId(), menu)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public List<Long> findCategoryPath(Long categoryId) {
        ArrayList<Long> categoryList = new ArrayList<>();
        getParentId(categoryId, categoryList);
        log.info("取出的三级分类为  :  {}", categoryList);
        Collections.reverse(categoryList);
        return categoryList;
    }

    @Override
    public void updateEntity(CategoryEntity category) {
        updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateNameByCategoryId(category.getCatId(), category.getName());
        }

    }

    //递归取出分类ID
    public void getParentId(Long categoryId, List<Long> categoryList) {
        categoryList.add(categoryId);
        CategoryEntity categoryEntityById = getById(categoryId);
        if (categoryEntityById.getParentCid() == 0) {
            return;
        }
        getParentId(categoryEntityById.getParentCid(), categoryList);
    }
}
