import Ember from 'ember';

export default Ember.Route.extend({
  redirect: function(params) {
        this.transitionTo('index','0');
    },
});
