package com.surya.dansbetest.util;

import com.surya.dansbetest.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    @Value("${jwt.key.private}")
    private String jwtPrivateKey;

    @Value("${jwt.key.public}")
    private String jwtPublicKey;

    @Value("${jwt.accessToken.expiration}")
    private Long jwtAccessTokenExpiration;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @PostConstruct
    public void constructSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        String privateKeyText = jwtPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\n", "");

        String publicKeyText = jwtPublicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyText));
        privateKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyText));
        publicKey = kf.generatePublic(keySpecX509);
    }


    public String generateJwtAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());

        return Jwts.builder()
                .addClaims(claims)
                .setIssuer(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessTokenExpiration))
                .signWith(privateKey)
                .compact();
    }

    public boolean isAuthenticated(String token) {
        Claims claims = getJwtClaims(token);


        if(claims.getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    private Claims getJwtClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
    }

}
