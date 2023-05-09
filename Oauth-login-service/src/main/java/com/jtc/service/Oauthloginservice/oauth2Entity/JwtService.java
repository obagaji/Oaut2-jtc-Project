package com.jtc.service.Oauthloginservice.oauth2Entity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
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
    private static  final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generateToken(Map<String, Object>extraClaims, UserDetails userDetails)
    {

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 24))
                .setSubject(userDetails.getUsername())
                .setIssuer("OauthLoginServiceApplication")
                .setClaims(extraClaims)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(PersonUser userDetails)
    {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 24))
                .setSubject(userDetails.getUsername())
                .setIssuer("OauthLoginServiceApplication")
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }
// extra username from the token
    public String getExtractedUserName(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isTokenValid(String token, PersonUser userDetails)
    {
        String username = getExtractedUserName(token);
        if (username.equals(userDetails.getUsername())&& !isTokenExpired(token))
        {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isTokenExpired(String token)
    {
        if (extractClaim(token, Claims::getExpiration).compareTo(new Date(System.currentTimeMillis()))>0)
        {
            return true;
        }
        else{
            return false;
        }
    }

    private Claims extraAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction)
    {
        final Claims claims = extraAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Key getSigningKey() {
            byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyByte);

    }
}
