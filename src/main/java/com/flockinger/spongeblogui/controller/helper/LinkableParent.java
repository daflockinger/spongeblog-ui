package com.flockinger.spongeblogui.controller.helper;

import java.util.List;

public interface LinkableParent<T extends Linkable> extends Linkable {
	List<T> getChildren();
	void setChildren(List<T> children);
}
