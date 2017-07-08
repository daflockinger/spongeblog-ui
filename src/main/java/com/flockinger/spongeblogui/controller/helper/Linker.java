package com.flockinger.spongeblogui.controller.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.dto.Pagination;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.Post;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.dto.PreviewPost;
import com.flockinger.spongeblogui.dto.Tag;
import com.flockinger.spongeblogui.dto.UserInfo;
import com.flockinger.spongeblogui.exception.InvalidRequestUrlException;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;

import wiremock.org.apache.commons.lang3.math.NumberUtils;


@Component
public class Linker {

	private final static int NEXT = 1;
	private final static int PREVIOUS = -1;

	public List<PreviewPost> addPostLinks(List<PreviewPost> posts) {
		return posts.stream().map(post -> {
			post.setLink(createLinkWithId(post.getTitle(),post.getPostId()));
			post.setTags(addTagLinks(post.getTags()));
			post.setUser(createUserLink(post.getUser()));
			return post;
		}).collect(Collectors.toList());
	}

	public Post addPostLink(Post post) {
		post.setTags(addTagLinks(post.getTags()));
		post.setUser(createUserLink(post.getUser()));
		return post;
	}

	public UserInfo createUserLink(UserInfo user) {
		user.setLink(createLinkWithId(user.getName(), user.getId()));
		return user;
	}

	public List<Tag> addTagLinks(List<Tag> tags) {
		return tags.stream().map(tag -> {
			tag.setLink(createLinkWithId(tag.getName(),tag.getId()));
			return tag;
		}).collect(Collectors.toList());
	}

	public List<Category> addCategoryLinks(List<Category> categories) {
		return categories.stream().map(category -> {
			category.setLink(createLinkWithId(category.getName(), category.getId()));

			if (category.getChildren() != null) {
				category.setChildren(addCategoryLinks(category.getChildren()));
			}
			return category;
		}).collect(Collectors.toList());
	}

	public Pagination createPagination(PostsPage page, Paging paging) {
		Pagination pagination = new Pagination();
		if (page.getHasNext()) {
			pagination.setNextLink(escapedLinkPaginated(paging, NEXT));
		}
		if (page.getHasPrevious()) {
			pagination.setPreviousLink(escapedLinkPaginated(paging, PREVIOUS));
		}
		return pagination;
	}

	private String escapedLinkPaginated(Paging paging, int direction) {
		return createEscapedLink(paging.getPath()) + "/" + (int)(paging.getPage() + direction);
	}

	public Long recoverIdFromLink(String link) throws InvalidRequestUrlException {
		String possibleId = StringUtils.substringAfterLast(link, "_");
		
		if(!NumberUtils.isParsable(possibleId)) {
			throw new InvalidRequestUrlException("Link " + link + " is missing _{ID}!");
		}
		return NumberUtils.toLong(possibleId);
	}
	
	private String createLinkWithId(String name, Long id){
		return createEscapedLink(name) + "_"  + id;
	}
	
	private String createEscapedLink(String name) {
		try {
			name = URLDecoder.decode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}
}
