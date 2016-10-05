import Ember from 'ember';
import Base from 'ember-simple-auth/authenticators/base';
import RSVP from 'rsvp';
const { RSVP: { Promise }, isEmpty, run, $: jQuery, assign: emberAssign, merge } = Ember;
const assign = emberAssign || merge;


export default Base.extend({
  //should propably check session data if it's kinda valid or just not
  restore(data) {
    console.log('I think my session is dead, but who cares:' + data);
    return RSVP.resolve();
  },

  authenticate(user, password) {
    return new Promise((resolve, reject) => {
    const data = {user: user, password: password};

    return this.makeRequest(data).then(
        (response) => {
          console.log('successfully authenticated: ' + response);
          run(null, resolve, response);
        },
        (xhr) => {
          console.log('wrong credentials ' + xhr.responseJSON.errorMessage);
          run(null, reject,  xhr.responseJSON.errorMessage);
        }
      );
    });
  },

  //stolen, changed, mine is better and it works
  makeRequest(data) {
    return jQuery.ajax({
  method: "POST",
  url: "http://localhost:9000/api/v1/login",
  data: JSON.stringify(data),
  contentType: "application/json"
});
  },

  //Don't need to change this! leave it as it is and done...
  invalidate(data) {
    return RSVP.resolve();
  }
});
