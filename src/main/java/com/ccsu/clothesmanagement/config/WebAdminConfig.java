package com.ccsu.clothesmanagement.config;

import com.ccsu.clothesmanagement.interceptor.LoginIntercepter;
import com.ccsu.clothesmanagement.interceptor.UserManageIntercepter;
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
        registry.addInterceptor(userManageIntercepter())
                .addPathPatterns("/user.html"); //仅仅拦截用户管理请求
    }

    @Bean
    public LoginIntercepter loginIntercepter(){
        return new LoginIntercepter();
    }

    @Bean
    public UserManageIntercepter userManageIntercepter() {
        return new UserManageIntercepter();
    }
}
