package com.flockinger.spongeblogui.security;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Profile("default")
@Configuration
@EnableWebSecurity
public class BlogSecurityConfig extends WebSecurityConfigurerAdapter {

	   @Value("${google.client.clientId}")
	    private String clientId;
	 
	    @Value("${google.client.clientSecret}")
	    private String clientSecret;
	 
	    @Value("${google.client.accessTokenUri}")
	    private String accessTokenUri;
	 
	    @Value("${google.client.userAuthorizationUri}")
	    private String userAuthorizationUri;
	 
	    @Value("${google.redirectUri}")
	    private String redirectUri;
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web
    	.ignoring().antMatchers(HttpMethod.GET, "/category/**").and()
    	.ignoring().antMatchers(HttpMethod.GET, "/user/**").and()
    	.ignoring().antMatchers(HttpMethod.GET, "/tag/**").and()
    	.ignoring().antMatchers(HttpMethod.GET, "/post/**").and()
    	.ignoring().antMatchers(HttpMethod.GET, "/");
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilterAfter(new OAuth2ClientContextFilter(),AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterAfter(ssoFilter(), OAuth2ClientContextFilter.class)
				.httpBasic()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				.and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/login").permitAll()
				.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.PUT, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.DELETE, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/").permitAll().and()
		        .csrf();
	}
	
	private Filter ssoFilter() {
		OpenIdFilter filter = new OpenIdFilter("/login", authenticationManager);
		OAuth2RestTemplate template = new OAuth2RestTemplate(google(), oauth2ClientContext);
		filter.setRestTemplate(template);
		return filter;
	}
	
    @Bean
    public OAuth2ProtectedResourceDetails google() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("openid", "email"));
        details.setPreEstablishedRedirectUri(redirectUri);
        details.setUseCurrentUri(false);
        return details;
    }
}
