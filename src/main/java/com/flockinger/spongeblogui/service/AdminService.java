package com.flockinger.spongeblogui.service;

import java.util.List;
import java.util.Map;

import com.flockinger.spongeblogui.exception.DtoValidationFailedException;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostPreviewDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;
import com.flockinger.spongeblogui.dto.Paging;

public interface AdminService {
	
	Map<String,String> getBlogSettings();
	void storeBlogSettings(Map<String,String> settings) throws DtoValidationFailedException;
	
	List<UserEditDTO> getUsers();
	UserEditDTO getUser(Long id);
	UserEditDTO createUser(UserEditDTO user);
	void updateUser(UserEditDTO user);
	void deleteUser(Long id);
	
	List<CategoryDTO> getCategories();
	CategoryDTO getCategory(Long id);
	CategoryDTO createCategory(CategoryDTO category);
	void updateCategory(CategoryDTO category);
	void deleteCategory(Long id);
	
	List<TagDTO> getTags();
	TagDTO getTag(Long id);
	TagDTO createTag(TagDTO tag);
	void updateTag(TagDTO tag);
	void deleteTag(Long id);
	
	PostsPageDTO getAllPosts(Paging page);
	PostsPageDTO getPostsByUser(Long userId, PostStatus status, Paging page);
	PostsPageDTO getPostsByTag(Long tagId, PostStatus status, Paging page);
	PostsPageDTO getPostsByCategory(Long categoryId, PostStatus status, Paging page);
	PostsPageDTO getPostsByStatus(PostStatus status, Paging page);
	PostDTO getPost(Long id);
	PostDTO createPost(PostDTO post);
	void updatePost(PostDTO post);
	void deletePost(Long id);
}
