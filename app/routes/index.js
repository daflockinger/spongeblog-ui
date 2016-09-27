import Ember from 'ember';
import RSVP from 'rsvp';


export default Ember.Route.extend({
  model() {
    return RSVP.hash({
    categories: this.get('store').findAll('category'),
    posts: this.get('store').query('post',{ page: 0, limit: 5, sort: 'created', orderasc:false, postStatus: 'PUBLIC'}),
    blog: this.get('store').findAll('blog')
  });
  }
});
