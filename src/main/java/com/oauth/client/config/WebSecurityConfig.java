package com.oauth.client.config;
import com.oauth.client.filter.JwtAuthenticationEntryPoint;
import com.oauth.client.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Order(1)
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(securedEnabled = true)
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()//未授权处理

                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()


                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/token").permitAll()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    public JwtAuthenticationEntryPoint unauthorizedHandler() throws Exception {
        return new JwtAuthenticationEntryPoint();
    }
}
