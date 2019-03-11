package com.chen.oauth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/29
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }


//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http.requestMatcher(new OAuthRequestedMatcher()).authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyRequest().authenticated();
//        // @formatter:on
//    }
//
//    private static class OAuthRequestedMatcher implements RequestMatcher {
//        public boolean matches(HttpServletRequest request) {
//            String auth = request.getHeader("Authorization");
//            // Determine if the client request contained an OAuth Authorization
//            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
//            boolean haveAccessToken = request.getParameter("access_token") != null;
//            return haveOauth2Token || haveAccessToken;
//        }
//    }

        @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }



}
