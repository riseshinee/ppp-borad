package com.riseshine.pppboard;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api/*").permitAll()
                            .anyRequest().authenticated()


            )
            .httpBasic(withDefaults())
            .csrf().disable();

    return http.build();
  }
}