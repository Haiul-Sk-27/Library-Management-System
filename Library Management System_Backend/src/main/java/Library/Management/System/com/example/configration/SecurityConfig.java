package Library.Management.System.com.example.configration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .sessionManagment(managment -> sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequest(Authroze -> Authroze
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )

                .addFilterBefore(new JwtValidator(),BasisAutenticatedFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors ->cors.configurationSource(corsConfiguration()))
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        return new CorsConfiguration(){
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowCredentials(true);
                cfg.setAllowedOrigins(
                        Arrays.asList(
                                "http://localhost:5173/"
                        )
                );
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Collections.singletonList("Authorization"));
                cfg.setMaxAge(360L);
                return cfg;
            }
        }
    }

    @Bean
    public passwordEncoder passwordEncoder(){
        return new BcryptPasswordEncoder();
    }
}
