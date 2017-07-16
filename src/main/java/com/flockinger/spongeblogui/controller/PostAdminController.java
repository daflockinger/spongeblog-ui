package com.flockinger.spongeblogui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flockinger.spongeblogui.controller.helper.Linker;
import com.flockinger.spongeblogui.dto.AjaxPagination;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.PostFilter;
import com.flockinger.spongeblogui.dto.PostQuery;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.UserInfoDTO;
import com.flockinger.spongeblogui.service.AdminService;

import wiremock.org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping("/admin/posts")
public class PostAdminController {

  private final static String POSTS_NAME = "posts";
  private final static String POST_NAME = "post";
  private final static String PAGINATION_NAME = "pagination";

  private final static String CATEGORIES_NAME = "categories";
  private final static String TAGS_NAME = "tags";
  private final static String USERS_NAME = "users";
  private final static String POST_STATUSES = "statuses";

  @Autowired
  private AdminService service;

  @Autowired
  private ModelMapper mapper;

  @RequestMapping(path = "", method = RequestMethod.GET)
  public String getPostsView(Model model) {
    PostQuery query = PostQuery.all().size(PostQuery.DEFAULT_SIZE).build();
    PostsPageDTO postPage = service.getPostsByQuery(query);
    model = createPostsModel(model, query.getPaging().getPage(), postPage);

    return "/admin/posts";
  }

  @RequestMapping(path = "/list", method = RequestMethod.GET)
  public String getPostList(Model model,
      @RequestParam(name = "filter", required = false) PostFilter filter,
      @RequestParam(name = "id", required = false) Long id,
      @RequestParam(name = "status", required = false, defaultValue = "ALL") PostStatus status,
      @RequestParam(value = "page", required = false) Optional<Integer> page,
      @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
    PostQuery query = null;

    if (id != null) {
      query =
          PostQuery.filter(filter).id(id).page(page.orElse(0)).size(size).status(status).build();
    } else {
      query = PostQuery.all().page(page.orElse(0)).size(size).status(status).build();
    }
    PostsPageDTO postPage = service.getPostsByQuery(query);
    model = createPostsModel(model, query.getPaging().getPage(), postPage);

    return "/admin/components/post-list";
  }

  @RequestMapping(path = "/edit", method = RequestMethod.GET)
  public String getPost(Model model, @RequestParam(name = "id", required = false) Long postId) {
    PostDTO post = new PostDTO();

    if (postId != null) {
      post.setTags(new ArrayList<>());
      post = service.getPost(postId);
    }
    model = createBasePostModel(model);
    model.addAttribute(POST_NAME, post);

    return "/admin/components/post-edit";
  }

  @RequestMapping(path = "/edit", method = RequestMethod.POST)
  public ResponseEntity<Void> getPost(@RequestBody PostDTO post) {

    if (post.getPostId() != null) {
      service.updatePost(post);
    } else {
      service.createPost(post);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(path = "/{postId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deletePost(
      @PathVariable(name = "postId", required = true) Long postId) {
    service.deletePost(postId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  private Model createBasePostModel(Model model) {
    model.addAttribute(CATEGORIES_NAME, service.getCategories());
    model.addAttribute(TAGS_NAME, service.getTags());
    model.addAttribute(USERS_NAME, service.getUsers());
    model.addAttribute(POST_STATUSES, enumToNameList(PostStatus.class));
    return model;
  }

  private Model createPostsModel(Model model, int currentPage, PostsPageDTO postPage) {
    model = createBasePostModel(model);
    model.addAttribute(PAGINATION_NAME, mapPagination(postPage, currentPage));
    model.addAttribute(POSTS_NAME, postPage.getPreviewPosts());
    return model;
  }

  private AjaxPagination mapPagination(PostsPageDTO postPage, int currentPage) {
    AjaxPagination pagination = mapper.map(postPage, AjaxPagination.class);
    pagination.setPageNumber(currentPage);
    return pagination;
  }

  private <E extends Enum<E>> List<String> enumToNameList(Class<E> enumClass) {
    return EnumUtils.getEnumList(enumClass).stream().map(status -> status.toString())
        .collect(Collectors.toList());
  }
}
