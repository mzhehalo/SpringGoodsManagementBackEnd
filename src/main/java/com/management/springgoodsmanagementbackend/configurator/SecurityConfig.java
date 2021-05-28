package com.management.springgoodsmanagementbackend.configurator;

import com.management.springgoodsmanagementbackend.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/cart/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/order-info/get/**").hasAnyRole("SELLER", "CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/order-info/add/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/order-info/delete/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/update/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/delete/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/statistics/**").hasAnyRole("ADMIN", "SELLER")
                .antMatchers(HttpMethod.POST, "/wishlist/add").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/wishlist/delete/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/user/admin/get/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/admin/delete/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/product/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/product/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/product/**").hasAnyRole("SELLER", "ADMIN")
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4300")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
