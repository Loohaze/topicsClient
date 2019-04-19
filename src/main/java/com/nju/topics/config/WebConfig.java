package com.nju.topics.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }

    // 获取配置文件中图片的路径
//    @Value("${web.upload-path}")
//    private String mImagesPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

        // 访问图片方法
//        registry.addResourceHandler("/showImages/**").addResourceLocations(mImagesPath);


        super.addResourceHandlers(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("11");
        this.applicationContext = applicationContext;
    }





//    // 访问图片方法
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (mImagesPath.equals("") || mImagesPath.equals("${web.upload-path}")) {
//            String imagesPath = WebConfig.class.getClassLoader().getResource("").getPath();
//            if (imagesPath.indexOf(".jar") > 0) {
//                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
//            } else if (imagesPath.indexOf("classes") > 0) {
//                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
//            }
//            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
//            mImagesPath = imagesPath;
//        }
//        LoggerFactory.getLogger(WebConfig.class).info("imagesPath=" + mImagesPath);
//        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
//        super.addResourceHandlers(registry);
//    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.of(80*1024*1024, DataUnit.BYTES));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.of(100*1024*1024,DataUnit.BYTES));
        return factory.createMultipartConfig();
    }

}