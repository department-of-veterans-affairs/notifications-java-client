package gov.va.vanotify;

import java.io.UnsupportedEncodingException;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.lang.JoseException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class Authentication {

    public Authentication() {
    }

    public String create(String issuer, String secret) {

        try {
            JsonWebSignature jws = new JsonWebSignature();
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
            jws.setHeader(HeaderParameterNames.TYPE, "JWT");
            JwtClaims claims = new JwtClaims();
            claims.setIssuer(issuer);
            claims.setIssuedAtToNow();
            jws.setPayload(claims.toJson());
            jws.setKey(keyFromString(secret));

            return jws.getCompactSerialization();

        } catch (JoseException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static final String ENCODING = "UTF-8";

    public static SecretKey keyFromString(String value)
            throws UnsupportedEncodingException {
        return new SecretKeySpec(value.getBytes(ENCODING), "RAW");
    }
}
