import Ember from 'ember';

export default Ember.Component.extend({
  store: Ember.inject.service(),
  userStatuses: ["ADMIN","AUTHOR","LOCKED_ADMIN","LOCKED_AUTHOR", "SUSPENDED"],
  actions: {
    insert(user) {
      let login = null;
      let password = null;
      let nickname = null;
      let email = null;
      let status = null;

      if(this.get('user.login') !== undefined){
        login = this.get('user.login');
      }
      if(this.get('user.password') !== undefined){
        password = this.get('user.password');
      }
      if(this.get('user.nickname') !== undefined){
        nickname = this.get('user.nickname');
      }
      if(this.get('user.email') !== undefined){
        email = this.get('user.email');
      }
      if(this.get('user.userStatus') !== undefined){
        status = this.get('user.userStatus');
      }

      var newUser = this.get('store').createRecord('User',{
        login : login,
        password : password,
        nickname : nickname,
        email : email,
        userStatus : status
      });

      newUser.save(); //persist new user
      this.refresh();
    },

    update(updatedUser) {

       this.get('store').findRecord('user',updatedUser.get('id'))
                       .then(function(user){
                         user.set('login',updatedUser.get('login'));
                         user.set('password',updatedUser.get('password'));
                         user.set('nickname',updatedUser.get('nickname'));
                         user.set('email',updatedUser.get('email'));
                         user.save();
                       });
      Materialize.toast('User updated.',2500);              
      this.refresh();
    },

    remove(user) {
      if(confirm('Really delete User?')){
        this.get('store').findRecord('user', this.get('user.id'), { backgroundReload: false })
        .then(function(post) {
          post.destroyRecord();
        });
        this.refresh();
      }
    },
  }
});
