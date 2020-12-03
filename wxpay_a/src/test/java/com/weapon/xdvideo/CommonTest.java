package com.weapon.xdvideo;

import com.weapon.xdvideo.domain.User;
import com.weapon.xdvideo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

public class CommonTest {
    @Test
    public void testGeneJwt(){
        User user = new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("kkk");

        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public  void testCheckJwt(){
        String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6ImtrayIsImltZyI6Ind3dy54ZGNsYXNzLm5ldCIsImlhdCI6MTYwMjEyOTE3OSwiZXhwIjoxNjAyNzMzOTc5fQ.puz0kTuCdoB6s75WG0L85C3bH6xVIfwoeYcCn4ue8Cg";
        Claims claims = JwtUtils.checkJWT(token);
        if(claims !=null){
            String name = (String)claims.get("name");
            int id = (Integer)claims.get("id");
            String img = (String)claims.get("img");
            System.out.println(name+" "+id+" "+img);
        }else{
            System.out.println("非法token");
        }
    }
}
