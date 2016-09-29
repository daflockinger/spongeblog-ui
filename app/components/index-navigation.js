import Ember from 'ember';

export default Ember.Component.extend({
  sortedBy: ['rank'],
  sortedCategories: Ember.computed.sort('categories' , 'sortedBy')
});
