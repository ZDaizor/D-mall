package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.AttrGroupDao;
import com.daizor.mallproduct.entity.AttrEntity;
import com.daizor.mallproduct.entity.AttrGroupEntity;
import com.daizor.mallproduct.service.AttrGroupService;
import com.daizor.mallproduct.service.AttrService;
import com.daizor.mallproduct.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 属性分组service
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;


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
            if (params.get("key") != null && StringUtils.isNotBlank(params.get("key").toString())) {
                cateLogQuery.and((obj) -> {
                    obj.eq("attr_group_name", params.get("key"))
                            .or().eq("attr_group_id", params.get("key"));
                });
            }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), cateLogQuery);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        List<AttrGroupEntity> attrGroupEntities = list(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
        return attrGroupEntities.stream().map(x -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(x, attrGroupWithAttrsVo);
            List<AttrEntity> attrEntities = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrEntities);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
    }
}
