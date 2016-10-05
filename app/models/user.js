import DS from 'ember-data';
import attr from 'ember-data/attr';

export default DS.Model.extend({
  "login":attr('string'),
  "password":attr('string'),
  "nickname":attr('string'),
  "email":attr('string'),
  "registered":attr('date',{
    defaultValue() { return new Date(); }
  }),
  "userStatus":attr('string')
});
