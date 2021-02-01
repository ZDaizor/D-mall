package com.daizor.mallproduct.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizor.common.utils.R;
import com.daizor.mallproduct.entity.BrandEntity;
import com.daizor.mallproduct.entity.CategoryBrandRelationEntity;
import com.daizor.mallproduct.service.CategoryBrandRelationService;
import com.daizor.mallproduct.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 品牌分类关联
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@RestController
@RequestMapping("mallproduct/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/catelog/list")
    public R list(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> relationEntities = categoryBrandRelationService
                .list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
        return R.ok().put("data", relationEntities);
    }

    /**
     * 查询分类下的所有品牌
     */
    @RequestMapping("/brands/list")
    public R relationBrandsList(@RequestParam(value = "catId") Long catId) {
        List<BrandEntity> brandEntities = categoryBrandRelationService.getBrandsByCatId(catId);

        List<BrandVo> brandVos = brandEntities.stream().map(x -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(x.getBrandId());
            brandVo.setBrandName(x.getName());
            return brandVo;
        }).collect(Collectors.toList());

        return R.ok().put("data", brandVos);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveEntity(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
