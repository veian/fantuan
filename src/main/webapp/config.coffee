sysPath = require 'path'

# A simple helper that checks if string starts with substring.
startsWith = (string, substring) ->
  string.lastIndexOf(substring, 0) is 0

exports.config =
  # https://github.com/brunch/brunch/blob/master/docs/config.md for docs.
  modules:
    definition: false
    wrapper: false

  conventions:
    assets: /^app\/assets\//
    ignored: (path) ->
      startsWith sysPath.basename(path), '_'

  files:
    javascripts:
      joinTo:
        'js/app.js': /^(app)/
        'js/vendor.js': /^(bower_components|vendor)/
        'test/js/test.js': /^test(\/|\\)(?!bower_components)/
        'test/js/test-bower_components.js': /^test(\/|\\)(?=bower_components)/
      order:
        before: [
          'vendor/pace/pace.coffee'
          'bower_components/lodash/lodash.js'
          'bower_components/jquery/jquery.js'
          'bower_components/angular/angular.js'
          'bower_components/nvd3/lib/d3.v3.js'
        ]

    stylesheets:
      joinTo:
        'css/app.css': /^(app)/
        'css/vendor.css': /^(bower_components|vendor)/

    templates:
      joinTo: 'app.js'

