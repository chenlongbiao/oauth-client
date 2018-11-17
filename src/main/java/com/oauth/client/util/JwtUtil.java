package com.oauth.client.util;

import com.oauth.client.domain.SysRole;
import com.oauth.client.domain.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/30
 */
@Component
public class JwtUtil {

    @Value("${jwt.stringKey}")
    private String key;
    @Value("${jwt.header}")
    private String tokenHeader;

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
        String stringKey = key;//本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
//        System.out.println(encodedKey);//[B@152f6e2
//        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节
        return key;
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

    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String str = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjMiLCJ1c2VyX25hbWUiOiIxMjMiLCJjcmVhdGVkIjoxNTQxNjYwODg5Njg1LCJzY29wZSI6WyIkMmEkMTAkVnk3NUxOSFBuWVprWURKVkdqRDJJLlVpUXBCMTJFbmYzNlkzR1lFWkQ1Y0R3eEtnYUVMak8iXSwiZXhwIjoxNTQxNjYyNjg3LCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9DSEFUIl0sImp0aSI6IjQ5YWZkNGFhLTBmZTMtNGQxZS1iNjllLWE0YTZlNDkxYmFmNCIsImNsaWVudF9pZCI6IlNhbXBsZUNsaWVudElkIn0.Tdc2mk1owpT-jQp0AvOvijlePkDz-xAw5Eh_P_wvw3g";
        Date expirationDateFromToken = jwtUtil.getCreatedDateFromToken(str);
        Boolean tokenExpired = jwtUtil.isTokenExpired(str);
        Date a = jwtUtil.getExpirationDateFromToken(str);
        System.out.println(a);
        System.out.println(tokenExpired);
        long ss= 1541453384l;
        Date date = new Date(ss);
        System.out.println(date + "------------------");
    }
}
