package com.flockinger.spongeblogui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/posts")
public class PostAdminController {
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getPostsView(Model model) {
		return "/admin/posts";
	}
	
}
