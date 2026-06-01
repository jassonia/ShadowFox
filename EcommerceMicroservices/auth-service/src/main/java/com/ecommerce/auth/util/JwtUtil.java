package com.ecommerce.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY =
            "myverysecuresecretkeyforjwtauthentication2026";

    public static String generateToken(String email) {

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )

                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY.getBytes()
                )

                .compact();
    }
}