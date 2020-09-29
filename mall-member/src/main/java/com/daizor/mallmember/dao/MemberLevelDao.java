package com.daizor.mallmember.dao;

import com.daizor.mallmember.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 14:56:06
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {
	
}
