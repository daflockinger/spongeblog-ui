export function initialize(instance) {
  const applicationRoute = instance.lookup('router:main');
  const session          = instance.lookup('service:session');
  session.on('authenticationSucceeded', function() {
    applicationRoute.transitionTo('admin');
  });
  session.on('invalidationSucceeded', function() {
    applicationRoute.transitionTo('toIndex');
  });
}

export default {
  initialize,
  name:  'session-events',
  after: 'ember-simple-auth'
};
