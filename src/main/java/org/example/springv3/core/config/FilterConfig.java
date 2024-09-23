package org.example.springv3.core.config;

import jakarta.servlet.FilterRegistration;
import org.example.springv3.core.filter.JwtAuthorizationFilter;
import org.example.springv3.user.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
  IoC: 제어의역전
 @Controller , @RestController, @Repository , @Component, @Configuration

 */

@Configuration //스프링 실행될때
public class FilterConfig {

    /* 문법
    public FilterConfig() {
       System.out.println("FilterConfig");
   }

    @Bean // return 값을 Ioc에 등록해준다.
    public User go () {
   System.out.println("ggggg");
       return User.builder().id(1).build();
   }

     */
    @Bean //리퀘스트 요청이 들어왔을 때
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> bean
                = new FilterRegistrationBean<>(new JwtAuthorizationFilter());

        bean.addUrlPatterns("/api/*");// ** 두개는 안 된다!!
        bean.setOrder(0);
        return bean;
    }

}
