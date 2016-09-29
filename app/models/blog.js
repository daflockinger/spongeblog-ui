import DS from 'ember-data';
import attr from 'ember-data/attr';

export default DS.Model.extend({
    "name":attr('string'),
    "blogStatus":attr('string'),
  	"users": attr(),
  	"settings":attr()
});
