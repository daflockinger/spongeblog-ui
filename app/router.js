import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('index', { path: '/' });
  this.route('page', { path: '/:name'});
  this.route('category', { path: '/:name/:page'});
  this.route('post', { path: '/post/:id'});
});

export default Router;
