var express = require('express'),
  namespace = require('express-namespace'),
  http = require('http'),
  partialResponse = require('express-partial-response'),
  path = require('path');

var mongoose = require('mongoose');
//mongoose.set('debug', true);
mongoose.connect('mongodb://localhost/fantuan');

var app = module.exports = express();
/**
 * Configuration
 */
app.set('port', process.env.PORT || 3000);
app.use(express.logger('dev'));
app.use(express.cookieParser());
app.use(express.cookieSession({secret: 'some secret'}));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(express.static(path.join(__dirname, '../public')));
app.use(partialResponse());
app.use(app.router);
app.use(express.errorHandler());

/**
 * Routes
 */
// serve index and view partials
var index = function(req, res){
  res.render('../index');
};
app.get('/', index);

// JSON API
app.get('/api/status', function (req, res) {
  res.json({
    status: 'OK'
  });
});
require('./api').addRoutes(app);

// redirect all others to the index (HTML5 history)
app.get('*', index);

/**
 * Start Server
 */
http.createServer(app).listen(app.get('port'), function () {
  console.log('Express server listening on port ' + app.get('port'));
});