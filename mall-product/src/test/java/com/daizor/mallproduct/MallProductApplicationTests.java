//package com.daizor.mallproduct;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import com.daizor.mallproduct.entity.BrandEntity;
//import com.daizor.mallproduct.service.BrandService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//@Slf4j
//@SpringBootTest
//class MallProductApplicationTests {
//
//
//    @Autowired
//    BrandService brandService;
//
//    @Test
//    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("修改描述");
//        brandService.updateById(brandEntity);
//    }
//
//    //    阿里云OSS测试
//    @Test
//    void ossUploadTest() throws FileNotFoundException {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-beijing.aliyuncs.com";// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI4GA5DJGA148pMde84oVs";
//        String accessKeySecret = "toLa8l6J7R0tyK8T8ydufjznyETRGK";
//
//// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//// 上传文件流。
//        InputStream inputStream = new FileInputStream("D:\\Learn\\Project\\nacos\\nacos-server-1.3.0\\nacos\\logs\\naming-distro.log");
//        ossClient.putObject("daizor-mall", "test.log", inputStream);
//
//// 关闭OSSClient。
//        ossClient.shutdown();
//        log.info("上传成功~");
//    }
//}
