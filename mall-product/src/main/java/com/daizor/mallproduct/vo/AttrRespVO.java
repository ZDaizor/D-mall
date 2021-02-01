package com.daizor.mallproduct.vo;

import com.daizor.mallproduct.entity.AttrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询规格参数返回VO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttrRespVO extends AttrEntity {
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
    private Long attrGroupId;

}
