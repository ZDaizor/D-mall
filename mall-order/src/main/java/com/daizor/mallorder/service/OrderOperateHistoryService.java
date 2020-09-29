package com.daizor.mallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallorder.entity.OrderOperateHistoryEntity;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 15:21:46
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

