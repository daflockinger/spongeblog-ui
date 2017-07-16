package com.flockinger.spongeblogui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.flockinger.spongeblogui.resource.dto.BlogStatus;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;
import com.flockinger.spongeblogui.resource.dto.UserRole;
import com.flockinger.spongeblogui.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final static String BLOG_SETTINGS_NAME = "blog";
	private final static String CATEGORIES_NAME = "categories";
	private final static String TOP_CATEGORIES = "topCategories";
	private final static String TAGS_NAME = "tags";
	private final static String USERS_NAME = "users";
	private final static String USER_ROLES = "userRoles";
	private final static String BLOG_STATUSES = "blogStatus";
	
	private static Logger logger = Logger.getLogger(AdminController.class.getName());

	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value={"","/settings"},method=RequestMethod.GET)
	public String getSettingsView(Model model) {
		model.addAttribute(BLOG_SETTINGS_NAME, adminService.getBlogSettings());
		model.addAttribute(BLOG_STATUSES,enumToNameList(BlogStatus.class));
		return "/admin/settings";
	}
	
	@RequestMapping(value={"/settings"},method=RequestMethod.POST,consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void storeSettingsView(@RequestBody MultiValueMap<String, String> settings) {
		adminService.storeBlogSettings(settings.toSingleValueMap());
	}
	
	@RequestMapping(value={"/users"},method=RequestMethod.GET)
	public String getUsersView(Model model) {
		model.addAttribute(USERS_NAME,addEmptyItemToList(adminService.getUsers(),UserEditDTO.class));
		model.addAttribute(USER_ROLES, EnumUtils.getEnumList(UserRole.class));
		return "/admin/users";
	}
	
	@RequestMapping(value={"/users"},method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveUser(@RequestBody UserEditDTO user) {
		
		if(user.getUserId() != null) {
			adminService.updateUser(user);
		} else {
			adminService.createUser(user);
		}
	}
	
	@RequestMapping(value={"/users/{userId}"},method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(value="userId",required=true) Long userId) {
		adminService.deleteUser(userId);
	}
	
	@RequestMapping(value={"/categories"},method=RequestMethod.GET)
	public String getCategoriesView(Model model) {
		List<CategoryDTO> allCategories = adminService.getCategories();
		model.addAttribute(CATEGORIES_NAME,addEmptyItemToList(allCategories,CategoryDTO.class));
		model.addAttribute(TOP_CATEGORIES,getTopCategories(allCategories));
		return "/admin/categories";
	}
	
	private List<CategoryDTO> getTopCategories(List<CategoryDTO> allCategories) {
		return allCategories.stream().filter(cat -> cat.getParentId() == null).collect(Collectors.toList());
	}
	
	@RequestMapping(value={"/categories"},method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveCategory(@RequestBody CategoryDTO category) {
		
		if(category.getCategoryId() != null) {
			adminService.updateCategory(category);
		} else {
			adminService.createCategory(category);
		}
	}
	
	@RequestMapping(value={"/categories/{categoryId}"},method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategory(@PathVariable(required=true) Long categoryId) {
		adminService.deleteCategory(categoryId);
	}
	
	
	@RequestMapping(value={"/tags"},method=RequestMethod.GET)
	public String getTagsView(Model model) {
		model.addAttribute(TAGS_NAME, addEmptyItemToList(adminService.getTags(),TagDTO.class));
		return "/admin/tags";
	}
	
	@RequestMapping(value={"/tags"},method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveTag(@RequestBody TagDTO tag) {
		
		if(tag.getTagId() != null) {
			adminService.updateTag(tag);
		} else {
			adminService.createTag(tag);
		}
	}
	
	@RequestMapping(value={"/tags/{tagId}"},method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteTag(@PathVariable(required=true) Long tagId) {
		adminService.deleteTag(tagId);
	}
	
	private <T> List<T> addEmptyItemToList(List<T> entities,Class<T> entity){
		List<T> extendedEntities = new ArrayList<>();
		try {
			extendedEntities.add(entity.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Entity must have a no args constructor!",e);
		} finally {
			extendedEntities.addAll(entities);
		}
		return extendedEntities;
	}
	
	private <E extends Enum<E>> List<String> enumToNameList(Class<E> enumClass){
		return EnumUtils.getEnumList(enumClass).stream().map(status -> status.toString()).collect(Collectors.toList());
	}
}
