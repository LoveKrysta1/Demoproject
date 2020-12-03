package com.weapon.online_xdclass;

import com.weapon.online_xdclass.model.entity.User;
import com.weapon.online_xdclass.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class OnlineXdclassApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testGenJwt(){
		User user = new User();
		user.setId(66);
		user.setName("xkokokokokokokokoko");
		user.setHeadImg("png");
		String token = JWTUtils.geneJsonWebToken(user);
		System.out.println(token);

		Claims claims = JWTUtils.checkJWT(token);
		System.out.println(claims.get("name"));


	}

}
