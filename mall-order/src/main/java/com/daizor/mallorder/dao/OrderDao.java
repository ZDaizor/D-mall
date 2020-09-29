package com.daizor.mallorder.dao;

import com.daizor.mallorder.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 15:21:46
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
