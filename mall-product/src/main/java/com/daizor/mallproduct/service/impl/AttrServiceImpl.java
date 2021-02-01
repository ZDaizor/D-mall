package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.constant.ProductConstant;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.AttrAttrgroupRelationDao;
import com.daizor.mallproduct.dao.AttrDao;
import com.daizor.mallproduct.entity.AttrAttrgroupRelationEntity;
import com.daizor.mallproduct.entity.AttrEntity;
import com.daizor.mallproduct.entity.AttrGroupEntity;
import com.daizor.mallproduct.entity.CategoryEntity;
import com.daizor.mallproduct.service.AttrAttrgroupRelationService;
import com.daizor.mallproduct.service.AttrGroupService;
import com.daizor.mallproduct.service.AttrService;
import com.daizor.mallproduct.service.CategoryService;
import com.daizor.mallproduct.vo.AttrRespVO;
import com.daizor.mallproduct.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * AttrServiceImpl
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) { 
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        save(attrEntity);
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public PageUtils queryBasePage(Map<String, Object> params, Long categoryId, String key, String type) {
        QueryWrapper<AttrEntity> attrEntityQueryWrapper = new QueryWrapper<>();
        IPage<AttrEntity> page = page(new Query<AttrEntity>().getPage(params), attrEntityQueryWrapper
                .eq(categoryId != 0L, "catelog_id", categoryId)
                .eq(StringUtils.isNotBlank(type), "attr_type", "base".equalsIgnoreCase(type)
                        ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode())
                .like(StringUtils.isNotBlank(key), "attr_name", key));
        List<AttrEntity> records = page.getRecords();

        List<AttrRespVO> attrRespVOS = records.stream().map((attrEntity) -> {
            AttrRespVO attrRespVO = new AttrRespVO();
            BeanUtils.copyProperties(attrEntity, attrRespVO);
            CategoryEntity categoryEntity = categoryService.getById(attrEntity.getCatelogId());
            attrRespVO.setCatelogName(categoryEntity.getName());
            if ("base".equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationService.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getAttrId()));
                if (relationEntity != null) {
                    AttrGroupEntity groupEntity = attrGroupService.getById(relationEntity.getAttrGroupId());
                    attrRespVO.setGroupName(groupEntity.getAttrGroupName());
                } else {
                    attrRespVO.setGroupName("");
                }
            }
            return attrRespVO;
        }).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVOS);
        return pageUtils;
    }

    @Override
    public AttrRespVO getAttrInfo(Long attrId) {

        AttrRespVO attrRespVO = new AttrRespVO();
        AttrEntity attrEntity = getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVO);

        List<Long> categoryPath = categoryService.findCategoryPath(attrEntity.getCatelogId());
        attrRespVO.setCatelogPath(categoryPath.toArray(new Long[0]));

        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationService.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_id", attrEntity.getAttrId()));
        if (relationEntity != null) {
            AttrGroupEntity attrGroupEntity = attrGroupService.getOne(new QueryWrapper<AttrGroupEntity>()
                    .eq("attr_group_id", relationEntity.getAttrGroupId()));
            attrRespVO.setAttrGroupId(attrGroupEntity.getAttrGroupId());
            attrRespVO.setGroupName(attrGroupEntity.getAttrGroupName());
        }
        return attrRespVO;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        updateById(attrEntity);
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attr.getAttrId());
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        Integer count = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_id", attr.getAttrId()));
        if (count > 0) {
            attrAttrgroupRelationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
        } else {
            attrAttrgroupRelationDao.insert(relationEntity);
        }
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        QueryWrapper<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityQueryWrapper = new QueryWrapper<>();
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(attrAttrgroupRelationEntityQueryWrapper
                .eq("attr_group_id", attrgroupId));
        List<Long> relationAttrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        ArrayList<AttrEntity> attrEntities = new ArrayList<>();
        for (Long attrId : relationAttrIds) {
            AttrEntity attrEntity = this.getById(attrId);
            attrEntities.add(attrEntity);
        }
        return attrEntities;
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {

        AttrGroupEntity attrGroupEntity = attrGroupService.getOne(new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", attrgroupId));
        Long catelogId = attrGroupEntity.getCatelogId();
        List<AttrGroupEntity> attrGroupEntities = attrGroupService.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> attrGroupIds = attrGroupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> attrgroupRelationEntities = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
        List<Long> attrRelationList = attrgroupRelationEntities.stream()
                .map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        String key = params.get("key").toString();
        IPage<AttrEntity> page = page(new Query<AttrEntity>().getPage(params), new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).notIn("attr_id", attrRelationList)
                .like(StringUtils.isNotBlank(key), "attr_name", key));

        return new PageUtils(page);
    }
}




