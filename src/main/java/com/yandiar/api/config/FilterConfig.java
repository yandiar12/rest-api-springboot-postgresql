package com.yandiar.api.config;


import com.yandiar.api.filter.JwtFilter;
import com.yandiar.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Jimmy Rengga
 */
@Configuration
public class FilterConfig {

    private static final String JWT_FILTER = "jwtFilter";
    
    @Autowired
    private AuthService authService;
    
    @Bean(name = JWT_FILTER)
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(authService));
        registrationBean.addUrlPatterns("/auth/secure/*");

        return registrationBean;
    }
}

