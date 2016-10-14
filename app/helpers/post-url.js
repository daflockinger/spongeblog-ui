import Ember from 'ember';

export function postUrl(params/*, hash*/) {
  return params[0].replace(/ /g,"_");
}

export default Ember.Helper.helper(postUrl);
