package com.niemczuk.taskmanagerbackend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.niemczuk.taskmanagerbackend.model.dto.authentication.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class JwtUtility {
    private static final String JWT_SECRET = "The biggest secret";
    private static final String ISSUER = "task-manager-backend";
    private static final int JWT_EXPIRATION_TIME = 15 * 60 * 1000; // milliseconds

    public static String generateJwtToken(UserDetails userDetails) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME);
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDetails.getUsername())
                    .withClaim("role", userDetails.getAuthorities().toString())
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

    public static boolean tokenVerify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            log.error("Invalid token");
            return false;
        }
    }

    public static String getSubject(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException exception) {
            return null;
        }
    }

    public static JwtUserDetails getJwtUserDetails(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(jwt.getClaims().get("role").toString()));

            return JwtUserDetails.builder()
                    .username(jwt.getSubject())
                    .authorities(authorities)
                    .build();
        } catch (JWTDecodeException exception){
            return null;
        }
    }


}
