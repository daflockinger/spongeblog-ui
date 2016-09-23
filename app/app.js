import Ember from 'ember';
import Resolver from './resolver';
import loadInitializers from 'ember-load-initializers';
import config from './config/environment';
//import DS from 'ember-data';

let App;

Ember.MODEL_FACTORY_INJECTIONS = true;

App = Ember.Application.extend({
  modulePrefix: config.modulePrefix,
  podModulePrefix: config.podModulePrefix,
  Resolver
});
/*
export default DS.RESTAdapter.extend({
  host: 'http://172.17.0.2:9000',
  namespace: 'api/v1'
});*/

loadInitializers(App, config.modulePrefix);

export default App;
