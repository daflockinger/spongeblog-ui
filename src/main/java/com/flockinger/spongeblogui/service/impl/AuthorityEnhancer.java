package com.flockinger.spongeblogui.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.AdminClient;
import com.flockinger.spongeblogui.resource.dto.BlogUserDetails;

@Service
public class AuthorityEnhancer extends FixedAuthoritiesExtractor {

	@Autowired
	private AdminClient client;
	
	private final PrincipalExtractor principleExtractor;
	private static Logger logger = Logger.getLogger(AuthorityEnhancer.class.getName());

	
	public AuthorityEnhancer(){
		this.principleExtractor = new FixedPrincipalExtractor();
	}

	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(principleExtractor.extractPrincipal(map),null);
		
		try {
			BlogUserDetails details = client.getUserByName(authentication.getName());
			return details.getAuthorities().stream().collect(Collectors.toList());
		} catch (EntityIsNotExistingException e) { 
			logger.warn("User tried to login with name: " + authentication.getName(), e);
		}
		return super.extractAuthorities(map);
	}
	
}
