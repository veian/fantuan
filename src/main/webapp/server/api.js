var _ = require('lodash');
var async = require('async');
var mongoose = require('mongoose');
var AccountEntryType = require('./model').AccountEntryType;
var Account = mongoose.model('Account');
var AccountEntry = mongoose.model('AccountEntry');
var MealRecord = mongoose.model('MealRecord');
var services = require('./service.js');

exports.addRoutes = function (app) {

  app.namespace('/api/accounts', function() {

    app.get('/', function (req, res) {
      Account.find(function(err, accounts) {
        res.json(accounts);
      });
    });

    app.get('/count', function (req, res) {
      Account.count(function(err, count) {
        res.json({count: count});
      });
    });

    app.get('/:user', function (req, res) {
      Account.findOne({name: req.params.user}, function (err, user) {
        res.json(user);
      });
    });

    app.get('/:user/entry', function (req, res) {
      Account.findOne({name: req.params.user}, function (err, user) {
        res.json(user.entries);
      });
    });

    app.get('/:user/entry/count', function (req, res) {
      Account.findOne({name: req.params.user}, function (err, user) {
        res.json({count: user.entries.length});
      });
    });

    app.post('/', function (req, res) {
      var account = req.body;
      account = new Account(account);
      account.save(function (err, account) {
        return err ? res.send(500) : res.send(200);     
      });
    });

  });

  app.get('/api/auth/user', function(req, res) {
    //console.log(req.session);
    res.send(req.session.currentUser || "anonymousUser");
  });

  app.post('/j_spring_security_check', function (req, res) {
    Account.findOne({name: req.body.j_username, password: req.body.j_password}, function (err, user) {
      if (user) {
        req.session.currentUser = req.body.j_username;
        res.send(200);
      } else
        res.send(404);
    });
  });

  app.post('/j_spring_security_logout', function (req, res) {
    req.session = null;
    res.send(200);
  });

  app.namespace('/api/meals', function() {

    app.get('/', function (req, res) {
      var user = req.query.user;
      if (!user) return res.send(404);
      var start = req.query.start || 0;
      var pageSize = req.query.pageSize || 10;
      MealRecord.find().or([{payer: user}, {participants: user}]).skip(start).limit(pageSize).exec(function (err, meals) {
        res.json(meals);
      });
    });

    app.get('/count', function (req, res) {
      var user = req.query.user;
      MealRecord.count().or([{payer: user}, {participants: user}]).exec(function (err, count) {
        res.json({count: count});
      });
    });

    app.post('/', function (req, res) {
      var record = req.body;
      console.log(record);
      record = new MealRecord(record);
      record.save(function (err, record) {
        services.splitMealRecord(record, function (err) {
          return err ? res.send(500) : res.send(200); 
        });  
      });
    });

  });

};
