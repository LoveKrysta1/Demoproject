package com.weapon.xdvideo.utils;

import com.weapon.xdvideo.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * jwt工具类
 */
public class JwtUtils {

    //发行者
    public static final String SUBJECT = "xdclass";
    //过期时间为一周
    public  static long EXPIRE = 1000*60*60*24*7;
    //设置一个秘钥，一般不能够泄漏
    public static final String APPSECRET = "xd168";

    public static String geneJsonWebToken(User user){
        //做某些校验
        if(user == null || user.getId() == null || user.getName() == null
        || user.getHeadImg() == null){
            return null;
        }

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try{
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                    .parseClaimsJws(token).getBody();
            return claims;
        }catch(Exception e){
            return null;
        }
    }
}
