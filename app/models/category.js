import DS from 'ember-data';
import attr from 'ember-data/attr';


export default DS.Model.extend({
    "errorMessage":attr(),
    "name":attr(),
    "parentCategory":attr(),
    "childCategories":attr(),
    "rank":attr()
});
