var shared = function(config) {
  config.set({
    basePath: './',
    frameworks: ['jasmine'],
    reporters: ['progress', 'growl'],
    browsers: ['Chrome'],
    autoWatch: true,

    // these are default values anyway
    singleRun: false,
    colors: true,

    preprocessors: {
      '**/*.coffee': ['coffee']
    },

    coffeePreprocessor: {
      options: {
        sourceMap: true
      }
    },
  });
};

shared.files = [
  //3rd Party Code
  'public/js/vendor.js',

  //Test-Specific Code
  //'public/js/app.js'
  'app/scripts/**/*.js'
];

module.exports = shared;
