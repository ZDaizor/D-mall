package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.BrandDao;
import com.daizor.mallproduct.dao.CategoryBrandRelationDao;
import com.daizor.mallproduct.dao.CategoryDao;
import com.daizor.mallproduct.entity.BrandEntity;
import com.daizor.mallproduct.entity.CategoryBrandRelationEntity;
import com.daizor.mallproduct.entity.CategoryEntity;
import com.daizor.mallproduct.service.BrandService;
import com.daizor.mallproduct.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * CategoryBrandRelationServiceImpl
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>
        implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandService brandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveEntity(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandName(name);
        categoryBrandRelationEntity.setBrandId(brandId);
        update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    public void updateNameByCategoryId(Long catId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setCatelogName(name);
        update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntities = list(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", catId));
        List<BrandEntity> brandEntities = categoryBrandRelationEntities.stream().map(x -> {
            Long brandId = x.getBrandId();
            return brandService.getById(brandId);
        }).collect(Collectors.toList());
        return brandEntities;
    }
}
