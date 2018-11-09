package com.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/9
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "1234";
    }

    @GetMapping("/user")
    public Object getUser(Authentication authentication, HttpServletRequest request) {
        log.info("auth : {}", authentication);
        HttpSession session = request.getSession();
        return authentication;
    }

    @GetMapping("/logins")
    public String getUser(Map map) {
        log.info("auth : {}", map);
        return "ttt";
    }
}