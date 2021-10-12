package com.city.oa.config;

import com.city.oa.filter.AdminFilter;
import com.city.oa.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean thirdFilter(){
        LoginFilter loginFilter = new LoginFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(loginFilter);
        List<String > urls = new ArrayList<>();
        urls.add("/shopper/*");
        urls.add("/admin/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
    @Bean
    public FilterRegistrationBean thirdFilter2(){
        AdminFilter adminFilter = new AdminFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(adminFilter);
        List<String > urls = new ArrayList<>();
        urls.add("/admin/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
