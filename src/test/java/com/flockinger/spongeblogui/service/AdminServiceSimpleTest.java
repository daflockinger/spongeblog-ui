package com.flockinger.spongeblogui.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.flockinger.spongeblogui.dto.PostFilter;
import com.flockinger.spongeblogui.dto.PostQuery;
import com.flockinger.spongeblogui.resource.AdminClient;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;
import com.flockinger.spongeblogui.resource.dto.PostDTO;
import com.flockinger.spongeblogui.resource.dto.PostStatus;
import com.flockinger.spongeblogui.resource.dto.PostsPageDTO;
import com.flockinger.spongeblogui.resource.dto.TagDTO;
import com.flockinger.spongeblogui.resource.dto.UserEditDTO;
import com.flockinger.spongeblogui.service.impl.AdminServiceImpl;

@RunWith(SpringRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
		MockitoTestExecutionListener.class })
@ContextConfiguration(classes = { AdminServiceImpl.class, AdminClient.class, ModelMapper.class })
public class AdminServiceSimpleTest {

	@Autowired
	private AdminService service;

	@MockBean
	private AdminClient client;

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Test
	public void testGetUsers_shouldPushUnmodified() throws Exception {
		List<UserEditDTO> users = new ArrayList<>();

		when(client.getUsers()).thenReturn(users);

		assertEquals("should return unmodified", users, service.getUsers());
	}

	@Test
	public void testGetUser_shouldPushUnmodified() throws Exception {
		UserEditDTO user = new UserEditDTO();

		when(client.getUser(anyLong())).thenReturn(user);

		assertEquals("should return unmodified", user, service.getUser(3l));
	}

	@Test
	public void testCreateUser_shouldPushUnmodified() throws Exception {
		UserEditDTO user = new UserEditDTO();
		ArgumentCaptor<UserEditDTO> catchUser = ArgumentCaptor.forClass(UserEditDTO.class);
		when(client.createUser(catchUser.capture())).thenReturn(user);

		service.createUser(user);

		assertEquals("should call unmodified", user, catchUser.getValue());
	}

	@Test
	public void testUpdateUser_shouldPushUnmodified() throws Exception {
		UserEditDTO user = new UserEditDTO();
		ArgumentCaptor<UserEditDTO> catchUser = ArgumentCaptor.forClass(UserEditDTO.class);
		doNothing().when(client).updateUser(catchUser.capture());

		service.updateUser(user);

		assertEquals("should call unmodified", user, catchUser.getValue());
	}

	@Test
	public void testDeleteUser_shouldExecuteClientDelete() throws Exception {
		doNothing().when(client).deleteUser(anyLong());

		service.deleteUser(3l);

		verify(client, times(1)).deleteUser(anyLong());
	}

	@Test
	public void testGetCategories_shouldPushUnmodified() throws Exception {
		List<CategoryDTO> categories = new ArrayList<>();

		when(client.getCategories()).thenReturn(categories);

		assertEquals("should return unmodified", categories, service.getCategories());
	}

	@Test
	public void testGetCategory_shouldPushUnmodified() throws Exception {
		CategoryDTO category = new CategoryDTO();

		when(client.getCategory(anyLong())).thenReturn(category);

		assertEquals("should return unmodified", category, service.getCategory(3l));
	}

	@Test
	public void testCreateCategory_shouldPushUnmodified() throws Exception {
		CategoryDTO category = new CategoryDTO();
		ArgumentCaptor<CategoryDTO> catchCategory = ArgumentCaptor.forClass(CategoryDTO.class);
		when(client.createCategory(catchCategory.capture())).thenReturn(category);

		service.createCategory(category);

		assertEquals("should call unmodified", category, catchCategory.getValue());
	}

	@Test
	public void testUpdateCategory_shouldPushUnmodified() throws Exception {
		CategoryDTO category = new CategoryDTO();
		ArgumentCaptor<CategoryDTO> catchCategory = ArgumentCaptor.forClass(CategoryDTO.class);
		doNothing().when(client).updateCategory(catchCategory.capture());

		service.updateCategory(category);

		assertEquals("should call unmodified", category, catchCategory.getValue());
	}

	@Test
	public void testDeleteCategory_shouldExecuteClientDelete() throws Exception {
		doNothing().when(client).deleteCategory(anyLong());

		service.deleteCategory(3l);

		verify(client, times(1)).deleteCategory(anyLong());
	}

	@Test
	public void testGetTags_shouldPushUnmodified() throws Exception {
		List<TagDTO> tags = new ArrayList<>();

		when(client.getTags()).thenReturn(tags);

		assertEquals("should return unmodified", tags, service.getTags());
	}

	@Test
	public void testGetTag_shouldPushUnmodified() throws Exception {
		TagDTO category = new TagDTO();

		when(client.getTag(anyLong())).thenReturn(category);

		assertEquals("should return unmodified", category, service.getTag(3l));
	}

	@Test
	public void testCreateTag_shouldPushUnmodified() throws Exception {
		TagDTO category = new TagDTO();
		ArgumentCaptor<TagDTO> catchTag = ArgumentCaptor.forClass(TagDTO.class);
		when(client.createTag(catchTag.capture())).thenReturn(category);

		service.createTag(category);

		assertEquals("should call unmodified", category, catchTag.getValue());
	}

	@Test
	public void testUpdateTag_shouldPushUnmodified() throws Exception {
		TagDTO category = new TagDTO();
		ArgumentCaptor<TagDTO> catchTag = ArgumentCaptor.forClass(TagDTO.class);
		doNothing().when(client).updateTag(catchTag.capture());

		service.updateTag(category);

		assertEquals("should call unmodified", category, catchTag.getValue());
	}

	@Test
	public void testDeleteTag_shouldExecuteClientDelete() throws Exception {
		doNothing().when(client).deleteTag(anyLong());

		service.deleteTag(3l);

		verify(client, times(1)).deleteTag(anyLong());
	}

	@Test
	public void testGetPost_shouldPushUnmodified() throws Exception {
		PostDTO category = new PostDTO();

		when(client.getPost(anyLong())).thenReturn(category);

		assertEquals("should return unmodified", category, service.getPost(3l));
	}

	@Test
	public void testCreatePost_shouldPushUnmodified() throws Exception {
		PostDTO category = new PostDTO();
		ArgumentCaptor<PostDTO> catchPost = ArgumentCaptor.forClass(PostDTO.class);
		when(client.createPost(catchPost.capture())).thenReturn(category);

		service.createPost(category);

		assertEquals("should call unmodified", category, catchPost.getValue());
	}

	@Test
	public void testUpdatePost_shouldPushUnmodified() throws Exception {
		PostDTO category = new PostDTO();
		ArgumentCaptor<PostDTO> catchPost = ArgumentCaptor.forClass(PostDTO.class);
		doNothing().when(client).updatePost(catchPost.capture());

		service.updatePost(category);

		assertEquals("should call unmodified", category, catchPost.getValue());
	}

	@Test
	public void testDeletePost_shouldExecuteClientDelete() throws Exception {
		doNothing().when(client).deletePost(anyLong());

		service.deletePost(3l);

		verify(client, times(1)).deletePost(anyLong());
	}

	@Test
	public void testGetAllPosts_withCorrectPaging_shouldPushCorrect() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPosts(catchPage.capture(), catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.all().page(1).size(6).build());
		
		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 1, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 6, catchSize.getValue().intValue());

		verify(client, times(1)).getPosts(anyInt(), anyInt());
		reset(client);
	}

	@Test
	public void testGetAllPosts_withEmptyPaging_shouldPushCorrect() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPosts(catchPage.capture(), catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service
				.getPostsByQuery(PostQuery.all().size(6).build());

		assertEquals("should push through", posts, result);
		assertEquals("should fall back to default page 0", 0, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 6, catchSize.getValue().intValue());

		verify(client, times(1)).getPosts(anyInt(), anyInt());
		reset(client);
	}

	@Test
	public void testGetPostsByStatus_withCorrectPaging_shouldPushCorrect() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> catchStatus = ArgumentCaptor.forClass(String.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByStatus(catchStatus.capture(), catchPage.capture(), catchSize.capture()))
				.thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.all().status(PostStatus.PUBLIC).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct post status", PostStatus.PUBLIC.toString(), catchStatus.getValue());

		verify(client, times(1)).getPostsByStatus(matches(PostStatus.PUBLIC.toString()), anyInt(), anyInt());
	}

	@Test
	public void testGetPostsByUser_withPublicStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> catchStatus = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> catchUser = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByUserAndStatus(catchUser.capture(), catchStatus.capture(), catchPage.capture(),
				catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.USER).id(34l).status(PostStatus.PUBLIC).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct post status", PostStatus.PUBLIC.toString(), catchStatus.getValue());
		assertEquals("should push correct user id", 34l, catchUser.getValue().longValue());

		verify(client, times(1)).getPostsByUserAndStatus(anyLong(), matches(PostStatus.PUBLIC.toString()), anyInt(),
				anyInt());
	}

	@Test
	public void testGetPostsByUser_withAllStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Long> catchUser = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByUser(catchUser.capture(), catchPage.capture(), catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.USER).id(34l).status(PostStatus.ALL).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct user id", 34l, catchUser.getValue().longValue());

		verify(client, times(1)).getPostsByUser(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void testGetPostsByTag_withPublicStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> catchStatus = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> catchTag = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByTagAndStatus(catchTag.capture(), catchStatus.capture(), catchPage.capture(),
				catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.TAG).id(34l).status(PostStatus.PUBLIC).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct post status", PostStatus.PUBLIC.toString(), catchStatus.getValue());
		assertEquals("should push correct user id", 34l, catchTag.getValue().longValue());

		verify(client, times(1)).getPostsByTagAndStatus(anyLong(), matches(PostStatus.PUBLIC.toString()), anyInt(),
				anyInt());
	}

	@Test
	public void testGetPostsByTag_withAllStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Long> catchTag = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByTag(catchTag.capture(), catchPage.capture(), catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.TAG).id(34l).status(PostStatus.ALL).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct user id", 34l, catchTag.getValue().longValue());

		verify(client, times(1)).getPostsByTag(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void testGetPostsByCategory_withPublicStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> catchStatus = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Long> catchCategory = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByCategoryAndStatus(catchCategory.capture(), catchStatus.capture(), catchPage.capture(),
				catchSize.capture())).thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.CATEGORY).id(34l).status(PostStatus.PUBLIC).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct post status", PostStatus.PUBLIC.toString(), catchStatus.getValue());
		assertEquals("should push correct user id", 34l, catchCategory.getValue().longValue());

		verify(client, times(1)).getPostsByCategoryAndStatus(anyLong(), matches(PostStatus.PUBLIC.toString()), anyInt(),
				anyInt());
	}

	@Test
	public void testGetPostsByCategory_withAllStatus_shouldPushCorrectApiCall() throws Exception {
		ArgumentCaptor<Integer> catchPage = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> catchSize = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Long> catchCategory = ArgumentCaptor.forClass(Long.class);
		PostsPageDTO posts = new PostsPageDTO();
		when(client.getPostsByCategory(catchCategory.capture(), catchPage.capture(), catchSize.capture()))
				.thenReturn(posts);

		PostsPageDTO result = service.getPostsByQuery(PostQuery.filter(PostFilter.CATEGORY).id(34l).status(PostStatus.ALL).page(3).size(4).build());

		assertEquals("should push through", posts, result);
		assertEquals("should use defined page number", 3, catchPage.getValue().intValue());
		assertEquals("should use defined page size", 4, catchSize.getValue().intValue());
		assertEquals("should push correct user id", 34l, catchCategory.getValue().longValue());

		verify(client, times(1)).getPostsByCategory(anyLong(), anyInt(), anyInt());
	}
}
