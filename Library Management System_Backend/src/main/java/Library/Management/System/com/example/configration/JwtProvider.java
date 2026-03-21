package Library.Management.System.com.example.configration;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@Service
public class JwtProvider {
    SecretKey key = keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){
        Collections<? extends GrantedAuthority> authorites = authentication
                .getAuthorities();

        String role = pupulateAuthorites(authorites);
        return  jwt.builder()
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorites",roles)
                .signedWith(key)
                .compact();

    }

    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);
        String Claims = jwt.parser()
                .verifyWith(key)
                .build()
                .parsedSignedClaims(jwt)
                .getPayload();
        return String.valueOf(claims.get("email"));

    }

    private  String poplutesAuthorities(Collection<? extends GrantedAuthority>authorities){
        set<String> autha = new HashSet<>();

        for(GrantedAuthority authority: authorities){
            authorites.add(authority.getAuthority());
        }

        return String.join(",",auths);
    };
}
