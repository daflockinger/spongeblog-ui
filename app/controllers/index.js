import Ember from 'ember';

export default Ember.Controller.extend({
  sortedBy: ['rank'],
  sortedCategories: Ember.computed.sort('model' , 'sortedBy')
});
