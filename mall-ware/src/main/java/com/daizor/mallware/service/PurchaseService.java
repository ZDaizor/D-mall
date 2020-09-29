package com.daizor.mallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 16:14:47
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

