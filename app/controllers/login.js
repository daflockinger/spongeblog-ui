import Ember from 'ember';

export default Ember.Controller.extend({
  session: Ember.inject.service('session'),
  actions: {
    authenticate(){
     this.get('session').authenticate('authenticator:spongeauth', this.get('user'), this.get('password'))
     .catch((reason) => {
       this.set('errorMessage', reason);
     });
    }
  }
});
