package com.oauth.client;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.util.Base64;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/30
 */
public class TestService {
    public static void main(String[] args) {
        Base64.Decoder decoder = Base64.getDecoder();
//        JwtParser parser = Jwts.parser();
//        String str = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA4NDkyMTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJiMWFjY2E5NS0wODIzLTRkOGEtYWM1YS02ZDI4NDNmZTI5NGMiLCJjbGllbnRfaWQiOiJ0ZXN0Y2xpZW50Iiwic2NvcGUiOlsidGVzdCJdfQ.LzxMDmXcPDaNlOqMeH32XkdvWCS_mduINNlTJHHQBeI";
//        Jwt parse = parser.parse(str);
//        Object body = parse.getBody();
//        Header header = parse.getHeader();
        String str1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String str2 = "eyJleHAiOjE1NDA4NDkyMTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJiMWFjY2E5NS0wODIzLTRkOGEtYWM1YS02ZDI4NDNmZTI5NGMiLCJjbGllbnRfaWQiOiJ0ZXN0Y2xpZW50Iiwic2NvcGUiOlsidGVzdCJdfQ";
        String str3 = "LzxMDmXcPDaNlOqMeH32XkdvWCS_mduINNlTJHHQBeI";
        byte[] decode = decoder.decode(str1);
        String s1 = new String(decode);
        System.out.println(s1);
//        System.out.println(header);
    }
}
