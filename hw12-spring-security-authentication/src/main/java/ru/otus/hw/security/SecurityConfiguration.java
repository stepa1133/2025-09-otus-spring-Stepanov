package ru.otus.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests((authorize) -> authorize
//                                .requestMatchers("/").permitAll()
                               .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/authenticated", "/success").authenticated()
                                .anyRequest().authenticated()
                )
//                .anonymous(a -> a.principal(new AnonimusUD()).authorities("ROLE_ANONYMOUS"))
//                .httpBasic(Customizer.withDefaults())
               // .formLogin(Customizer.withDefaults())
                .formLogin(fm -> fm
                        .defaultSuccessUrl("/")
                        /*.failureForwardUrl("/fail")*/)
                .rememberMe(rm -> rm.key("AnyKey")
                        .tokenValiditySeconds(2000));
        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String [] arrPasswords = new String[] {"admin", "tests"};
        Arrays.stream(arrPasswords).map(passwordEncoder::encode).forEach(System.out::println);
    }

}
