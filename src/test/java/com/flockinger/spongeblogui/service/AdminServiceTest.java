package com.flockinger.spongeblogui.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.resource.dto.BlogDTO;
import com.flockinger.spongeblogui.resource.dto.BlogStatus;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;
import com.flockinger.spongeblogui.resource.dto.UserRole;
import com.flockinger.spongeblogui.service.impl.AdminServiceImpl;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

  @Autowired
  private AdminService service;

  private ObjectMapper mapper;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Rule
  public WireMockRule spongeMock = new WireMockRule(8081);

  @Test
  public void testGetBlogSettings_withExistingBlog_shouldReturnBlog() throws Exception {
    // prepare
    BlogDTO blog = new BlogDTO();
    blog.setName("some blog");
    blog.setStatus(BlogStatus.ACTIVE);
    blog.setSettings(ImmutableMap.of("title", "my blog", "backgroundImage", "sun.jpg", "copyright",
        "free for all"));
    spongeMock.stubFor(get(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(200)
        .withHeader("Content-Type", "application/json").withBody(toJson(blog))));

    // execute
    Map<String, String> settings = service.getBlogSettings();

    // assert
    assertNotNull("returned map must not be null", settings);
    assertEquals("validate correct name", "some blog", settings.get(AdminServiceImpl.BLOG_NAME));
    assertEquals("validate correct status", "ACTIVE", settings.get(AdminServiceImpl.BLOG_STATUS));
    assertEquals("validate correct title", "my blog", settings.get("title"));
    assertEquals("validate correct background image", "sun.jpg", settings.get("backgroundImage"));
    assertEquals("validate correct copyright", "free for all", settings.get("copyright"));
  }

  @Test
  public void testStoreBlogSettings_withValidSettingsAndExistingBlog_shouldUpdate()
      throws Exception {
    // prepare
    BlogDTO blog = new BlogDTO();
    blog.setName("some blog");
    blog.setStatus(BlogStatus.ACTIVE);
    blog.setSettings(ImmutableMap.of("title", "my blog", "backgroundImage", "sun.jpg", "copyright",
        "free for all"));
    spongeMock.stubFor(put(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(201)
        .withHeader("Content-Type", "application/json").withBody(toJson(blog))));
    spongeMock.stubFor(get(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(200)
        .withHeader("Content-Type", "application/json").withBody(toJson(new BlogDTO()))));
    // execute
    Map<String, String> settings = new HashMap<>();
    settings.putAll(blog.getSettings());
    settings.put(AdminServiceImpl.BLOG_NAME, "some blog");
    settings.put(AdminServiceImpl.BLOG_STATUS, "ACTIVE");
    service.storeBlogSettings(settings);
  }

  @Test
  public void testStoreBlogSettings_withValidSettingsAndNotExistingBlog_shouldUpdate()
      throws Exception {
    // prepare
    BlogDTO blog = new BlogDTO();
    blog.setName("some blog");
    blog.setStatus(BlogStatus.ACTIVE);
    blog.setSettings(ImmutableMap.of("title", "my blog", "backgroundImage", "sun.jpg", "copyright",
        "free for all"));
    spongeMock.stubFor(post(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(201)
        .withHeader("Content-Type", "application/json").withBody(toJson(blog))));
    spongeMock.stubFor(get(urlEqualTo("/api/v1/blog")).willReturn(aResponse().withStatus(404)));
    // execute
    Map<String, String> settings = new HashMap<>();
    settings.putAll(blog.getSettings());
    settings.put(AdminServiceImpl.BLOG_NAME, "some blog");
    settings.put(AdminServiceImpl.BLOG_STATUS, "ACTIVE");
    service.storeBlogSettings(settings);
  }


  private String toJson(Object something) throws JsonProcessingException {
    return mapper.writeValueAsString(something);
  }
}
