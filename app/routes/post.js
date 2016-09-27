import Ember from 'ember';
import RSVP from 'rsvp';

export default Ember.Route.extend({
  model(params) {
    return RSVP.hash({
      post: this.get('store').findRecord('post', params.id),
      categories: this.get('store').findAll('category'),
      blog: this.get('store').findAll('blog')
    });
  }
});
