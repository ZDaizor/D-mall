package com.daizor.mallproduct.dao;

import com.daizor.mallproduct.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
