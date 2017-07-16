package com.flockinger.spongeblogui.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Profile("default")
@Configuration
@EnableWebSecurity
public class BlogSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Autowired
	AuthoritiesExtractor authoritiesExtractor;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
				// .addFilterAfter(new OAuth2ClientContextFilter(),
				// AbstractPreAuthenticatedProcessingFilter.class)
				// .addFilterAfter(filter,
				// OAuth2ClientContextFilter.class)
				// .httpBasic()
				// .authenticationEntryPoint(new
				// LoginUrlAuthenticationEntryPoint("/google-login"))
				// .and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/admin/login").permitAll()
				.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.PUT, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.DELETE, "/admin/**").hasAnyAuthority("ADMIN", "AUTHOR")
				.antMatchers(HttpMethod.GET, "/**").permitAll().and().logout().logoutSuccessUrl("/").permitAll().and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
		        .csrf();
	}

	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(
				"/admin/login");
		OAuth2RestTemplate template = new OAuth2RestTemplate(github(), oauth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(),
				github().getClientId());
		tokenServices.setRestTemplate(template);
		tokenServices.setAuthoritiesExtractor(authoritiesExtractor);
		filter.setTokenServices(tokenServices);
		
		return filter;
	}

	@Bean
	@ConfigurationProperties("github.client")
	public AuthorizationCodeResourceDetails github() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("github.resource")
	public ResourceServerProperties githubResource() {
		return new ResourceServerProperties();
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
}
