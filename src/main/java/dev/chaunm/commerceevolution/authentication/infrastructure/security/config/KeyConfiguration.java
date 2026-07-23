package dev.chaunm.commerceevolution.authentication.infrastructure.security.config;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class KeyConfiguration {
    public final String KEY_PAIR_ALGORITHM = "RSA";

    @Value("${app.jwt.private-key}")
    private Resource privateKeyResource;

    @Value("${app.jwt.public-key}")
    private Resource publicKeyResource;

    @Bean
    public RSAPublicKey publicKey() throws
            IOException,
            NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        String pem = readResource(publicKeyResource);

        pem = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(pem);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

        KeyFactory factory = KeyFactory.getInstance(KEY_PAIR_ALGORITHM);

        return (RSAPublicKey) factory.generatePublic(spec);
    }

    @Bean
    public RSAPrivateKey privateKey() throws
            IOException,
            NoSuchAlgorithmException,
            InvalidKeySpecException
    {
        String pem = readResource(privateKeyResource);

        pem = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(pem);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        KeyFactory factory = KeyFactory.getInstance(KEY_PAIR_ALGORITHM);

        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    @Bean
    public RSAKey jwk(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
    }

    private String readResource(Resource resource) throws IOException {
        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
