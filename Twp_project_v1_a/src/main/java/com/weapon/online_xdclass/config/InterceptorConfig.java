package com.weapon.online_xdclass.config;

import com.weapon.online_xdclass.interceptor.CorsInterceptor;
import com.weapon.online_xdclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * 不用权限可以访问url /api/v1/pub/
 * 要登录可以访问url /api/v1/pri/
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    CorsInterceptor corsInterceptor(){return new CorsInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        /**
         * 拦截全部路径，这个  跨域  需要放在最上面的路径
         */
        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");

        //拦截全部
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                //不拦截哪些路径,斜杆一定要加  ，不然拦截会不成功
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register");

    }
}
