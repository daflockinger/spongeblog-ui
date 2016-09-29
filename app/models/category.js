import DS from 'ember-data';
import attr from 'ember-data/attr';


export default DS.Model.extend({
    "errorMessage":attr('string'),
    "name":attr('string'),
    "parentCategory":attr('string'),
    "childCategories":attr('string'),
    "rank":attr('number'),
    "sideCategory":attr('boolean')
});
