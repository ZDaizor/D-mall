package com.daizor.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallproduct.entity.AttrEntity;
import com.daizor.mallproduct.vo.AttrRespVO;
import com.daizor.mallproduct.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBasePage(Map<String, Object> params, Long categoryId, String key, String type);

    AttrRespVO getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

