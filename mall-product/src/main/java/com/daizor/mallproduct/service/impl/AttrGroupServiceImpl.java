package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.AttrGroupDao;
import com.daizor.mallproduct.entity.AttrGroupEntity;
import com.daizor.mallproduct.service.AttrGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 属性分组service
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        if (categoryId == 0) {
            //拿出所有分组属性
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        } else {
            //查询指定ID对应的属性分组
            QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<>();
            QueryWrapper<AttrGroupEntity> cateLogQuery = attrGroupEntityQueryWrapper.eq("catelog_id", categoryId);
            if (StringUtils.isNotBlank(params.get("key").toString())) {
                cateLogQuery.and((obj) -> {
                    obj.eq("attr_group_name", params.get("key"))
                            .or().eq("attr_group_id", params.get("key"));
                });
            }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), cateLogQuery);
            return new PageUtils(page);
        }
    }
}