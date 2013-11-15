var async = require('async');
var Q = require('q');
Q.longStackSupport = true;
var mongoose = require('mongoose');
var services = require('./service.js');
mongoose.connect('mongodb://localhost/fantuan');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  var AccountEntryType = require('./model').AccountEntryType;
  var Account = mongoose.model('Account');
  var AccountEntry = mongoose.model('AccountEntry');
  var MealRecord = mongoose.model('MealRecord');
  
  function cleanup() {
    return Q.all([Q.ninvoke(mongoose.connection.collections[Account.collection.name], 'drop'),
      Q.ninvoke(mongoose.connection.collections[MealRecord.collection.name], 'drop')]);
  }

  function insertData() {
    var account = new Account({name: "Oliver Zhou", password: "123", enabled: true, balance: 0, entries: []});
    var account2 = new Account({name: "Gary Chang", password: "123", enabled: true, balance: 0, entries: []});
    return Q.all([Q.ninvoke(account, 'save'), Q.ninvoke(account2, 'save')]).then(function () {
      var record = new MealRecord({amount: 200, restaurant: "汉堡王", date: Date.now(), payer: account.name, 
        participants: [account.name, account2.name]});
      return Q.ninvoke(record, 'save');
    }).then(function(record) {
      console.log(record);
      return services.splitMealRecord(record[0]);
    });
  }

  function disconnect() {
    return Q.ninvoke(mongoose, 'disconnect');
  }

  cleanup().then(insertData).then(disconnect).catch(console.log);

  // async.series([
  //   function(done) {
  //     mongoose.connection.collections[Account.collection.name].drop(function() {
  //       mongoose.connection.collections[MealRecord.collection.name].drop(function() {
  //         done();
  //       });
  //     });
  //   },
  //   function(done) {
  //     account.save(function (err, account) {
  //       var account2 = new Account({name: "Gary Chang", password: "123", enabled: true, balance: 0, entries: []});
  //       account2.save(function (err, account2) {
  //         var record = new MealRecord({amount: 200, restaurant: "汉堡王", date: Date.now(), payer: account.name, participants: [account.name, account2.name]});
  //         record.save(function (err, record) {
  //           console.log(record);

  //           console.log(services);
  //           services.splitMealRecord(record, function() {
  //             console.log("Fuck");
  //             done();
  //           });
  //         });
  //       });
  //     });
  //   },
  //   function(done) {
  //     mongoose.disconnect(function() {
  //       done();
  //     });
  //   }
  // ]);

});
