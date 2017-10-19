package fi.ficora.lippu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Configuration for spring websecurity. Allows non-authenticated requests
 * for authentication endpoint, requires token authentication for
 * others.
 * @author markkuko
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthorizationFilter filter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/auth/**","/",
                        "/swagger-ui.html","/swagger-resources/**","/api-docs", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(filter, BasicAuthenticationFilter.class);

    }
    /*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }*/

}
