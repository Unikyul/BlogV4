package org.example.springv3.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.springv3.user.User;

import java.util.Date;

public class JwtUtil {

    public static String create(User user){
        String accessToken = JWT.create()
                .withSubject("바보")
                //유효시간 정하기 7일을 줬다 토큰을 잃어버리면 7일동안 아무것도 할 수 없이 털린다.
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("id", user.getId())// payload 자리
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding"));//SIGNATURE 자리
        return accessToken;
    }

    public static User verify(String jwt){
        jwt = jwt.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("metacoding")).build().verify(jwt);
        int id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();

        return User.builder()
                .id(id)
                .username(username)
                .build();
    }

}
