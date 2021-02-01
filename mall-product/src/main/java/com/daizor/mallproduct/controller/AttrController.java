package com.daizor.mallproduct.controller;

import java.util.Arrays;
import java.util.Map;

import com.daizor.mallproduct.vo.AttrRespVO;
import com.daizor.mallproduct.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daizor.mallproduct.entity.AttrEntity;
import com.daizor.mallproduct.service.AttrService;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.R;


/**
 * 商品属性
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@RestController
@RequestMapping("mallproduct/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/{attrType}/list/{categoryId}")
    public R baseList(Map<String, Object> params, @PathVariable Long categoryId, @RequestParam(value = "key", required = false) String key,
                      @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryBasePage(params, categoryId, key, type);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrRespVO attrRespVO = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrRespVO);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
