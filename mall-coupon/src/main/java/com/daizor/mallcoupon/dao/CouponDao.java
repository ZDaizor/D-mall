package com.daizor.mallcoupon.dao;

import com.daizor.mallcoupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 14:35:47
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
