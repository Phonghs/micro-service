package com.example.gateway.utils;

import com.example.common.proxy.user.payload.response.UserProfileResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private final  JwtProperties jwtProperties;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "server.security.authentication.jwt")
    public static class JwtProperties implements Serializable {
        /**
         * The total seconds that a token keep being valid after its creation.
         */
        private Long validitySeconds;
        /**
         * The secret key used in encoding-decoding algorithm.
         */
        private String secretKey;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private <T> T safeGetClaimFromToken(String token, Function<Claims, T> claimsResolver, T defaultValue) {
        try {
            return getClaimFromToken(token, claimsResolver);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * @param token the token.
     * @return the expiration of input token.
     */
    private Date getExpirationFromToken(String token) {
        return safeGetClaimFromToken(token, Claims::getExpiration, null);
    }

    /**
     * check if token a token has been expired.
     * @param token the token.
     * @return {@code true} if the token is expired, otherwise {@code false}
     */
    private boolean isTokenExpired(String token) {
        return Optional.ofNullable(getExpirationFromToken(token))
            .map(e -> e.before(new Date())).orElse(true);

    }

    /**
     *  Retrieves the username from a token.
     * @param token the token.
     */
    public String getUsernameFromToken(String token) {
        return safeGetClaimFromToken(token, Claims::getSubject, null);
    }

    public boolean isTokenValid(String token, UserProfileResponse userDetails) {
        if(Objects.isNull(userDetails)) return false;
        String username = getUsernameFromToken(token);
        String userName = userDetails.getUsername();
        return Objects.equals(username, userName)
            && !isTokenExpired(token);
    }

    /**
     * generates a token for user.
     * @param userDetails the {@link UserDetails} of user.
     * @return a string token.
     */
    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+jwtProperties.getValiditySeconds()*MILLISECONDS_PER_SECOND))
            .signWith(SignatureAlgorithm.HS512, jwtProperties.secretKey)
            .compact();
    }


}
