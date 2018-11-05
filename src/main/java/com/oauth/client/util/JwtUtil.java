package com.oauth.client.util;

import com.oauth.client.SysRole;
import com.oauth.client.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/30
 */
@Component
public class JwtUtil {

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }

    public SecretKey generalKey(){
        String stringKey = "MTIz";//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
//        System.out.println(encodedKey);//[B@152f6e2
//        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节
        return key;
    }

    public static void main(String[] args) throws Exception {
        JwtUtil jwtUtil = new JwtUtil();
//        String str = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDA5MjQ0MjYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJiM2QzYzlhMC1iMDlkLTRjYjktODFkMC03NzFjOTc3ZjZhYjYiLCJjbGllbnRfaWQiOiJ0ZXN0Y2xpZW50Iiwic2NvcGUiOlsidGVzdCJdfQ.MoBXSUnI06hUaQvMVxTbqmdAEC73dldQPHKC8w3-lPA";
        String str = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDExODY5MjIsInVzZXJfbmFtZSI6IjEyMyIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX0NIQVQiXSwianRpIjoiNWRiN2Y0ODMtNTk1My00YWE2LTkyOGItMDk3YmRkZWFlYmZlIiwiY2xpZW50X2lkIjoiU2FtcGxlQ2xpZW50SWQiLCJzY29wZSI6WyIkMmEkMTAkVnk3NUxOSFBuWVprWURKVkdqRDJJLlVpUXBCMTJFbmYzNlkzR1lFWkQ1Y0R3eEtnYUVMak8iXX0.nzIWbQ19OX7TIAxLy-mWM6CN9MGldEwfRFpWQlip6oY";
        Claims claims = jwtUtil.parseJWT(str);
        String[] split = str.split(".");
        String s = split[1];
        byte[] bytes = Base64.decodeBase64(s);
        String s1 = new String(bytes);
        System.out.println(s1);
        System.out.println(claims);
    }

    public String getUserAccountFromToken(String token) {
        String useraccount;
        try {
            final Claims claims = getClaimsFromToken(token);
            useraccount = claims.getSubject();
        } catch (Exception e) {
            useraccount = null;
        }
        return useraccount;
    }
    public SysUser getUser(String token) {
        String useraccount;
        final Claims claims = getClaimsFromToken(token);
        useraccount = claims.getSubject();
        SysUser sysUser = new SysUser();
        List roles = new ArrayList();
        SysRole sysRole = new SysRole();
        List<String> authorities =(List)(claims).get("authorities");
        for (String ss: authorities){
            sysRole.setName(ss);
        }
        roles.add(sysRole);
        sysUser.setRoles(roles);
        sysUser.setUsername(useraccount);
        return sysUser;
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String useraccount = getUserAccountFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        Boolean result= (
                useraccount.equals(userDetails.getUsername())
                        && !isTokenExpired(token)
        );
        return result;
    }
    /**
     * 获取token的过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * 从token中获取创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get("created"));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        Boolean result= expiration.before(new Date());
        return result;
    }
}
