package com.daizor.mallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizor.common.utils.PageUtils;
import com.daizor.common.utils.Query;
import com.daizor.mallproduct.dao.BrandDao;
import com.daizor.mallproduct.entity.BrandEntity;
import com.daizor.mallproduct.service.BrandService;
import com.daizor.mallproduct.service.CategoryBrandRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


/**
 * 品牌相关Service
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override

    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<BrandEntity> brandEntityQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(params.get("key").toString())) {
            brandEntityQueryWrapper.eq("brand_id", params.get("key")).or().like("name", params.get("key"));
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                brandEntityQueryWrapper
        );
        return new PageUtils(page);
    }


    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        updateById(brand);
        if (StringUtils.isNotBlank(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
    }
}
