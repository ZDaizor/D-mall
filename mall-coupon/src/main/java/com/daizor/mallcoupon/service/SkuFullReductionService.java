package com.daizor.mallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallcoupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 14:35:47
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

