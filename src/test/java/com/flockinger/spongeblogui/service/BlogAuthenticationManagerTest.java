package com.flockinger.spongeblogui.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.flockinger.spongeblogui.resource.AdminClient;
import com.flockinger.spongeblogui.resource.dto.BlogAuthority;
import com.flockinger.spongeblogui.resource.dto.BlogUserDetails;
import com.flockinger.spongeblogui.service.impl.BlogAuthenticationManager;

import wiremock.com.google.common.collect.ImmutableList;

@RunWith(SpringRunner.class)
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    MockitoTestExecutionListener.class})
@ContextConfiguration(classes = {BlogAuthenticationManager.class,AdminClient.class})
public class BlogAuthenticationManagerTest {

  @Autowired
  private BlogAuthenticationManager manager;
  
  @MockBean
  private AdminClient client;
  
  @Test
  public void testAuthenticate_withExistingUserEmail_shouldWork() {
    BlogUserDetails details = new BlogUserDetails();
    details.setAuthorities(ImmutableList.of(new BlogAuthority("ADMIN"),new BlogAuthority("AUTHOR")));
    when(client.getUserByEmail(anyString())).thenReturn(details);
    
    Authentication result = manager.authenticate(getTestAuth("flo@kinger.cc"));
    
    assertTrue("found user should be authenticated",result.isAuthenticated());
    assertEquals("should contain correct amount of authorities",2,result.getAuthorities().size());
    assertTrue("should contain admin auth",result.getAuthorities().stream().anyMatch(au -> au.getAuthority().equals("ADMIN")));
    assertTrue("should contain author auth",result.getAuthorities().stream().anyMatch(au -> au.getAuthority().equals("AUTHOR")));
  }
  
  @Test(expected=OAuth2Exception.class)
  public void testAuthenticate_withNotExistingUserEmail_shouldThrowOauthException() {
    when(client.getUserByEmail(anyString())).thenThrow(new OAuth2Exception("User tried to login with: hacker@hack.me"));
    manager.authenticate(getTestAuth("hacker@hack.me"));
  }
 
  @Test(expected=OAuth2Exception.class)
  public void testAuthenticate_withNullPrincipal_shouldThrowOauthException() {
    when(client.getUserByEmail(anyString())).thenThrow(new OAuth2Exception("User tried to login with: hacker@hack.me"));
    manager.authenticate(getTestAuth(null));
  }
  
  
  private Authentication getTestAuth(String principal) {
    return new Authentication() {
      @Override
      public String getName() {
        return null;
      }
      @Override
      public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
      @Override
      public boolean isAuthenticated() {
        return true;
      }
      @Override
      public Object getPrincipal() {
        return principal;
      }
      @Override
      public Object getDetails() {
        return null;
      }
      @Override
      public Object getCredentials() {
        return null;
      }
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
      }
    };
  }
}
