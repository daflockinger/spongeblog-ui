package com.flockinger.spongeblogui.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.flockinger.spongeblogui.resource.ClientErrorHandler;

import feign.codec.ErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "com.flockinger.spongeblogui.resource",
    defaultConfiguration = ClientConfig.class)
public class ClientConfig {

  @Bean
  @Primary
  public ErrorDecoder feignErrorDecoder() {
    return new ClientErrorHandler();
  }
}
