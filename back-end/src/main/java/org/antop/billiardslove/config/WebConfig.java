package org.antop.billiardslove.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.antop.billiardslove.config.filter.HttpLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final ObjectMapper om;

    @Bean
    FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilter() {
        FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpLoggingFilter(om));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
