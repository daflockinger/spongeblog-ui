package com.flockinger.spongeblogui.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.flockinger.spongeblogui.config.TemplatingConfig;
import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.Post;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.dto.PreviewPost;
import com.flockinger.spongeblogui.dto.Tag;
import com.flockinger.spongeblogui.resource.BlogClient;
import com.flockinger.spongeblogui.resource.dto.BlogDTO;
import com.flockinger.spongeblogui.resource.dto.BlogStatus;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostPreviewDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserInfoDTO;
import com.flockinger.spongeblogui.service.impl.AdminServiceImpl;
import com.flockinger.spongeblogui.service.impl.BlogServiceImpl;
import com.google.common.collect.ImmutableMap;

import wiremock.com.google.common.collect.ImmutableList;

@RunWith(SpringRunner.class)
@TestExecutionListeners(listeners={DependencyInjectionTestExecutionListener.class,MockitoTestExecutionListener.class})
@ContextConfiguration(classes={BlogServiceImpl.class,BlogClient.class,TemplatingConfig.class})
public class BlogServiceSimpleTest {

	@Autowired
	private BlogService service;

	@MockBean
	private BlogClient client;
	
	@Test
	public void testGetBlogSettings_shouldPushAndMapToValidBlog() throws Exception {
		// prepare
		BlogDTO blog = new BlogDTO();
		blog.setName("some blog");
		blog.setStatus(BlogStatus.ACTIVE);
		blog.setSettings(
				ImmutableMap.of("title", "my blog", "backgroundImage", "sun.jpg", "copyright", "free for all"));

		when(client.getBlog()).thenReturn(blog);

		// execute
		Map<String, String> settings = service.getBlogSettings();

		// assert
		assertNotNull("returned map must not be null", settings);
		assertEquals("validate correct name", "some blog", settings.get(AdminServiceImpl.BLOG_NAME));
		assertEquals("validate correct status", "ACTIVE", settings.get(AdminServiceImpl.BLOG_STATUS));
		assertEquals("validate correct title", "my blog", settings.get("title"));
		assertEquals("validate correct background image", "sun.jpg", settings.get("backgroundImage"));
		assertEquals("validate correct copyright", "free for all", settings.get("copyright"));
	}

	@Test
	public void testGetCategories_shouldMapCorrectly() throws Exception {
		// prepare to send
		CategoryDTO cat1 = new CategoryDTO();
		cat1.setCategoryId(2l);
		cat1.setName("uber category");
		cat1.setParentId(null);
		CategoryDTO cat2 = new CategoryDTO();
		cat2.setCategoryId(3l);
		cat2.setName("secondary stuff");
		cat2.setParentId(null);
		List<CategoryDTO> categories = ImmutableList.of(cat1, cat2);

		CategoryDTO sub1 = new CategoryDTO();
		sub1.setCategoryId(22l);
		sub1.setName("sub sandwich");
		sub1.setParentId(null);
		CategoryDTO sub2 = new CategoryDTO();
		sub2.setCategoryId(23l);
		sub2.setName("uboot");
		sub2.setParentId(null);
		List<CategoryDTO> subs = ImmutableList.of(sub1, sub2);

		when(client.getCategories()).thenReturn(categories);
		when(client.getCategoriesFromParent(anyLong())).thenReturn(subs);

		Category subResult1 = new Category("sub sandwich", 22l, null);
		Category subResult2 = new Category("uboot", 23l, null);

		Category catResult1 = new Category("uber category", 2l, ImmutableList.of(subResult1, subResult2));
		Category catResult2 = new Category("secondary stuff", 3l, null);

		List<Category> results = service.getAllCategories();

		assertEquals("validate correct top categories size", 2, results.size());
		assertEquals("validate first category id", catResult1.getId(), results.get(0).getId());
		assertEquals("validate first category name", catResult1.getName(), results.get(0).getName());
		assertEquals("validate first category child count", catResult1.getChildren().size(),
				results.get(0).getChildren().size());
		assertEquals("validate first child id", subResult1.getId(), results.get(0).getChildren().get(0).getId());
		assertEquals("validate first child name", subResult1.getName(), results.get(0).getChildren().get(0).getName());
		assertEquals("validate second child id", subResult2.getId(), results.get(0).getChildren().get(1).getId());
		assertEquals("validate second child name", subResult2.getName(), results.get(0).getChildren().get(1).getName());
		assertEquals("validate second category id", catResult2.getId(), results.get(1).getId());
		assertEquals("validate second category name", catResult2.getName(), results.get(1).getName());

		verify(client, times(1)).getCategories();
		verify(client, times(2)).getCategoriesFromParent(anyLong());
	}

	@Test
	public void testGetTags_shouldMapCorrectly() throws Exception {
		TagDTO tag1 = new TagDTO();
		tag1.setName("light");
		tag1.setTagId(3l);
		TagDTO tag2 = new TagDTO();
		tag2.setName("bright");
		tag2.setTagId(7l);
		TagDTO tag3 = new TagDTO();
		tag3.setName("might");
		tag3.setTagId(6l);
		List<TagDTO> tags = ImmutableList.of(tag1, tag2, tag3);

		when(client.getTags()).thenReturn(tags);

		List<Tag> results = service.getAllTags();

		assertEquals("validate correct tags amount", 3, results.size());
		assertEquals("validate first Tag id", 3l, results.get(0).getId().longValue());
		assertEquals("validate first Tag name", "light", results.get(0).getName());
		assertEquals("validate first Tag id", 7l, results.get(1).getId().longValue());
		assertEquals("validate first Tag name", "bright", results.get(1).getName());
		assertEquals("validate first Tag id", 6l, results.get(2).getId().longValue());
		assertEquals("validate first Tag name", "might", results.get(2).getName());
	}

	@Test
	public void testGetPost_withValidPostId_shouldMapCorrectly() throws Exception {
		PostDTO sourcePost = getTestPost();

		when(client.getPost(anyLong())).thenReturn(sourcePost);

		Post result = service.getPost(2l);

		assertEquals("validate created date", 123l, result.getCreated().getTime());
		assertEquals("validate modified date", 124l, result.getModified().getTime());
		assertEquals("validate part-content",
				"Maybe you and me were never meant to be. But baby think of me once in awhile.", result.getContent());
		assertEquals("validate title", "Fish don't fry in the kitchen and beans don't burn on the grill.",
				result.getTitle());
		assertTrue("validate first tag",
				result.getTags().stream().anyMatch(tag -> tag.getName().equals("Beverly. Hills")));
		assertTrue("validate second tag",
				result.getTags().stream().anyMatch(tag -> tag.getName().equals("Sesame Street!")));
		assertEquals("validate ", 12l, result.getUser().getId().longValue());
		assertEquals("validate created date", "nick", result.getUser().getName());

	}

	@Test
	public void testGetPostsByCategory_withPublicStatus_shouldMapCorrectly() throws Exception {
		PostsPageDTO sourcePage = getTestPostSource();

		when(client.getPublicPostsByCategory(anyLong(), anyInt(), anyInt())).thenReturn(sourcePage);

		PostsPage result = service.getPostsForCategory(2l, new Paging(Optional.of(2), 1, ""));

		assertTrue(result.getHasPrevious());
		assertFalse(result.getHasNext());
		PreviewPost resultPost = result.getPreviewPosts().get(0);
		assertEquals("validate created date", 123l, resultPost.getCreated().getTime());
		assertEquals("validate modified date", 124l, resultPost.getModified().getTime());
		assertEquals("validate part-content",
				"Maybe you and me were never meant to be. But baby think of me once in awhile.",
				resultPost.getPartContent());
		assertEquals("validate title", "Fish don't fry in the kitchen and beans don't burn on the grill.",
				resultPost.getTitle());
		assertTrue("validate first tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Beverly. Hills")));
		assertTrue("validate second tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Sesame Street!")));
		assertEquals("validate ", 12l, resultPost.getUser().getId().longValue());
		assertEquals("validate created date", "nick", resultPost.getUser().getName());

	}

	@Test
	public void testGetPostsByTag_withPublicStatus_shouldMapCorrectly() throws Exception {
		PostsPageDTO sourcePage = getTestPostSource();

		when(client.getPublicPostsByTag(anyLong(), anyInt(), anyInt())).thenReturn(sourcePage);

		PostsPage result = service.getPostsForTag(2l, new Paging(Optional.of(2), 1, ""));

		assertTrue(result.getHasPrevious());
		assertFalse(result.getHasNext());
		PreviewPost resultPost = result.getPreviewPosts().get(0);
		assertEquals("validate created date", 123l, resultPost.getCreated().getTime());
		assertEquals("validate modified date", 124l, resultPost.getModified().getTime());
		assertEquals("validate part-content",
				"Maybe you and me were never meant to be. But baby think of me once in awhile.",
				resultPost.getPartContent());
		assertEquals("validate title", "Fish don't fry in the kitchen and beans don't burn on the grill.",
				resultPost.getTitle());
		assertTrue("validate first tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Beverly. Hills")));
		assertTrue("validate second tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Sesame Street!")));
		assertEquals("validate ", 12l, resultPost.getUser().getId().longValue());
		assertEquals("validate created date", "nick", resultPost.getUser().getName());
	}

	@Test
	public void testGetPostsByUser_withPublicStatus_shouldMapCorrectly() throws Exception {
		PostsPageDTO sourcePage = getTestPostSource();

		when(client.getPublicPostsByUser(anyLong(), anyInt(), anyInt())).thenReturn(sourcePage);

		PostsPage result = service.getPostsForUser(12l, new Paging(Optional.of(2), 1, ""));

		assertTrue(result.getHasPrevious());
		assertFalse(result.getHasNext());
		PreviewPost resultPost = result.getPreviewPosts().get(0);
		assertEquals("validate created date", 123l, resultPost.getCreated().getTime());
		assertEquals("validate modified date", 124l, resultPost.getModified().getTime());
		assertEquals("validate part-content",
				"Maybe you and me were never meant to be. But baby think of me once in awhile.",
				resultPost.getPartContent());
		assertEquals("validate title", "Fish don't fry in the kitchen and beans don't burn on the grill.",
				resultPost.getTitle());
		assertTrue("validate first tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Beverly. Hills")));
		assertTrue("validate second tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Sesame Street!")));
		assertEquals("validate ", 12l, resultPost.getUser().getId().longValue());
		assertEquals("validate created date", "nick", resultPost.getUser().getName());
	}

	@Test
	public void testGetPosts_withPublicStatus_shouldMapCorrectly() throws Exception {
		PostsPageDTO sourcePage = getTestPostSource();

		when(client.getPublicPosts(anyInt(), anyInt())).thenReturn(sourcePage);

		PostsPage result = service.getAllPosts(new Paging(Optional.of(2), 1, ""));

		assertTrue(result.getHasPrevious());
		assertFalse(result.getHasNext());
		PreviewPost resultPost = result.getPreviewPosts().get(0);
		assertEquals("validate created date", 123l, resultPost.getCreated().getTime());
		assertEquals("validate modified date", 124l, resultPost.getModified().getTime());
		assertEquals("validate part-content",
				"Maybe you and me were never meant to be. But baby think of me once in awhile.",
				resultPost.getPartContent());
		assertEquals("validate title", "Fish don't fry in the kitchen and beans don't burn on the grill.",
				resultPost.getTitle());
		assertTrue("validate first tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Beverly. Hills")));
		assertTrue("validate second tag",
				resultPost.getTags().stream().anyMatch(tag -> tag.getName().equals("Sesame Street!")));
		assertEquals("validate ", 12l, resultPost.getUser().getId().longValue());
		assertEquals("validate created date", "nick", resultPost.getUser().getName());
	}

	private PostDTO getTestPost() {
		PostDTO testPost = new PostDTO();
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("a@b.cc");
		user.setNickName("nick");
		user.setRegistered(12l);
		user.setUserId(12l);
		testPost.setAuthor(user);
		CategoryDTO category = new CategoryDTO();
		category.setCategoryId(2l);
		category.setName("Muppetational");
		category.setParentId(1l);
		testPost.setCategory(category);
		testPost.setCreated(new Date(123l));
		testPost.setModified(new Date(124l));
		testPost.setStatus(PostStatus.PUBLIC);
		testPost.setPostId(321l);
		testPost.setContent("Maybe you and me were never meant to be. But baby think of me once in awhile.");
		testPost.setTitle("Fish don't fry in the kitchen and beans don't burn on the grill.");
		TagDTO tag1 = new TagDTO();
		tag1.setName("Beverly. Hills");
		tag1.setTagId(1l);
		TagDTO tag2 = new TagDTO();
		tag2.setName("Sesame Street!");
		tag2.setTagId(2l);
		testPost.setTags(ImmutableList.of(tag1,tag2));

		return testPost;
	}

	private PostsPageDTO getTestPostSource() {
		PostPreviewDTO testPost = new PostPreviewDTO();
		UserInfoDTO user = new UserInfoDTO();
		user.setEmail("a@b.cc");
		user.setNickName("nick");
		user.setRegistered(12l);
		user.setUserId(12l);
		testPost.setAuthor(user);
		CategoryDTO category = new CategoryDTO();
		category.setCategoryId(2l);
		category.setName("Muppetational");
		category.setParentId(1l);
		testPost.setCategory(category);
		testPost.setCreated(new Date(123l));
		testPost.setModified(new Date(124l));
		testPost.setStatus(PostStatus.PUBLIC);
		testPost.setPostId(321l);
		testPost.setPartContent("Maybe you and me were never meant to be. But baby think of me once in awhile.");
		testPost.setTitle("Fish don't fry in the kitchen and beans don't burn on the grill.");
		TagDTO tag1 = new TagDTO();
		tag1.setName("Beverly. Hills");
		tag1.setTagId(1l);
		TagDTO tag2 = new TagDTO();
		tag2.setName("Sesame Street!");
		tag2.setTagId(2l);
		testPost.setTags(ImmutableList.of(tag1,tag2));

		PostsPageDTO sourcePage = new PostsPageDTO();
		sourcePage.setHasNext(false);
		sourcePage.setHasPrevious(true);
		sourcePage.setPreviewPosts(ImmutableList.of(testPost));

		return sourcePage;
	}
}
