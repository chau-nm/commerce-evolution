package dev.chaunm.commerceevolution.authentication.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                // TODO: remove once AuthenticationFilter is wired into this chain and
                                // populates SecurityContext. Customer identity is resolved via
                                // CurrentUserProvider in the meantime, so this endpoint group is
                                // deliberately left open rather than rejected by a filter that
                                // doesn't exist yet.
                                .requestMatchers("/api/v1/customers/**").permitAll()
                                // TODO: remove once AuthenticationFilter is wired into this chain and
                                // populates SecurityContext. Cart identity is resolved via
                                // CurrentUserProvider in the meantime, so this endpoint group is
                                // deliberately left open rather than rejected by a filter that
                                // doesn't exist yet.
                                .requestMatchers("/api/v1/cart/**").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
