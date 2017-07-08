package com.flockinger.spongeblogui.service.impl;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.Post;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.dto.PreviewPost;
import com.flockinger.spongeblogui.dto.Tag;
import com.flockinger.spongeblogui.dto.UserInfo;
import com.flockinger.spongeblogui.exception.EntityIsNotExistingException;
import com.flockinger.spongeblogui.resource.BlogClient;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostPreviewDTO;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserInfoDTO;
import com.flockinger.spongeblogui.service.BlogService;


@Component
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private BlogClient client;

	@Autowired
	private ModelMapper mapper;
	
	private static Logger logger = Logger.getLogger(BlogServiceImpl.class.getName());
	
	@Override
	public Map<String, String> getBlogSettings() {
		Map<String,String> blog = new HashMap<>();
		
		try{
			blog = AdminServiceImpl.mapBlog(client.getBlog());
		} catch (EntityIsNotExistingException e) {
			logger.error("No Blog is existing, please create!",e);
		}
		return blog;
	}

	@Override
	public List<Category> getAllCategories() {
		return client.getCategories().stream()
				.filter(this::isTopLevelCategory)
				.map(this::map)
				.map(this::enrichWithChildren)
				.collect(Collectors.toList());
	}
	
	private boolean isTopLevelCategory(CategoryDTO category) {
		return category.getParentId() == null;
	}
	
	private Category map(CategoryDTO category) {
		return mapper.map(category, Category.class);
	}
	
	private Category enrichWithChildren(Category category) {
		List<Category> children = client.getCategoriesFromParent(category.getId()).stream().map(this::map).collect(Collectors.toList());
		category.setChildren(children);
		return category;
	}

	@Override
	public List<Tag> getAllTags() {
		return client.getTags().stream()
				.map(this::map).collect(Collectors.toList());
	}
	
	private Tag map(TagDTO tag) {
		return mapper.map(tag, Tag.class);
	}

	@Override
	public PostsPage getAllPosts(Paging page) {
		return map(client.getPublicPosts(page.getPage(), page.getSize()));
	}

	@Override
	public PostsPage getPostsForCategory(Long category, Paging page) {
		return map(client.getPublicPostsByCategory(category, page.getPage(), page.getSize()));
	}

	@Override
	public PostsPage getPostsForTag(Long tag, Paging page) {
		return map(client.getPublicPostsByTag(tag, page.getPage(), page.getSize()));
	}

	@Override
	public PostsPage getPostsForUser(Long user, Paging page) {
		return map(client.getPublicPostsByUser(user, page.getPage(), page.getSize()));
	}
	
	private PostsPage map(PostsPageDTO pageDto) {
		PostsPage page = mapper.map(pageDto, PostsPage.class);
		List<PreviewPost> previews = emptyIfNull(pageDto.getPreviewPosts()).stream().map(this::map).collect(Collectors.toList());
		page.setPreviewPosts(previews);
		return page;
	}
	
	private PreviewPost map(PostPreviewDTO previewPost) {
		PreviewPost post = mapper.map(previewPost, PreviewPost.class);
		post.setUser(map(previewPost.getAuthor()));
		post.setTags(emptyIfNull(previewPost.getTags()).stream().map(this::map).collect(Collectors.toList()));
		return post;
	}

	@Override
	public Post getPost(Long id) {
		return map(client.getPost(id));
	}
	
	private Post map(PostDTO postDto){
		Post post = mapper.map(postDto, Post.class);
		post.setUser(map(postDto.getAuthor()));
		post.setCategory(map(postDto.getCategory()));
		post.setTags(emptyIfNull(postDto.getTags()).stream().map(this::map).collect(Collectors.toList()));
		return post;
	}
	
	private UserInfo map(UserInfoDTO user) {
		return mapper.map(user, UserInfo.class);
	}
}
