package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.CategoryDao;
import com.daizor.mallproduct.entity.CategoryEntity;
import com.daizor.mallproduct.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author : zhouxingzuo
 * @version : 1.0.0
 * @description : 分类service impl
 * @createTime : 2020/10/21 16:22:29
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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
                //peek : 不改变流元素中本身的类型
                .peek((menu) -> menu.setSubCategory(findSubCategory(menu.getCatId(), categoryEntities)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
    }


    private List<CategoryEntity> findSubCategory(Long parentId, List<CategoryEntity> menu) {

        return menu.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(parentId))
                .peek(categoryEntity -> categoryEntity.setSubCategory(findSubCategory(categoryEntity.getCatId(), menu)))
                .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                .collect(Collectors.toList());
    }
}
