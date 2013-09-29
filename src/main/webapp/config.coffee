exports.config =
  # https://github.com/brunch/brunch/blob/master/docs/config.md for docs.    
  files:
    javascripts:
      joinTo:
        'js/app.js': /^(app)/
        'js/vendor.js': /^(bower_components|vendor)/
        'test/js/test.js': /^test(\/|\\)(?!bower_components)/
        'test/js/test-bower_components.js': /^test(\/|\\)(?=bower_components)/
      order:
        before: [
          'bower_components/jquery/jquery.js'
          'bower_components/angular/angular.js',
          'bower_components/nvd3/lib/d3.v3.js',
          'bower_components/lodash/lodash.js'
        ]

    stylesheets:
      joinTo:
        'css/app.css' : /^(app|bower_components|vendor)/

    templates:
      joinTo: 'app.js'

  modules:
    definition: false
    wrapper: false

  conventions:
    assets: /^app\/assets\//
