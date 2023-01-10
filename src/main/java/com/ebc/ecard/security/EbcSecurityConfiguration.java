package com.ebc.ecard.security;

import com.ebc.ecard.security.entrypoint.EbcAuthenticationEntryPoint;
import com.ebc.ecard.security.filter.*;
import com.ebc.ecard.security.provider.AccessOpenApiAuthenticationProvider;
import com.ebc.ecard.security.provider.EbcClientAuthenticationProvider;
import com.ebc.ecard.security.provider.EbcUserAuthenticationProvider;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class EbcSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(
            "/",
            "/healthy",
            "/error",
            "/favicon.ico",
            "/api/auth/login",
            "/api/auth/refresh",
            "/js/**",
            "/css/**",
            "/img/**",
            "/font/**",
            "/svg/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .anonymous().disable()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(ebcAuthenticationEntryPoint())
            .and()
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(accessTokenAuthenticationFilter().getFilter(), CsrfFilter.class)
            .addFilterAfter(accessOpenApiAuthenticationFilter().getFilter(), AccessTokenAuthenticationFilter.class)
            .addFilterAfter(ebcClientAuthenticationFilter().getFilter(), AccessTokenAuthenticationFilter.class)
            .addFilterAfter(loginRequiredAuthenticationFilter().getFilter(), AccessTokenAuthenticationFilter.class)
            .addFilterAfter(ebcAnonymousAuthenticationFilter().getFilter(), LoginRequiredAuthenticationFilter.class);


    }

    public FilterRegistrationBean<AccessTokenAuthenticationFilter> accessTokenAuthenticationFilter() throws Exception {
        AccessTokenAuthenticationFilter filter = new AccessTokenAuthenticationFilter(authenticationManager(),
                ebcAuthenticationEntryPoint());

        FilterRegistrationBean<AccessTokenAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    public FilterRegistrationBean<AccessOpenApiAuthenticationFilter> accessOpenApiAuthenticationFilter() throws Exception {
        AccessOpenApiAuthenticationFilter filter = new AccessOpenApiAuthenticationFilter(
                authenticationManager(),
                ebcAuthenticationEntryPoint()
        );

        FilterRegistrationBean<AccessOpenApiAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    public FilterRegistrationBean<EbcClientAuthenticationFilter> ebcClientAuthenticationFilter() throws Exception {
        EbcClientAuthenticationFilter filter = new EbcClientAuthenticationFilter(
                authenticationManager(),
                ebcAuthenticationEntryPoint()
        );

        FilterRegistrationBean<EbcClientAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    public FilterRegistrationBean<EbcAnonymousAuthenticationFilter> ebcAnonymousAuthenticationFilter()
            throws Exception {
        EbcAnonymousAuthenticationFilter filter = new EbcAnonymousAuthenticationFilter(authenticationManager(),
                ebcAuthenticationEntryPoint());

        FilterRegistrationBean<EbcAnonymousAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    public FilterRegistrationBean<LoginRequiredAuthenticationFilter> loginRequiredAuthenticationFilter()
            throws Exception {
        LoginRequiredAuthenticationFilter filter = new LoginRequiredAuthenticationFilter(authenticationManager(),
                ebcAuthenticationEntryPoint());

        FilterRegistrationBean<LoginRequiredAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    protected EbcUserAuthenticationProvider ebcUserAuthenticationProvider() {
        return new EbcUserAuthenticationProvider();
    }

    @Bean
    protected EbcClientAuthenticationProvider ebcClientAuthenticationProvider() {
        return new EbcClientAuthenticationProvider();
    }

    @Bean
    protected AccessOpenApiAuthenticationProvider accessOpenApiAuthenticationProvider() {
        return new AccessOpenApiAuthenticationProvider();
    }

    @Bean
    protected EbcAuthenticationEntryPoint ebcAuthenticationEntryPoint() {
        return new EbcAuthenticationEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(accessOpenApiAuthenticationProvider());
        auth.authenticationProvider(ebcUserAuthenticationProvider());
        //auth.authenticationProvider(ebcClientAuthenticationProvider());
    }
}
