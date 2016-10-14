/* jshint node: true */

module.exports = function(environment) {
  var ENV = {
    modulePrefix: 'spongeblog-ui',
    environment: environment,
    rootURL: '/',
    locationType: 'auto',
  /*  disqus: {   TODO activate someday
      shortname: 'add-my-forum-name-here'
    },*/
    materializeDefaults: {
      modalIsFooterFixed:  false,
      buttonIconPosition:  'left',
      loaderSize:          'big',
      loaderMode:          'indeterminate',
      modalContainerId:    'materialize-modal-root-element',
      dropdownInDuration:  300,
      dropdownOutDuration: 300
    },
    EmberENV: {
      FEATURES: {
        // Here you can enable experimental features on an ember canary build
        // e.g. 'with-controller': true
      }
    },
    APP: {
      // Here you can pass flags/options to your application instance
      // when it is created
    }
  };

ENV.contentSecurityPolicy = {
  'default-src': "'none'",
  'script-src': "'self' 'unsafe-inline'",
  'style-src': "'self' 'unsafe-inline' https://fonts.googleapis.com",
  'font-src': "'self' fonts.gstatic.com",
  'connect-src': "'self'",
  'img-src': "'self' data:",
  'media-src': "'self'"
}

  if (environment === 'development') {
    // ENV.APP.LOG_RESOLVER = true;
    // ENV.APP.LOG_ACTIVE_GENERATION = true;
    // ENV.APP.LOG_TRANSITIONS = true;
    // ENV.APP.LOG_TRANSITIONS_INTERNAL = true;
    // ENV.APP.LOG_VIEW_LOOKUPS = true;
  }

  if (environment === 'test') {
    // Testem prefers this...
    ENV.locationType = 'none';

    // keep test console output quieter
    ENV.APP.LOG_ACTIVE_GENERATION = false;
    ENV.APP.LOG_VIEW_LOOKUPS = false;

    ENV.APP.rootElement = '#ember-testing';
  }

  if (environment === 'production') {

  }

  return ENV;
};
