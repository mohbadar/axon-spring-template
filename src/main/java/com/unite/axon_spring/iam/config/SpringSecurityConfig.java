package com.unite.axon_spring.iam.config;

import com.unite.axon_spring.iam.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Configuration
    @Order(1)
    public static class JwtAuthSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomUserService customUserService;

        @Autowired
        private JwtAuthenticationEntryPoint unauthorizedHandler;

        private static final String[] AUTH_WHITE_LIST = {
                "/api/login",
            };

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // Add our custom JWT security filter
            http.addFilterBefore(jwtAuthenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

            http.cors().and().csrf().disable();
            http.antMatcher("/api/**").authorizeRequests().antMatchers(AUTH_WHITE_LIST).permitAll().anyRequest()
                    .authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // .and().authorizeRequests().antMatchers("/").permitAll().anyRequest()
            // .authenticated();
        }

        @Autowired
        public JwtAuthenticationFilter jwtAuthenticationTokenFilterBean() throws Exception {
            return new JwtAuthenticationFilter();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder.userDetailsService(customUserService).passwordEncoder(jwtPasswordEncoder());
        }

        public BCryptPasswordEncoder jwtPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        // @Autowired
        // public void globalUserDetails(AuthenticationManagerBuilder auth) throws
        // Exception {
        // // configure AuthenticationManager so that it knows from where to load
        // // user for matching credentials
        // // Use BCryptPasswordEncoder
        // auth.userDetailsService(customUserService).passwordEncoder(jwtPasswordEncoder());
        // }
    }

    @Configuration
    @Order(4)
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomUserService customUserService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable();
            http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/api/login").permitAll()
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/api/login");
        }

        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(customUserService);
            auth.setPasswordEncoder(passwordEncoder());
            auth.setHideUserNotFoundExceptions(false);
            return auth;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }
    }

}
