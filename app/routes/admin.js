import Ember from 'ember';
import RSVP from 'rsvp';
import AuthenticatedRouteMixin from 'ember-simple-auth/mixins/authenticated-route-mixin';

export default Ember.Route.extend(AuthenticatedRouteMixin,{
  model() {
    return RSVP.hash({
      users: this.get('store').findAll('user'),
      blog: this.get('store').findAll('blog')
    });
  },
  actions: {
    update(updateBlog) {
       this.get('store').findRecord('blog',updateBlog.get('id'))
                       .then(function(blog){
                         blog.set('name',updateBlog.get('name'));
                         blog.set('status',updateBlog.get('status'));
                         blog.set('settings.sub_title',updateBlog.get('settings.sub_title'));
                         blog.set('settings.main_background_url',updateBlog.get('settings.main_background_url'));
                         blog.set('settings.footer_copyright_text',updateBlog.get('settings.footer_copyright_text'));
                         blog.set('settings.social_twitter_link',updateBlog.get('settings.social_twitter_link'));
                         blog.set('settings.social_facebook_link',updateBlog.get('settings.social_facebook_link'));
                         blog.set('settings.social_instagram_link',updateBlog.get('settings.social_instagram_link'));
                         blog.set('settings.social_reddit_link',updateBlog.get('settings.social_reddit_link'));
                         blog.set('settings.social_googlep_link',updateBlog.get('settings.social_googlep_link'));
                         blog.set('settings.social_youtube_link',updateBlog.get('settings.social_youtube_link'));
                         //blog.set('',updateBlog.get(''));
                         blog.save();
                       });
        Materialize.toast('Settings saved.',2500);
      this.refresh();

    },
  },
});
