import Ember from 'ember';

export default Ember.Route.extend({
  redirect: function(params) {
        this.transitionTo('category',params.name,'0');
    },
});
