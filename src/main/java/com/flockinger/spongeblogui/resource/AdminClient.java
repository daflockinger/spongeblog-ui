package com.flockinger.spongeblogui.resource;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.flockinger.spongeblogui.config.BlogConstants.*;
import com.flockinger.spongeblogui.resource.dto.BlogDTO;
import com.flockinger.spongeblogui.resource.dto.BlogUserDetails;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostPreviewDTO;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;

@FeignClient(value = "blog", url = "${spongeblog.client.url}")
public interface AdminClient {

  // BLOG Entpoints
  @RequestMapping(method = RequestMethod.DELETE, value = "blog")
  void deleteBlog();

  @RequestMapping(method = RequestMethod.GET, value = "blog")
  BlogDTO getBlog();

  @RequestMapping(method = RequestMethod.POST, value = "blog", consumes = "application/json")
  BlogDTO createBlog(@RequestBody BlogDTO blog);

  @RequestMapping(method = RequestMethod.PUT, value = "blog", consumes = "application/json")
  BlogDTO updateBlog(@RequestBody BlogDTO blog);

  // User Entpoints
  @RequestMapping(method = RequestMethod.GET, value = "users/email")
  BlogUserDetails getUserByEmail(@RequestParam("address") String email);

  @RequestMapping(method = RequestMethod.GET, value = "users/{userId}")
  UserEditDTO getUser(@PathVariable("userId") Long userId);

  @RequestMapping(method = RequestMethod.GET, value = "users")
  List<UserEditDTO> getUsers();

  @RequestMapping(method = RequestMethod.POST, value = "users", consumes = "application/json")
  UserEditDTO createUser(@RequestBody UserEditDTO user);

  @RequestMapping(method = RequestMethod.PUT, value = "users", consumes = "application/json")
  void updateUser(@RequestBody UserEditDTO user);

  @RequestMapping(method = RequestMethod.DELETE, value = "users/{userId}")
  void deleteUser(@PathVariable("userId") Long userId);

  // Category Entpoints
  @RequestMapping(method = RequestMethod.GET, value = "categories/{categoryId}")
  CategoryDTO getCategory(@PathVariable("categoryId") Long categoryId);

  @RequestMapping(method = RequestMethod.GET, value = "categories")
  List<CategoryDTO> getCategories();

  @RequestMapping(method = RequestMethod.POST, value = "categories", consumes = "application/json")
  CategoryDTO createCategory(@RequestBody CategoryDTO category);

  @RequestMapping(method = RequestMethod.PUT, value = "categories", consumes = "application/json")
  void updateCategory(@RequestBody CategoryDTO category);

  @RequestMapping(method = RequestMethod.DELETE, value = "categories/{categoryId}")
  void deleteCategory(@PathVariable("categoryId") Long categoryId);

  // Tag Entpoints
  @RequestMapping(method = RequestMethod.GET, value = "tags/{tagId}")
  TagDTO getTag(@PathVariable("tagId") Long tagId);

  @RequestMapping(method = RequestMethod.GET, value = "tags")
  List<TagDTO> getTags();

  @RequestMapping(method = RequestMethod.POST, value = "tags", consumes = "application/json")
  TagDTO createTag(@RequestBody TagDTO tag);

  @RequestMapping(method = RequestMethod.PUT, value = "tags", consumes = "application/json")
  void updateTag(@RequestBody TagDTO tag);

  @RequestMapping(method = RequestMethod.DELETE, value = "tags/{tagId}")
  void deleteTag(@PathVariable("tagId") Long tagId);

  // Post Entpoints
  @RequestMapping(method = RequestMethod.GET, value = "posts/{postId}")
  PostDTO getPost(@PathVariable("postId") Long postId);

  @RequestMapping(method = RequestMethod.POST, value = "posts", consumes = "application/json")
  PostDTO createPost(@RequestBody PostDTO post);

  @RequestMapping(method = RequestMethod.PUT, value = "posts", consumes = "application/json")
  void updatePost(@RequestBody PostDTO post);

  @RequestMapping(method = RequestMethod.DELETE, value = "posts/{postId}")
  void deletePost(@PathVariable("postId") Long postId);

  @RequestMapping(method = RequestMethod.GET, value = "posts")
  PostsPageDTO getPosts(
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/author/{userId}")
  PostsPageDTO getPostsByUser(@PathVariable("userId") Long userId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/author/{userId}/{status}")
  PostsPageDTO getPostsByUserAndStatus(@PathVariable("userId") Long userId,
      @PathVariable("status") String status,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/category/{categoryId}")
  PostsPageDTO getPostsByCategory(@PathVariable("categoryId") Long categoryId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/category/{categoryId}/{status}")
  PostsPageDTO getPostsByCategoryAndStatus(@PathVariable("categoryId") Long categoryId,
      @PathVariable("status") String status,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/status/{status}")
  PostsPageDTO getPostsByStatus(@PathVariable("status") String status,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/tag/{tagId}")
  PostsPageDTO getPostsByTag(@PathVariable("tagId") Long tagId,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);

  @RequestMapping(method = RequestMethod.GET, value = "posts/tag/{tagId}/{status}")
  PostsPageDTO getPostsByTagAndStatus(@PathVariable("tagId") Long tagId,
      @PathVariable("status") String status,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_PAGE_KEY,
          value = PAGE_PARAM_NAME) Integer page,
      @RequestParam(required = false, defaultValue = PAGING_DEFAULT_ITEMS_PER_PAGE_KEY,
          value = SIZE_PARAM_NAME) Integer size);
}
