import DS from 'ember-data';
import attr from 'ember-data/attr';

export default DS.Model.extend({
  "errorMessage":attr('string'),
  "user":attr('string'),
  "created":attr('date'),
  "modified":attr('date'),
  "title":attr('string'),
  "content":attr('string'),
  "postStatus":attr('string'),
  "keywords":attr(),
  "category":attr('string')
});
