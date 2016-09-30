import Ember from 'ember';
import RSVP from 'rsvp';


export default Ember.Route.extend({
  model(params) {
    return RSVP.hash({
      keyword : params.keyword,
      page : params.page,
      posts: this.get('store').query('post', { page: params.page, limit: 5,
        sort: 'created',orderasc:false, postStatus: 'PUBLIC', keywords: params.keyword}),
      categories: this.get('store').findAll('category'),
      blog: this.get('store').findAll('blog')
  });
  }
});
