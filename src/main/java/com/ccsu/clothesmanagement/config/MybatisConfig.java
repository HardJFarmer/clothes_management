package com.ccsu.clothesmanagement.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {
    /**配置PageInterceptor分页插件*/
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageIntercptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("value", "true");
        properties.setProperty("reasonable", "true");
        pageIntercptor.setProperties(properties);
        return pageIntercptor;
    }
}
