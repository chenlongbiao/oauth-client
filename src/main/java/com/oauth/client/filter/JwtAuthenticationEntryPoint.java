package com.oauth.client.filter;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/11/2
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
//        JSONObject result=new JSONObject();
//        JSONObject header=new JSONObject();
//        if(authException instanceof BadCredentialsException){ /**身份认证未通过*/
//            header.put("errorcode","8002");
//            header.put("errorinfo","用户名或密码错误，请重新输入！");
//            result.put("header",header);
//        }else{
        JsonParser jsonParser = JsonParserFactory.create();
        Map header = new HashMap();
        header.put("errorcode","8001");
        header.put("errorinfo","无效的token");
        String s = jsonParser.formatMap(header);
        response.getWriter().write(s);
    }
}
