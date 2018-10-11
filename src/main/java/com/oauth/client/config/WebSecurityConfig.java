package com.oauth.client.config;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers("/login.html")
        .antMatchers("/**/*.html")
        .antMatchers( "/**/*.js")
        .antMatchers( "/**/*.css")
        .antMatchers( "/**/ui")
        .antMatchers( "/**/swagger-resources**/**")
        .antMatchers( "/**/v2/**")
        .antMatchers( "/**/*.png");
    }
}
