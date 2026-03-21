package Library.Management.System.com.example.configration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        //Bearer
        if(jwt != null){
            jwt = jwt.substring(7);

            try{
                SecretKey keys=keys.hmaShakeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parser().verifyWith(key).build()
                        .parseSignedClaims(jwt).getPayload();

                String email = claims.valueOf(claims.get("email"));
                String authories = String.valueOf(claims.get('authorites'));
                List<GrandAuthority> authoritiesList = AuthorityUtils
                        .commaSeparatedStringToAuthority(authories);

                Authentication auth = new UserNamePasswordAuthenticationToken(
                        email.getSubject(),null,authoritiesList);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                throw  new BadCredentialsExpection("Invalid.... JWT Token");
            }
            filterChain.doFilter(request,response);
        }
    }
}
