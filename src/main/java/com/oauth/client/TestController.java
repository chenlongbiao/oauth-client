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

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\sql.txt");
        InputStreamReader inputStream = new FileReader(file);
        BufferedReader bufferedReader1 = new BufferedReader(inputStream);
        StringBuilder sb1 = new StringBuilder();
        String orderId = null;
        while ((orderId = bufferedReader1.readLine()) != null) {
            sb1.append(orderId);
        }
        File file1 = new File("C:\\Users\\Administrator\\Desktop\\sss");
        InputStreamReader inputStream1 = new FileReader(file1);
        BufferedReader bufferedReader = new BufferedReader(inputStream1);
        StringBuilder sb2 = new StringBuilder();
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            sb2.append(lineTxt + "\r\n");
        }
        lineTxt = sb2.toString();
        lineTxt = lineTxt.replace("{}", "("+sb1.toString()+")");
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\newsql.txt");
        FileWriter fileWriter = new FileWriter(file2);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String trim = lineTxt.trim();
        bufferedWriter.write(trim);
        bufferedWriter.close();
        fileWriter.close();
    }


}