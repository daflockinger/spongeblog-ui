package com.flockinger.spongeblogui.controller.helper;

import java.util.List;

import com.flockinger.spongeblogui.dto.Tag;
import com.flockinger.spongeblogui.dto.UserInfo;
import com.flockinger.spongeblogui.resource.dto.UserInfoDTO;

public interface Postable {
	UserInfo getUser();
	void setUser(UserInfo user);
	List<Tag> getTags();
	void setTags(List<Tag> tags);
	
	default String getTitle(){
		return "";
	}
	default Long getPostId(){
		return 0l;
	}
	default void setLink(String link) {}
}
