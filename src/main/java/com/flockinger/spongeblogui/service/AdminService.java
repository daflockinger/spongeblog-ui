package com.flockinger.spongeblogui.service;

import java.util.List;
import java.util.Map;

import com.flockinger.spongeblogui.dto.PostQuery;
import com.flockinger.spongeblogui.exception.DtoValidationFailedException;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;

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
	
	PostsPageDTO getPostsByQuery(PostQuery query);
	PostDTO getPost(Long id);
	PostDTO createPost(PostDTO post);
	void updatePost(PostDTO post);
	void deletePost(Long id);
}
