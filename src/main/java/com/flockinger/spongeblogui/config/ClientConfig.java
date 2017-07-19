package com.flockinger.spongeblogui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.flockinger.spongeblogui.resource.ClientErrorHandler;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "com.flockinger.spongeblogui.resource",
    defaultConfiguration = ClientConfig.class)
public class ClientConfig {

  @Value("${security-admin.clientid}")
  private String adminClientId;
  @Value("${security-admin.secretkey}")
  private String adminSecretkey;
  
  @Bean
  @Primary
  public ErrorDecoder feignErrorDecoder() {
    return new ClientErrorHandler();
  }
  
  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
       return new BasicAuthRequestInterceptor(adminClientId, adminSecretkey);
  }
}
