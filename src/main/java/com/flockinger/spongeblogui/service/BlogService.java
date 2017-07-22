package com.flockinger.spongeblogui.service;

import java.util.List;
import java.util.Map;

import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.Post;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.dto.Tag;

public interface BlogService {
  Map<String, String> getBlogSettings();

  List<Category> getAllCategories();

  List<Tag> getAllTags();

  PostsPage getAllPosts(Paging page);

  PostsPage getPostsForCategory(Long category, Paging page);

  PostsPage getPostsForTag(Long tag, Paging page);

  PostsPage getPostsForUser(Long user, Paging page);

  Post getPost(Long id);
}
