package com.flockinger.spongeblogui.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flockinger.spongeblogui.config.ClientConfig;
import com.flockinger.spongeblogui.config.TemplatingConfig;
import com.flockinger.spongeblogui.exception.ClientUnavailableException;
import com.flockinger.spongeblogui.exception.DtoValidationFailedException;
import com.flockinger.spongeblogui.exception.EntityConflictException;
import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.dto.Error;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import({TemplatingConfig.class, ClientConfig.class})
public class AllKindsOfClientErrorsTest {

  @Autowired
  private AdminService service;

  private ObjectMapper mapper = new ObjectMapper();

  @ClassRule
  public static WireMockClassRule spongeMock = new WireMockClassRule(options().port(8081));

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Ignore
  @Test
  public void test_withStatusNotFound_shouldThrowEntityIsNotExistingException() throws Exception {
    expectedEx.expect(EntityIsNotExistingException.class);
    expectedEx.expectMessage("Blog not found!");

    Error error = new Error();
    error.setMessage("Blog not found!");
    spongeMock.stubFor(get(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(404)
        .withHeader("Content-Type", "application/json").withBody(toJson(error))));

    service.getBlogSettings();
  }

  @Ignore
  @Test
  public void test_withConflict_shouldThrowEntityConflictException() throws Exception {
    expectedEx.expect(EntityConflictException.class);
    expectedEx.expectMessage("Entity already existing!");

    Error error = new Error();
    error.setMessage("Entity already existing!");
    spongeMock.stubFor(post(urlEqualTo("/api/v1/posts")).willReturn(aResponse().withStatus(409)
        .withHeader("Content-Type", "application/json").withBody(toJson(error))));

    service.createPost(new PostDTO());
  }

  @Ignore
  @Test
  public void test_withBadRequest_shouldThrowDtoValidationFailedException() throws Exception {
    expectedEx.expect(DtoValidationFailedException.class);
    expectedEx.expectMessage("Invalid request!");

    Error error = new Error();
    error.setMessage("Invalid request!");
    Map<String, String> fields = new HashMap<>();
    fields.put("name", "Must not be empty!");
    fields.put("email", "Must be an valid email address!");
    error.setFields(fields);

    spongeMock.stubFor(put(urlEqualTo("/api/v1/posts")).willReturn(aResponse().withStatus(400)
        .withHeader("Content-Type", "application/json").withBody(toJson(error))));
    service.updatePost(new PostDTO());
  }

  @Ignore
  @Test
  public void test_withBadRequestWithoutBody_shouldThrowDtoValidationFailedException()
      throws Exception {
    expectedEx.expect(DtoValidationFailedException.class);

    spongeMock.stubFor(put(urlEqualTo("/api/v1/posts"))
        .willReturn(aResponse().withStatus(400).withHeader("Content-Type", "application/json")));
    service.updatePost(new PostDTO());
  }

  @Ignore
  @Test
  public void test_withServiceIsLazy_shouldThrowDtoValidationFailedException() throws Exception {
    expectedEx.expect(ClientUnavailableException.class);
    expectedEx.expectMessage("Backend is on strike!");

    Error error = new Error();
    error.setMessage("Backend is on strike!");
    spongeMock.stubFor(put(urlEqualTo("/api/v1/posts")).willReturn(aResponse().withStatus(500)
        .withHeader("Content-Type", "application/json").withBody(toJson(error))));

    service.updatePost(new PostDTO());
  }

  private String toJson(Object something) throws JsonProcessingException {
    return mapper.writeValueAsString(something);
  }
}
