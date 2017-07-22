package com.flockinger.spongeblogui.resource;

import static com.flockinger.spongeblogui.config.BlogConstants.PAGE_PARAM_NAME;
import static com.flockinger.spongeblogui.config.BlogConstants.PAGING_DEFAULT_ITEMS_PER_PAGE_KEY;
import static com.flockinger.spongeblogui.config.BlogConstants.PAGING_DEFAULT_PAGE_KEY;
import static com.flockinger.spongeblogui.config.BlogConstants.SIZE_PARAM_NAME;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flockinger.spongeblogui.resource.dto.BlogDTO;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;

@FeignClient(value = "blog", url = "${spongeblog.client.url}")
public interface BlogClient {

  @RequestMapping(method = RequestMethod.GET, value = "blog")
  BlogDTO getBlog();

  @RequestMapping(method = RequestMethod.GET, value = "categories/children/{parentCategoryId}")
  List<CategoryDTO> getCategoriesFromParent(
      @PathVariable("parentCategoryId") Long parentCategoryId);

  @RequestMapping(method = RequestMethod.GET, value = "categories")
  List<CategoryDTO> getCategories();

  @RequestMapping(method = RequestMethod.GET, value = "tags")
  List<TagDTO> getTags();

  @RequestMapping(method = RequestMethod.GET, value = "posts/{postId}")
  PostDTO getPost(@PathVariable("postId") Long postId);

  @RequestMapping(method = RequestMethod.GET, value = "posts/status/PUBLIC")
  PostsPageDTO getPublicPosts(
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/author/{userId}/PUBLIC")
  PostsPageDTO getPublicPostsByUser(@PathVariable("userId") Long userId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/category/{categoryId}/PUBLIC")
  PostsPageDTO getPublicPostsByCategory(@PathVariable("categoryId") Long categoryId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/tag/{tagId}/PUBLIC")
  PostsPageDTO getPublicPostsByTag(@PathVariable("tagId") Long tagId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);
}
