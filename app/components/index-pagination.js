import Ember from 'ember';

export default Ember.Component.extend({
  hasNextPage: Ember.computed('page', function() {
    return Number(this.get('page')) > 0 ;
  }),
  nextPage: Ember.computed('page', function() {
    return Number(this.get('page')) - 1 ;
  }),
  previousPage: Ember.computed('page', function() {
    return Number(this.get('page')) + 1 ;
  })
});
