package com.construction.appconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@SuppressWarnings("deprecation")
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/**").authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/**/image/**",
                        "/files/**",
                        "/image/**").permitAll()
                .antMatchers("/health/isloggedin").hasIpAddress("192.168.1.254")
                .antMatchers("/health", "/api/**", "/swagger-resources/**", "/v2/**").permitAll()
                .antMatchers(HttpMethod.POST, "/feedback").permitAll()
                .anyRequest().authenticated();
    }
}
