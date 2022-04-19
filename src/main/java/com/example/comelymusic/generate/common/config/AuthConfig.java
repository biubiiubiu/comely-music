package com.example.comelymusic.generate.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description: 鉴权配置，通过注入的拦截器完成鉴权
 *
 * @author: zhangtian
 * @since: 2022-04-19 09:36
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor initAuthInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只有 /generate/login 这个接口的请求不被拦截，后续还可以配置其它不被拦截的接口（路人权限）
//        registry.addInterceptor(initAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/generate/login");
        // 所有接口都可以无身份访问
        registry.addInterceptor(initAuthInterceptor()).excludePathPatterns("/**");
    }

}
