import Ember from 'ember';
import momentComputed from 'ember-moment/computeds/moment';
import format from 'ember-moment/computeds/format';
import locale from 'ember-moment/computeds/locale';
import fromNow from 'ember-moment/computeds/from-now';


export default Ember.Component.extend({
  created: Ember.computed('post', function() {
    return this.get('post').get('created');
  }),
  createdOnFormatted: format(locale(momentComputed('created'), 'moment.locale'), 'MMMM DD, YYYY'),
  createdFromNow: fromNow(momentComputed('created'), false),
  cleanTitle: Ember.computed('post',function(){
    return this.get('post').get('title').replace(/ /g,"_");
  }),
});
