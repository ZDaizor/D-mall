package com.daizor.mallproduct.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daizor.mallproduct.entity.SkuImagesEntity;
import com.daizor.mallproduct.service.SkuImagesService;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.R;



/**
 * sku图片
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@RestController
@RequestMapping("mallproduct/skuimages")
public class SkuImagesController {
    @Autowired
    private SkuImagesService skuImagesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SkuImagesEntity skuImages = skuImagesService.getById(id);

        return R.ok().put("skuImages", skuImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuImagesEntity skuImages){
		skuImagesService.save(skuImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuImagesEntity skuImages){
		skuImagesService.updateById(skuImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
