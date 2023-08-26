package pl.jacek.coursebooking.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "aUJ4Q0lMjQ2VfexMijSijXOipbWNTMw0HSv50RVS/JIx79iolCkMR3PVIKntiKdf9GWDkXZTkA/ZdZw+dvfgT2aTcGS2xwzCnQedgMoG7KK8pSjkN4w9x7P3gx3KzYjYfGjZ+BHj/I7GcoyFR2XR8BjSYwtGJQwQBB6sLaSTiKKKGxvo+NYdcZQw5Nu/0LOKFaQzTcGK2eprx+s29p9Td3Fhn9i/eUIUlVFb/qIiuHXfX6k3MlHHjKc6G4H50WhYp2wz1EG7btFaF/uBiAyAS701jdJCuE0tBzNkPnBAW+KS0RanursGlw3HlMvUUQANg40SmrdHwLEgE/VdpDLrUhbAtRaWJTOL7Xae7Gw3F08=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(null, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 15))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
