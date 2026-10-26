package oit.is.z3384.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class JankenAuthConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
      .permitAll())
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/"))
      .authorizeHttpRequests(authz -> authz
        .requestMatchers("/janken/**").authenticated()
        .anyRequest().permitAll())
      .csrf(csrf -> csrf
        .ignoringRequestMatchers("/h2-console/*"))
      .headers(headers -> headers
        .frameOptions(frameOptions -> frameOptions.sameOrigin()));
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user1 = User.withUsername("user1")
      .password("{bcrypt}$2y$10$0badkgXUfRiaydqcxAL7SeSbFpKprGNq7bJCTcobwiRP9F4iEDIZS").roles("USER").build();
    UserDetails user2 = User.withUsername("user2")
      .password("{bcrypt}$2y$10$Oz3MlaSQI/OKxLDREsIe1unB2JdyP8bzphd6Rff/hs90cgxmW0TI.").roles("USER").build();
    UserDetails honda = User.withUsername("ほんだ")
      .password("{bcrypt}$2y$10$Ycb6O/Qyqqv91ZDocZyXHeZ2j//OeSa6m1K1H4qc0sTNqPDrir4ee").roles("USER").build();

    return new InMemoryUserDetailsManager(user1, user2, honda);
  }
}
