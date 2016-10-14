import Ember from 'ember';
import RSVP from 'rsvp';

export default Ember.Route.extend({

  model(params) {
    return RSVP.hash({
      showWithStatus: "PUBLIC",
      postStatuses: ["ALL","PUBLIC", "PRIVATE", "MAINTENANCE", "DELETED"],
      postStore: this.get('store'),
      posts: Ember.computed('showWithStatus',function(){
          if(this.showWithStatus == "ALL"){
            return this.postStore.query('post', { /*page: params.page,*/ sort: 'created',orderasc:false});
          } else {
            return this.postStore.query('post', { /*page: params.page,*/ sort: 'created',orderasc:false,postStatus:this.showWithStatus});
          }
      }),
    });
  },
  actions:{
    remove(id){
      if(confirm("Really delete post " + id + "?")){
        this.get('store').findRecord('post', id, { backgroundReload: false })
        .then(function(post) {
          post.destroyRecord();
        });
        this.refresh();
      }
      console.log('remove ' + id);
    },
  },
});
