import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return this.get('store').findAll('category');
  },
  actions: {
    insert(newCategory) {
      let name = null;
      let rank = null;
    //  let parentCategory = null;
      let sideCategory = null;

      if(newCategory.name !== undefined){
        name = newCategory.name;
      }
      if(newCategory.rank !== undefined){
        rank = newCategory.rank;
      }
    /*  if(newCategory.parentCategory !== undefined){
        parentCategory = newCategory.parentCategory;
      } */
      if(newCategory.sideCategory !== undefined){
        sideCategory = newCategory.sideCategory;
      }

      var latestCat = this.get('store').createRecord('category',{
        name : name,
      //  parentCategory : parentCategory,
      //  childCategories : [],
        rank : rank,
        sideCategory : sideCategory
      });

      latestCat.save();
      this.refresh();
    },

    update(updatedCategory) {

       this.get('store').findRecord('category',updatedCategory.get('id'))
                       .then(function(category){
                         category.set('name',updatedCategory.get('name'));
                         category.set('rank',updatedCategory.get('rank'));
                         category.set('sideCategory',updatedCategory.get('sideCategory'));
                         //user.set('parentCategory',updatedCategory.get('parentCategory'));
                         //category.set('childCategories',[]);
                         category.save();
                       });
      this.refresh();
    },

    remove(category) {
      if(confirm('Really delete Category?')){
        this.get('store').findRecord('category', category.get('id'), { backgroundReload: false })
        .then(function(post) {
          post.destroyRecord();
        });
        this.refresh();
      }
    },
  }
});
