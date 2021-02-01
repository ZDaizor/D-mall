package com.daizor.mallproduct.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.daizor.mallproduct.entity.AttrEntity;
import com.daizor.mallproduct.entity.CategoryEntity;
import com.daizor.mallproduct.service.AttrAttrgroupRelationService;
import com.daizor.mallproduct.service.AttrService;
import com.daizor.mallproduct.service.CategoryService;
import com.daizor.mallproduct.vo.AttrGroupRelationVo;
import com.daizor.mallproduct.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.daizor.mallproduct.entity.AttrGroupEntity;
import com.daizor.mallproduct.service.AttrGroupService;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.R;


/**
 * 属性分组
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@RestController
@RequestMapping("mallproduct/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long categoryId) {
        PageUtils page = attrGroupService.queryPage(params, categoryId);
        return R.ok().put("page", page);
    }

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> attrEntities = attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data", attrEntities);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrAttrgroupRelationService.deleteRelation(vos);
        return R.ok();
    }


    /**
     * 拿出属性分组所在分类中未关联的属性信息
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        List<Long> categoryPath = categoryService.findCategoryPath(catelogId);
        attrGroup.setCatelogPath(categoryPath);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 取出商品所在分类中的所有属性分组和所有属性
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrsVo> attrGroupWithAttrsVos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", attrGroupWithAttrsVos);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
