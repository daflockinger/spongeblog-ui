package com.flockinger.spongeblogui.controller.helper;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.flockinger.spongeblogui.controller.helper.impl.LinkerImpl;
import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.dto.Pagination;
import com.flockinger.spongeblogui.dto.Paging;
import com.flockinger.spongeblogui.dto.Post;
import com.flockinger.spongeblogui.dto.PostsPage;
import com.flockinger.spongeblogui.dto.PreviewPost;
import com.flockinger.spongeblogui.dto.Tag;
import com.flockinger.spongeblogui.dto.UserInfo;
import com.flockinger.spongeblogui.exception.InvalidRequestUrlException;
import com.google.common.collect.ImmutableList;


public class LinkerTest {

  private Linker linker = new LinkerImpl();
  
  @Test
  public void testAddPostLinks_withNormalPosts_shouldReturnCorrectLinks() {
    PreviewPost post1 = new PreviewPost();
    post1.setPostId(23l);
    post1.setTitle("And you will know My name is the Lord when I lay My vengeance upon thee");
    UserInfo user = new UserInfo();
    user.setName("sepp");
    user.setId(2l);
    post1.setUser(user);
    PreviewPost post2 = new PreviewPost();
    post2.setPostId(8l);
    post2.setTags(ImmutableList.of(new Tag("s.l.j.",3l),new Tag("guns",9l)));
    post2.setTitle("You think water moves fast? You should see ice.");
    List<PreviewPost> linkedPosts = linker.addPostLinks(ImmutableList.of(post1,post2));
    
    assertEquals("validate post link","You_think_water_moves_fast%3F_You_should_see_ice_8",
        linkedPosts.stream().filter(p->p.getPostId()==8l).findAny().get().getLink());
    
    assertTrue("validate post first tag link",linkedPosts.stream().filter(p->p.getPostId()==8l)
        .findAny().get().getTags().stream().anyMatch(t->t.getLink().equals("slj_3")));    
    assertTrue("validate post second tag link",linkedPosts.stream().filter(p->p.getPostId()==8l)
        .findAny().get().getTags().stream().anyMatch(t->t.getLink().equals("guns_9")));
    
    assertEquals("validate second post link","And_you_will_know_My_name_is_the_Lord_when_I_lay_My_vengeance_upon_thee_23",
        linkedPosts.stream().filter(p->p.getPostId()==23l).findAny().get().getLink());
    assertEquals("validate second post user link","sepp_2",
        linkedPosts.stream().filter(p->p.getPostId()==23l).findAny().get().getUser().getLink());
  }
  
  @Test
  public void testAddPostLinks_withPostsWithoutIds_shouldCreateNoLinks() {
    PreviewPost post1 = new PreviewPost();
    post1.setTitle("And you will know My name is the Lord when I lay My vengeance upon thee");
    UserInfo user = new UserInfo();
    user.setName("sepp");
    post1.setUser(user);
    PreviewPost post2 = new PreviewPost();
    post2.setTags(ImmutableList.of(new Tag("s.l.j.",null),new Tag("guns",null)));
    post2.setTitle("You think water moves fast? You should see ice.");
    List<PreviewPost> linkedPosts = linker.addPostLinks(ImmutableList.of(post1,post2));
    
    assertTrue("validate no post link present",
        linkedPosts.stream().filter(p->p.getTitle().startsWith("You think")).findAny().get().getLink() == null);
    
    assertTrue("validate no tag link present",linkedPosts.stream().filter(p->p.getTitle().startsWith("You think"))
        .findAny().get().getTags().stream().allMatch(t->t.getLink()==null));    
    
    assertTrue("validate no second post link present",
        linkedPosts.stream().filter(p->p.getTitle().startsWith("And you")).findAny().get().getLink() == null);
    assertTrue("validate no user link present",
        linkedPosts.stream().filter(p->p.getTitle().startsWith("And you")).findAny().get().getUser().getLink()==null);
  }
  
  @Test
  public void testAddPostLinks_withEmptyNames_shouldCreateNoLinks() {
    PreviewPost post1 = new PreviewPost();
    post1.setPostId(23l);
    post1.setTitle("");
    UserInfo user = new UserInfo();
    user.setName("");
    user.setId(2l);
    post1.setUser(user);
    PreviewPost post2 = new PreviewPost();
    post2.setPostId(8l);
    post2.setTags(ImmutableList.of(new Tag("",3l),new Tag("",9l)));
    post2.setTitle("");
    List<PreviewPost> linkedPosts = linker.addPostLinks(ImmutableList.of(post1,post2));

    assertTrue("validate no post link present",
        linkedPosts.stream().filter(p->p.getPostId()==8l).findAny().get().getLink() == null);
    
    assertTrue("validate no tag link present",linkedPosts.stream().filter(p->p.getPostId()==8l)
        .findAny().get().getTags().stream().allMatch(t->t.getLink()==null));    
    
    assertTrue("validate no second post link present",
        linkedPosts.stream().filter(p->p.getPostId()==23l).findAny().get().getLink() == null);
    assertTrue("validate no user link present",
        linkedPosts.stream().filter(p->p.getPostId()==23l).findAny().get().getUser().getLink()==null);
  }
  
  @Test
  public void testAddPostSideLinks_withValidPost_shouldAddSideLinks() {
    Post post1 = new Post();
    post1.setTags(ImmutableList.of(new Tag("YOLO",3l),new Tag("90's",9l)));
    UserInfo user = new UserInfo();
    user.setName("Occupy vegan typewriter");
    user.setId(2l);
    post1.setUser(user);
    
    Post linkedPost = linker.addPostSideLinks(post1);
    
    assertTrue("validate post first tag link",linkedPost.getTags().stream().anyMatch(t->t.getLink().equals("YOLO_3")));    
    assertTrue("validate post second tag link",linkedPost.getTags().stream().anyMatch(t->t.getLink().equals("90%27s_9")));
    assertEquals("validate post user link","Occupy_vegan_typewriter_2",linkedPost.getUser().getLink());
  }
  
  @Test
  public void testAddPostSideLinks_withPostWithoutUser_shouldAddSideLinks() {
    Post post1 = new Post();
    post1.setTags(ImmutableList.of(new Tag("YOLO",3l),new Tag("90's",9l)));
    
    
    Post linkedPost = linker.addPostSideLinks(post1);
    
    assertTrue("validate post first tag link",linkedPost.getTags().stream().anyMatch(t->t.getLink().equals("YOLO_3")));    
    assertTrue("validate post second tag link",linkedPost.getTags().stream().anyMatch(t->t.getLink().equals("90%27s_9")));
  }
  
  @Test
  public void testAddPostSideLinks_withPostWithoutTags_shouldAddSideLinks() {
    Post post1 = new Post();
    UserInfo user = new UserInfo();
    user.setName("Occupy vegan typewriter");
    user.setId(2l);
    post1.setUser(user);
    
    Post linkedPost = linker.addPostSideLinks(post1);
    assertEquals("validate post user link","Occupy_vegan_typewriter_2",linkedPost.getUser().getLink());
  }
  
  @Test
  public void testAddLinksWithChildren_withValidCategories_shouldReturnLinks() {
    Category cat1 = new Category();
    cat1.setId(35l);
    cat1.setName("organic pitchfork");
    Category sub1 = new Category();
    sub1.setId(676l);
    sub1.setName("Waistcoat");
    Category sub2 = new Category();
    sub2.setId(65l);
    sub2.setName("Gastropub");
    cat1.setChildren(ImmutableList.of(sub1,sub2));
    Category cat2 = new Category();
    cat2.setId(35l);
    cat2.setName("Bootstrap");
    
    List<Category> linkedCategories = linker.addLinksWithChildren(ImmutableList.of(cat1,cat2));
    
    assertEquals("validate first category link","organic_pitchfork_35",linkedCategories.get(0).getLink());
    assertEquals("validate first cat child link","Waistcoat_676",linkedCategories.get(0).getChildren().get(0).getLink());
    assertEquals("validate second cat child link","Gastropub_65",linkedCategories.get(0).getChildren().get(1).getLink());
    
    assertEquals("validate second category link","Bootstrap_35",linkedCategories.get(1).getLink());
  }
  
  @Test
  public void testAddLinksWithChildren_withMissingStuff_shouldReturnNoLinks() {
    Category cat1 = new Category();
    cat1.setId(null);
    cat1.setName("organic pitchfork");
    Category sub1 = new Category();
    sub1.setId(676l);
    sub1.setName("");
    Category sub2 = new Category();
    sub2.setId(null);
    sub2.setName("Gastropub");
    cat1.setChildren(ImmutableList.of(sub1,sub2));
    Category cat2 = new Category();
    cat2.setId(35l);
    cat2.setName(null);
    
    List<Category> linkedCategories = linker.addLinksWithChildren(ImmutableList.of(cat1,cat2));
    
    assertNull("verify first category link not present",linkedCategories.get(0).getLink());
    assertNull("verify cat first child link not present",linkedCategories.get(0).getChildren().get(0).getLink());
    assertNull("verify cat second child link not present",linkedCategories.get(0).getChildren().get(1).getLink());
    assertNull("verify second category link not present",linkedCategories.get(1).getLink());
  }
  
  @Test
  public void testCreatePagination_withCorrectValues_shouldWork() {
    PostsPage page = new PostsPage();
    page.setHasNext(true);
    page.setHasPrevious(true);
    page.setTotalPages(324);
    Paging paging = new Paging();
    paging.setPage(2);
    paging.setSize(25);
    paging.setPath("somewhere/no where");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertEquals("validate correct previous link","/somewhere/no_where/1",resultPagination.getPreviousLink());
    assertEquals("validate correct next link","/somewhere/no_where/3",resultPagination.getNextLink());
  }
  
  @Test
  public void testCreatePagination_withFirstPage_shouldHaveNoPrevious() {
    PostsPage page = new PostsPage();
    page.setHasNext(true);
    page.setHasPrevious(true);
    page.setTotalPages(324);
    Paging paging = new Paging();
    paging.setPage(0);
    paging.setSize(25);
    paging.setPath("somewhere/no where");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertNull("validate empty previous link",resultPagination.getPreviousLink());
    assertEquals("validate correct next link","/somewhere/no_where/1",resultPagination.getNextLink());
  }
  
  @Test
  public void testCreatePagination_withFirstPageAlsoDeclaredInPage_shouldHaveNoPrevious() {
    PostsPage page = new PostsPage();
    page.setHasNext(true);
    page.setHasPrevious(false);
    page.setTotalPages(324);
    Paging paging = new Paging();
    paging.setPage(0);
    paging.setSize(25);
    paging.setPath("somewhere/bla bla");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertNull("validate empty previous link",resultPagination.getPreviousLink());
    assertEquals("validate correct next link","/somewhere/bla_bla/1",resultPagination.getNextLink());
  }
  
  @Test
  public void testCreatePagination_withLastPage_shouldHaveNoNext() {
    PostsPage page = new PostsPage();
    page.setHasNext(true);
    page.setHasPrevious(true);
    page.setTotalPages(3);
    Paging paging = new Paging();
    paging.setPage(3);
    paging.setSize(25);
    paging.setPath("somewhere/no where");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertEquals("validate correct previous link","/somewhere/no_where/2",resultPagination.getPreviousLink());
    assertNull("validate empty next link",resultPagination.getNextLink());
  }
  
  @Test
  public void testCreatePagination_withLastPagedeclared_shouldHaveNoNext() {
    PostsPage page = new PostsPage();
    page.setHasNext(false);
    page.setHasPrevious(true);
    page.setTotalPages(37);
    Paging paging = new Paging();
    paging.setPage(3);
    paging.setSize(25);
    paging.setPath("somewhere/no where");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertEquals("validate correct previous link","/somewhere/no_where/2",resultPagination.getPreviousLink());
    assertNull("validate empty next link",resultPagination.getNextLink());
  }
  
  @Test
  public void testCreatePagination_withOnlyOnePage_shouldHaveNoPaginationLinks() {
    PostsPage page = new PostsPage();
    page.setHasNext(false);
    page.setHasPrevious(false);
    page.setTotalPages(1);
    Paging paging = new Paging();
    paging.setPage(0);
    paging.setSize(25);
    paging.setPath("somewhere/no where");
    
    Pagination resultPagination = linker.createPagination(page, paging);
    
    assertNull("validate empty previous link",resultPagination.getPreviousLink());
    assertNull("validate empty next link",resultPagination.getNextLink());
  }
  
  @Test
  public void testRecoverIdFromLink_withValidLink_shouldRecover() {
    Long id = linker.recoverIdFromLink("posts/some_very_loud!!topic_%3Fwith_3_things_89");
    assertEquals("validate extraced id",89l,id.longValue());
  }
  
  @Test
  public void testRecoverIdFromLink_withFunkyLink_shouldRecover() {
    Long id = linker.recoverIdFromLink("posts/23_48_57_9");
    assertEquals("validate extraced id",9l,id.longValue());
  }
  
  @Test(expected=InvalidRequestUrlException.class)
  public void testRecoverIdFromLink_withMissingDashBeforeId_shouldThrowException() {
    Long id = linker.recoverIdFromLink("posts/some_very_loud!!topic_%3Fwith_things89");
    assertEquals("validate extraced id",9l,id.longValue());
  }
  
  @Test(expected=InvalidRequestUrlException.class)
  public void testRecoverIdFromLink_withMissingIdInLink_shouldThrowException() {
    linker.recoverIdFromLink("posts/some_very_loud!!topic_%3Fwith_things");
  }
  
  @Test(expected=InvalidRequestUrlException.class)
  public void testRecoverIdFromLink_withMissingIdNotANumber_shouldThrowException() {
    linker.recoverIdFromLink("posts/some_very_loud!!topic_%3Fwith_things_3o7");
  }
}
