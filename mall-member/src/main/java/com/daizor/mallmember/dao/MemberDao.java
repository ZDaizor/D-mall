package com.daizor.mallmember.dao;

import com.daizor.mallmember.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-25 14:56:06
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
