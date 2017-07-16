package com.flockinger.spongeblogui.controller.helper;

import java.util.List;

import com.flockinger.spongeblogui.dto.Pagination;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.exception.InvalidRequestUrlException;

public interface Linker {
	public <P extends Postable> List<P> addPostLinks(List<P> posts);
	<P extends Postable> P addPostSideLinks(P post);
	<L extends LinkableParent<L>> List<L> addLinksWithChildren(List<L> linkables);
	Pagination createPagination(Navigable page, Paging paging);
	Long recoverIdFromLink(String link) throws InvalidRequestUrlException;
}
