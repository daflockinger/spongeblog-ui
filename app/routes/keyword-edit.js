import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return this.get('store').findAll('keyword');
  },
  actions: {
    insert(newKeyword) {
      let name = null;
      let popularity = 0;

      if(newKeyword.name !== undefined){
        name = newKeyword.name;
      }

      var latestKeyword = this.get('store').createRecord('keyword',{
        name : name,
        popularity : popularity
      });

      latestKeyword.save();
      this.refresh();
    },

    remove(keyword) {
      if(confirm('Really delete Keyword?')){
        this.get('store').findRecord('keyword', keyword.get('id'), { backgroundReload: false })
        .then(function(post) {
          post.destroyRecord();
        });
        this.refresh();
      }
    },
  }
});
