import Ember from 'ember';
import RSVP from 'rsvp';
import momentComputed from 'ember-moment/computeds/moment';
import format from 'ember-moment/computeds/format';
import locale from 'ember-moment/computeds/locale';
import fromNow from 'ember-moment/computeds/from-now';


export default Ember.Route.extend({
  model(params) {
    return RSVP.hash({
      post: this.get('store').findRecord('post', params.id),
      categories: this.get('store').findAll('category'),
      blog: this.get('store').findAll('blog'),
      createdOnFormatted: format(locale(momentComputed('post.created'), 'moment.locale'), 'MMMM DD, YYYY'),
      createdFromNow: fromNow(momentComputed('post.created'), false),
    });
  }
});
