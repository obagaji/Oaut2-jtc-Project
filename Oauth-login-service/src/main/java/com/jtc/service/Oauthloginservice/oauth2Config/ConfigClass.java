package com.jtc.service.Oauthloginservice.oauth2Config;

import com.jtc.service.Oauthloginservice.oauth2Entity.OauthAuthenticalFilter;
import com.jtc.service.Oauthloginservice.oauth2Entity.PersonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@PropertySource({"classpath:application.properties"})
public class ConfigClass {
    @Autowired
    private Environment environment;

    @Autowired
    private OauthAuthenticalFilter oauthAuthenticalFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/registration/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(oauthAuthenticalFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .and()
               .formLogin();
        return httpSecurity.build();
    }
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new PersonUserService();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        return daoAuthenticationProvider();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration googleClient =
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId(environment.getProperty("spring.security.oauth2.registration.google.clientId"))
                        .clientSecret(environment.getProperty("spring.security.oauth2.registration.google.clientSecret"))
                        .build();
        ClientRegistration githubClient =

                CommonOAuth2Provider.GITHUB.getBuilder("github")
                        .clientId("spring.security.oauth2.registration.github.clientId")
                        .clientSecret("spring.security.oauth2.registration.github.clientSecret")
                        .build();
        return new InMemoryClientRegistrationRepository(googleClient, githubClient);
    }
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }
    //App ID: 320479
    //
    //Client ID: Iv1.954870c95863deff
    //client : 0b52e2130949e271b2cd63acda9d241fe9965bac


    //google
    //clientid:214309837753-nmlupmd4e20dsmr9fdktjqju8po29mnf.apps.googleusercontent.com
    //clientsecret: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

}
