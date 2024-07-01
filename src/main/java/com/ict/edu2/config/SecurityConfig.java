package com.ict.edu2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ict.edu2.jwt.JWTUtil;
import com.ict.edu2.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;
    private OAuth2AuthenticaitonSuccessHandler oAuth2AuthenticaitonSuccessHandler;
       
    public SecurityConfig(UserDetailsService userDetailsService, 
            JwtRequestFilter jwtRequestFilter, OAuth2AuthenticaitonSuccessHandler oAuth2AuthenticaitonSuccessHandler){
        this.userDetailsService = userDetailsService ;
        this.jwtRequestFilter = jwtRequestFilter;
        this.oAuth2AuthenticaitonSuccessHandler = oAuth2AuthenticaitonSuccessHandler ;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/login", "/api/logout", "/api/guest","/api/oauth2/**", "/api/userInfo").permitAll()
                        .anyRequest().authenticated()
        )
        .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })
        )
        // oauth2Login 로그인 설정
        // successHandler => 로그인 성공시 호출
        // userInfoEndpoint => OAuth2 인증과정에서 인증된 사용자에 대한 정보를 제공하는 API 엔드포인드 (사용자의 세부정보를 가져오는 역할)
        .oauth2Login(oauth2 -> oauth2
                    .successHandler(oAuth2AuthenticaitonSuccessHandler)
                    .userInfoEndpoint(userInfo -> userInfo
                        .userService(oAuth2UserService())
                    )
        )

        // 먼저 토큰 검사 
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }    

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean    
     CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // CustomOAuth2userService 클래스는 사용자 정보를 가져오는 로직을 사용자 정의할 수 있는 클래스 
    // CustomOAuth2userService 클래스는 OAuth2UserService를 상속 받는 클래스이다.
    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(){
        return new CustomOAuth2userService();
    }
}
