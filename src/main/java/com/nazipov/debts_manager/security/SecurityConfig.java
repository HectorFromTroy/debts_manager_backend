package com.nazipov.debts_manager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazipov.debts_manager.dto.SampleResponseDto;
import com.nazipov.debts_manager.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DetailsService detailsService;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SecurityConfig(
            DetailsService detailsService,
            AuthenticationExceptionHandler authenticationExceptionHandler) {
        this.detailsService = detailsService;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .authorizeRequests()
                    .antMatchers("/signup").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic().authenticationEntryPoint(authenticationExceptionHandler)
                .and()
                    .csrf().disable()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                        SampleResponseDto<?> sampleResponseDto = new SampleResponseDto<>();
                        sampleResponseDto.setStatus(true);
                        httpServletResponse
                                .getWriter()
                                .write(objectMapper.writeValueAsString(sampleResponseDto));
                    });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService).passwordEncoder(new CustomPasswordEncoder());//.passwordEncoder(User.PASSWORD_ENCODER);
    }

    public static class CustomPasswordEncoder implements PasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return String.valueOf(rawPassword);
        }
        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return String.valueOf(rawPassword).equals(encodedPassword);
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of(
                "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"
        ));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
