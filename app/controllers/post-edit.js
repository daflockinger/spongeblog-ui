import Ember from 'ember';

export default Ember.Controller.extend({
  isModalShown: false,
  actions:{
    toggleModal() {
      this.set('model.post.content.content', $(".trumbowyg-editor").html());
      this.toggleProperty('isModalShown');
    },
  },
});
