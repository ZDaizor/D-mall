package com.daizor.mallproduct.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daizor.mallproduct.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchByAttrIdAndAttrGroupId(@Param("attrAttrgroupRelationEntity") List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntity);
}
