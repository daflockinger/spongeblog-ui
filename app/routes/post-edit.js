import Ember from 'ember';
import RSVP from 'rsvp';

export default Ember.Route.extend({
  session: Ember.inject.service('session'),
  model(params) {
    return RSVP.hash({
      isNewPost: Ember.computed('id',function(){
        return params.id == '0';
      }),
      postStore: this.get('store'),
      postStatuses: ["PUBLIC", "PRIVATE", "MAINTENANCE", "DELETED"],
      post: Ember.computed('id',function(){
        if(params.id === '0'){ //new post
          var newPost = {};
          newPost.keywords = Ember.A();
          return newPost;
        } else {
          return this.postStore.findRecord('post', params.id);
        }
      }),
      categories: this.get('store').findAll('category'),
      keywords: this.get('store').findAll('keyword'),
      btns: [
        ['viewHTML'],
        ['undo', 'redo'],
        ['formatting'],
        'btnGrp-design',
        ['link'],
        'insertImage',
        'upload',
        'btnGrp-justify',
        'btnGrp-lists',
        ['foreColor', 'backColor'],
      //  ['preformatted'],
        ['horizontalRule'],
        ['fullscreen']
    ],
    });
  },
  actions: {
    insert(newPost) {
      var content =  $(".trumbowyg-editor").html();
      var user = this.get('session.data.user');

      var latestPost = this.get('store').createRecord('Post',{
          title: newPost.title,
          user: user,
          created: new Date(),
          content: content,
          postStatus: newPost.postStatus,
          keywords: newPost.keywords,
          category: newPost.category,
          noPost: newPost.noPost,
      });
      latestPost.save();
      Materialize.toast('Post created.',2500);
      this.refresh();
    },
    save(editedPost){
      var content =  $(".trumbowyg-editor").html();
      console.log('editedPost  ' + editedPost.get('keywords') );

      this.get('store').findRecord('post',editedPost.get('id'))
                     .then(function(post){
                       post.set('modified',new Date());
                       post.set('title',editedPost.get('title'));
                       post.set('content',content);
                       post.set('postStatus',editedPost.get('postStatus'));
                       post.set('keywords',editedPost.get('keywords'));
                       post.set('category',editedPost.get('category'));
                       post.set('noPost',editedPost.get('noPost'));
                       post.save();
                   });
      Materialize.toast('Post saved.',2500);
      this.refresh();
    },
  },
});
