//package com.daizor.mallgateway;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.server.ServerWebExchange;
//
///**
// * @author : zhouxingzuo
// * @version : 1.0.0
// * @description : 跨域配置
// * @createTime : 2020年10月27日 16:43:00
// */
//@Configuration
//public class CORSConfig {
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//
//        CorsConfigurationSource corsConfigurationSource = new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(ServerWebExchange serverWebExchange) {
//
//                return null;
//            }
//        };
//
//        return new CorsWebFilter();
//    }
//}
