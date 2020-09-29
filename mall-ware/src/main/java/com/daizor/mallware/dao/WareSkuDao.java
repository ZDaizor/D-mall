package com.daizor.mallware.dao;

import com.daizor.mallware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 16:14:47
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
