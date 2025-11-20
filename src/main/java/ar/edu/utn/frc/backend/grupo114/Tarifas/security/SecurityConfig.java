package ar.edu.utn.frc.backend.grupo114.tarifas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth

                // --- ENDPOINTS DE MS-TARIFAS ---
                .requestMatchers("/tarifas/**").hasRole("OPERADOR")
                .requestMatchers("/tarifas/calcular").hasAnyRole("OPERADOR", "CLIENTE")

                // Swagger sin token
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api-docs/**"
                ).permitAll()

                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt());

        return http.build();
    }
}
