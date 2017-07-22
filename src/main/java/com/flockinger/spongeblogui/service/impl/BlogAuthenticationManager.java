package com.flockinger.spongeblogui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.AdminClient;
import com.flockinger.spongeblogui.resource.dto.BlogUserDetails;
import com.flockinger.spongeblogui.security.BlogAuthentication;

@Service
public class BlogAuthenticationManager implements AuthenticationManager {

  @Autowired
  private AdminClient client;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    BlogAuthentication wrapper = new BlogAuthentication(authentication);
    String userLogin = StringUtils.toString(authentication.getPrincipal());

    try {
      BlogUserDetails details = client.getUserByEmail(userLogin);
      wrapper.setAuthorities(details.getAuthorities());
      wrapper.setAuthenticated(true);
    } catch (EntityIsNotExistingException e) {
      throw new OAuth2Exception("User tried to login with: " + userLogin, e);
    }
    return wrapper;
  }
}
