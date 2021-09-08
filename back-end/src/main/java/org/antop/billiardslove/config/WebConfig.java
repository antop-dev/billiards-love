package org.antop.billiardslove.config;

import org.antop.billiardslove.config.filter.HttpLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilter() {
        FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
