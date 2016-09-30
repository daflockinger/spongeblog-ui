import Ember from 'ember';

export default Ember.Route.extend({
  redirect: function(params) {
        this.transitionTo('keyword',params.keyword,'0');
    },
});
