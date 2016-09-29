import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('toIndex', { path: '/' });
  this.route('index', { path: '/:page' });
  this.route('page', { path: '/category/:name'});
  this.route('category', { path: '/category/:name/:page'});
  this.route('post', { path: '/post/:id'});

});

export default Router;
