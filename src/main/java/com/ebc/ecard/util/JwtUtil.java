package com.ebc.ecard.util;

import com.ebc.ecard.application.auth.dto.AccessTokenPayloadDto;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtil {

    private static final String JWT_SECRET = "<secret>";

    private static final int EXPIRATION_MILLISECONDS = (60 * 60 * 1000); // 1hr

    public static String generateUserToken(AccessTokenPayloadDto dto) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRATION_MILLISECONDS);

        return Jwts.builder()
                .setSubject(dto.getUserId())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public static String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static Date getTokenExpiration(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public static boolean validateToken(String token) throws ExpiredJwtException {
        if (token == null) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}