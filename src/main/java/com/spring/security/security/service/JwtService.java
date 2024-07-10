package com.spring.security.security.service;

import com.spring.security.enums.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("classpath:${jwtKeys.private}")
    private Resource privateKeyResource;

    @Value("classpath:${jwtKeys.public}")
    private Resource publicKeyResource;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public String createToken(String subject, Set<Role> roles) {

        String rolesString = roles.stream()
                .map(Role::name)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(subject)
                .claim("roles", rolesString) // claims visibles en el token
                .issuedAt(new Date(System.currentTimeMillis())) // hora de creación del token
                .expiration(new Date(System.currentTimeMillis() + 600000)) // hora de expiración del token
                .signWith(SignatureAlgorithm.RS256, privateKey) // algoritmo de encriptación y clave secreta
                .compact();
    }

    @PostConstruct
    public void init() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyResource.getFile().toPath());
        String privateKeyPem = new String(privateKeyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);

        byte[] publicKeyBytes = Files.readAllBytes(publicKeyResource.getFile().toPath());
        String publicKeyPem = new String(publicKeyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKeyPem);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodedPublicKey);
        this.publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
    }
}
