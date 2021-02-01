package com.daizor.mallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daizor.common.utils.PageUtils;
import com.daizor.mallproduct.entity.AttrGroupEntity;
import com.daizor.mallproduct.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author daizor
 * @email xingzuozhou@gmail.com
 * @date 2020-09-17 11:52:59
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params, Long categoryId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
}

