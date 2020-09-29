package com.daizor.mallmember.feign;

import com.daizor.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : zhouxingzuo
 * @version : 1.0.0
 * @description : 远程调用接口
 * @createTime : 2020年09月29日 14:47:00
 */
@FeignClient("mall-coupon") //nacos中的feign服务端的名字
public interface CouponFeignService {

    /**
     * 获取会员优惠券
     */
    @RequestMapping("mallcoupon/coupon/member/list")
    public R memberCoupons();
}
