package com.ccsu.clothesmanagement.config;

import com.ccsu.clothesmanagement.interceptor.LoginIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAdminConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercepter())
                .addPathPatterns("/**")  //所有请求都被拦截
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**",
                        "/js/**");   // 放行请求
    }

    @Bean
    public LoginIntercepter loginIntercepter(){
        return new LoginIntercepter();
    }
}
