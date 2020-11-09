/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yandiar.api.common;

import com.yandiar.api.model.UserToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author USER
 */
@Slf4j
public class TokenUtil {
    public static String buildJWTToken(@Nonnull UserToken dto) {
        return Jwts.builder()
                .setSubject(AppServer.encodeUserDto(dto))
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + AppServer.JWT_EXPIRED))
                .signWith(SignatureAlgorithm.HS256, AppServer.JWT_SECRET_KEY)
                .compact();
    }
    
    public static String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(AppServer.JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
    
    public static boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(AppServer.JWT_SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new ExpiredJwtException(null, null, "JWT token is expired", e);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException("JWT token is unsupported", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException("JWT claims string is empty", e);
        }

    }
}
