package com.flockinger.spongeblogui.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.PostQuery;
import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.AdminClient;
import com.flockinger.spongeblogui.resource.dto.BlogDTO;
import com.flockinger.spongeblogui.resource.dto.BlogStatus;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;
import com.flockinger.spongeblogui.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminClient client;

	public final static String BLOG_NAME = "name";
	public final static String BLOG_STATUS = "status";
	private static Logger logger = Logger.getLogger(AdminServiceImpl.class.getName());

	@Override
	public Map<String, String> getBlogSettings() {
		Map<String, String> blog = new HashMap<>();

		blog = mapBlog(client.getBlog());

		return blog;
	}

	@Override
	public void storeBlogSettings(Map<String, String> settings) {
		BlogDTO blog = mapBlog(settings);

		if (!isBlogExisting()) {
			client.createBlog(blog);
		} else {
			client.updateBlog(blog);
		}
	}

	private boolean isBlogExisting() {
		boolean isExisting = false;

		try {
			client.getBlog();
			isExisting = true;
		} catch (EntityIsNotExistingException e) {
			logger.error("No Blog is existing, please create!", e);
		}
		return isExisting;
	}

	static Map<String, String> mapBlog(BlogDTO blog) {
		Map<String, String> settings = new HashMap<>();
		settings.putAll(blog.getSettings());
		settings.put(BLOG_NAME, blog.getName());
		settings.put(BLOG_STATUS, blog.getStatus().toString());

		return settings;
	}

	private BlogDTO mapBlog(Map<String, String> settings) {
		BlogDTO blog = new BlogDTO();
		blog.setName(settings.get(BLOG_NAME));
		blog.setStatus(EnumUtils.getEnum(BlogStatus.class, settings.get(BLOG_STATUS)));
		settings.remove(BLOG_NAME);
		settings.remove(BLOG_STATUS);
		blog.setSettings(settings);

		return blog;
	}

	@Override
	public List<UserEditDTO> getUsers() {
		return client.getUsers();
	}

	@Override
	public UserEditDTO getUser(Long id) {
		return client.getUser(id);
	}

	@Override
	public UserEditDTO createUser(UserEditDTO user) {
		return client.createUser(user);
	}

	@Override
	public void updateUser(UserEditDTO user) {
		client.updateUser(user);
	}

	@Override
	public void deleteUser(Long id) {
		client.deleteUser(id);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		return client.getCategories();
	}

	@Override
	public CategoryDTO getCategory(Long id) {
		return client.getCategory(id);
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO category) {
		return client.createCategory(category);
	}

	@Override
	public void updateCategory(CategoryDTO category) {
		client.updateCategory(category);
	}

	@Override
	public void deleteCategory(Long id) {
		client.deleteCategory(id);
	}

	@Override
	public List<TagDTO> getTags() {
		return client.getTags();
	}

	@Override
	public TagDTO getTag(Long id) {
		return client.getTag(id);
	}

	@Override
	public TagDTO createTag(TagDTO tag) {
		return client.createTag(tag);
	}

	@Override
	public void updateTag(TagDTO tag) {
		client.updateTag(tag);
	}

	@Override
	public void deleteTag(Long id) {
		client.deleteTag(id);
	}

	@Override
	public PostsPageDTO getPostsByQuery(PostQuery query) {
		PostsPageDTO postsPage = new PostsPageDTO();
		
		switch (query.getFilter()) {
		case TAG:
			postsPage = getPostsByTag(query.getId(), query.getStatus(),query.getPaging());
			break;
		case USER:
			postsPage = getPostsByUser(query.getId(), query.getStatus(),query.getPaging());
			break;
		case CATEGORY:
			postsPage = getPostsByCategory(query.getId(), query.getStatus(),query.getPaging());
			break;
		default:
			postsPage = getAllPosts(query.getStatus(), query.getPaging());
			break;
		}
		return postsPage;
	}

	private PostsPageDTO getAllPosts(PostStatus status, Paging page) {
		if (status.equals(PostStatus.ALL)) {
			return client.getPosts(page.getPage(), page.getSize());
		}
		return  client.getPostsByStatus(status.toString(), page.getPage(), page.getSize());
	}

	private PostsPageDTO getPostsByUser(Long userId, PostStatus status, Paging page) {
		if (status.equals(PostStatus.ALL)) {
			return client.getPostsByUser(userId, page.getPage(), page.getSize());
		}
		return client.getPostsByUserAndStatus(userId, status.toString(), page.getPage(), page.getSize());
	}

	private PostsPageDTO getPostsByTag(Long tagId, PostStatus status, Paging page) {
		if (status.equals(PostStatus.ALL)) {
			return client.getPostsByTag(tagId, page.getPage(), page.getSize());
		}
		return client.getPostsByTagAndStatus(tagId, status.toString(), page.getPage(), page.getSize());
	}

	private PostsPageDTO getPostsByCategory(Long categoryId, PostStatus status, Paging page) {
		if (status.equals(PostStatus.ALL)) {
			return client.getPostsByCategory(categoryId, page.getPage(), page.getSize());
		}
		return client.getPostsByCategoryAndStatus(categoryId, status.toString(), page.getPage(), page.getSize());
	}

	@Override
	public PostDTO getPost(Long id) {
		return client.getPost(id);
	}

	@Override
	public PostDTO createPost(PostDTO post) {
		return client.createPost(post);
	}

	@Override
	public void updatePost(PostDTO post) {
		client.updatePost(post);
	}

	@Override
	public void deletePost(Long id) {
		client.deletePost(id);
	}
}
