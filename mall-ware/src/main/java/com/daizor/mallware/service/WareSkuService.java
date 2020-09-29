package com.daizor.mallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 16:14:47
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

