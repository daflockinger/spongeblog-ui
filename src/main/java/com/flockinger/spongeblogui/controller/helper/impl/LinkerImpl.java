package com.flockinger.spongeblogui.controller.helper.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.flockinger.spongeblogui.controller.helper.Linkable;
import com.flockinger.spongeblogui.controller.helper.LinkableParent;
import com.flockinger.spongeblogui.controller.helper.Linker;
import com.flockinger.spongeblogui.controller.helper.Navigable;
import com.flockinger.spongeblogui.controller.helper.Postable;
import com.flockinger.spongeblogui.dto.Pagination;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.PreviewPost;
import com.flockinger.spongeblogui.exception.InvalidRequestUrlException;

import wiremock.org.apache.commons.lang3.math.NumberUtils;


@Component
public class LinkerImpl implements Linker {

	private final static int NEXT = 1;
	private final static int PREVIOUS = -1;

	public <P extends Postable> List<P> addPostLinks(List<P> posts) {
		return posts.stream().map(this::addPostSideLinks).collect(Collectors.toList());
	}

	public <P extends Postable> P addPostSideLinks(P post) {
		post.setLink(createLinkWithId(post.getTitle(),post.getPostId()));
		post.setTags(addLinks(post.getTags()));
		post.setUser(addLink(post.getUser()));
		return post;
	}
	
	private <L extends Linkable> L addLink(L linkable){
		linkable.setLink(createLinkWithId(linkable.getName(), linkable.getId()));
		return linkable;
	}
	
	private <L extends Linkable> List<L> addLinks(List<L> linkables) {
		return linkables.stream().map(this::addLink).collect(Collectors.toList());
	}
	
	public <L extends LinkableParent<L>> List<L> addLinksWithChildren(List<L> linkables) {
		return linkables.stream().map(this::addLinkWithChildren).collect(Collectors.toList());
	}
	
	private <L extends LinkableParent<L>> L addLinkWithChildren(L linkable){
		if (linkable.getChildren() != null) {
			linkable.setChildren(addLinks(linkable.getChildren()));
		}
		return addLink(linkable);
	}

	public Pagination createPagination(Navigable page, Paging paging) {
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
