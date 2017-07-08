package com.flockinger.spongeblogui.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flockinger.spongeblogui.controller.helper.Linker;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.service.BlogService;

@Controller
public class BlogController {

	@Autowired
	private Linker linkHelper;
	@Autowired
	private BlogService service;
	
	private final static String BLOG_SETTINGS_NAME = "blog";
	private final static String CATEGORIES_NAME = "categories";
	private final static String TAGS_NAME = "tags";
	private final static String POST_PREVIEWS_NAME = "previews"; 
	private final static String POST_NAME = "post";
	private final static String PAGINATION_NAME = "pagination";
	
	@RequestMapping(value={"/","/{page}"})
	public String getHome(Model model, @PathVariable(value = "page", required = false) Optional<Integer> page,
	         @RequestParam(value = "size", required = false,defaultValue="5") Integer size) {
		Paging chosenPage = new Paging(page,size,"");

		PostsPage postPage = service.getAllPosts(chosenPage);
		model = createBaseModel(model);
		model.addAttribute(PAGINATION_NAME,linkHelper.createPagination(postPage,chosenPage));
		model.addAttribute(POST_PREVIEWS_NAME, linkHelper.addPostLinks(postPage.getPreviewPosts()));
		
		return "/index";
	}
	
	@RequestMapping(value={"/category/{category}","/category/{category}/{page}"})
	public String getPostsForCategory(Model model, @PathVariable(value = "page", required = false) Optional<Integer> page,
	         @RequestParam(value = "size", required = false,defaultValue="5") Integer size, @PathVariable("category") String category) {
		Paging chosenPage = new Paging(page,size,"/category/" + category);
		
		PostsPage postPage = service.getPostsForCategory(linkHelper.recoverIdFromLink(category), chosenPage);	 	
		model = createBaseModel(model);
		model.addAttribute(PAGINATION_NAME,linkHelper.createPagination(postPage,chosenPage));
		model.addAttribute(POST_PREVIEWS_NAME, linkHelper.addPostLinks(postPage.getPreviewPosts()));
		
		return "/index";
	}
	
	@RequestMapping(value={"/tag/{tag}","/tag/{tag}/{page}"})
	public String getPostsForTag(Model model, @PathVariable(value = "page", required = false) Optional<Integer> page,
	         @RequestParam(value = "size", required = false,defaultValue="5") Integer size, @PathVariable("tag") String tag) {
		Paging chosenPage = new Paging(page,size,"/tag/" + tag);

		PostsPage postPage = service.getPostsForTag(linkHelper.recoverIdFromLink(tag), chosenPage);		
		model = createBaseModel(model);
		model.addAttribute(PAGINATION_NAME,linkHelper.createPagination(postPage,chosenPage));
		model.addAttribute(POST_PREVIEWS_NAME, linkHelper.addPostLinks(postPage.getPreviewPosts()));
		
		return "/index";
	}
	
	@RequestMapping(value={"/user/{user}","/user/{user}/{page}"})
	public String getPostsForUser(Model model, @PathVariable(value = "page", required = false) Optional<Integer> page,
	         @RequestParam(value = "size", required = false,defaultValue="5") Integer size, @PathVariable("user") String user) {
		Paging chosenPage = new Paging(page,size,"/user/" + user);

		PostsPage postPage =service.getPostsForUser(linkHelper.recoverIdFromLink(user), chosenPage);		
		model = createBaseModel(model);
		model.addAttribute(PAGINATION_NAME,linkHelper.createPagination(postPage,chosenPage));
		model.addAttribute(POST_PREVIEWS_NAME, linkHelper.addPostLinks(postPage.getPreviewPosts()));
		
		return "/index";
	}
	
	@RequestMapping("/post/{title}")
	public String getPostByTitle(Model model, @PathVariable("title") String title) {	
		model.addAttribute(POST_NAME, linkHelper.addPostLink(service.getPost(linkHelper.recoverIdFromLink(title))));
		model = createBaseModel(model);
		
		return "/post";
	}
	
	private Model createBaseModel(Model model) {
		model.addAttribute(BLOG_SETTINGS_NAME, service.getBlogSettings());
		model.addAttribute(CATEGORIES_NAME, linkHelper.addCategoryLinks(service.getAllCategories()));
		model.addAttribute(TAGS_NAME, service.getAllTags());
		
		return model;
	}
}
