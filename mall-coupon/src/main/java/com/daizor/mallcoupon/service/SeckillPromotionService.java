package com.daizor.mallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallcoupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 14:35:47
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

