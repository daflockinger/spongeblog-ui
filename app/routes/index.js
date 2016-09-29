import Ember from 'ember';
import RSVP from 'rsvp';


export default Ember.Route.extend({
  model(params) {
    return RSVP.hash({
    page : params.page,
    categories: this.get('store').findAll('category'),
    posts: this.get('store').query('post',{ page: params.page, limit: 5, sort: 'created', orderasc:false, postStatus: 'PUBLIC', noPost:false}),
    blog: this.get('store').findAll('blog')
  });
  }
});
