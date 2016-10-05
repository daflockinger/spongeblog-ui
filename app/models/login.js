import DS from 'ember-data';

export default DS.Model.extend({
  "user":attr('string'),
  "password":attr('string'),
});
