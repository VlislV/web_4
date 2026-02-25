package beans;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ejb.Stateless;

import javax.crypto.SecretKey;
import java.util.Date;

@Stateless
public class Tokenizer {
    final String str_key = "SPECIALSECRETRANDOMSYMBOLSSPECIALSECRETRANDOMSYMBOLS";
    private final SecretKey key = Keys.hmacShaKeyFor(
            str_key.getBytes()
    );

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) throws JwtException {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
    }
