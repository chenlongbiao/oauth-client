package com.chen.oauth.client.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2019/3/11
 */
public class ProvideUtil {

    public static Authentication getAuthentication(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }
}
