/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.common;

import com.yandiar.api.model.UserToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.annotation.Nonnull;

/**
 *
 * @author USER
 */
public class TokenUtil {
    public static String buildJWTToken(@Nonnull UserToken dto) {
        return Jwts.builder()
                .setSubject(AppServer.encodeUserDto(dto))
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, AppServer.JWT_SECRET_KEY)
                .compact();
    }
}
