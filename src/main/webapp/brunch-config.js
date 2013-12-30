var endWith, startsWith, sysPath;

sysPath = require('path');

startsWith = function(string, substring) {
  return string.lastIndexOf(substring, 0) === 0;
};

endWith = function(string, suffix) {
  return string.indexOf(suffix, string.length - suffix.length) !== -1;
};

exports.config = {
  modules: {
    definition: false,
    wrapper: function(path, data) {
      if (!endWith(path, 'joint.nojquerynobackbone.js') && !endWith(path, 'joint.layout.DirectedGraph.js')) {
        return "(function() {\n  " + data + "\n}());\n\n";
      } else {
        return "" + data;
      }
    }
  },
  conventions: {
    assets: /^app\/assets\//,
    ignored: function(path) {
      return startsWith(sysPath.basename(path), '_');
    }
  },
  files: {
    javascripts: {
      joinTo: {
        'js/app.js': /^(app)/,
        'js/vendor.js': /^(bower_components|vendor)/,
        'test/js/test.js': /^test(\/|\\)(?!bower_components)/,
        'test/js/test-bower_components.js': /^test(\/|\\)(?=bower_components)/
      },
      order: {
        before: ['vendor/jquery.js', 'vendor/lodash.js', 'vendor/joint/backbone.js', 'vendor/joint/joint.nojquerynobackbone.js', 'vendor/joint/joint.layout.DirectedGraph.js', 'bower_components/angular/angular.js', 'vendor/angular-nvd3/d3.js']
      }
    },
    stylesheets: {
      joinTo: {
        'css/app.css': /^(app)/,
        'css/vendor.css': /^(bower_components|vendor)/
      }
    }
  },
  overrides: {
    production: {
      sourceMaps: true
    }
  },
  server: {
    path: 'nodemon-wrapper.js'
  }
};
