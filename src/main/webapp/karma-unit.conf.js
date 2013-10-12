var shared = require('./karma-shared.conf');

module.exports = function(config) {
  shared(config);

  config.files = shared.files.concat([
    //extra testing code
    'test/angular-mocks.js',

    //test files
    // './test/*.spec.js',
    './test/*.spec.coffee'
  ]);
};
