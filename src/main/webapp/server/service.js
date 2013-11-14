var _ = require('lodash');
var async = require('async');
var mongoose = require('mongoose');
var AccountEntryType = require('./model').AccountEntryType;
var Account = mongoose.model('Account');
var AccountEntry = mongoose.model('AccountEntry');
var MealRecord = mongoose.model('MealRecord');

exports.splitMealRecord = function spiltMealRecord(mealRecord, cb) {
  Account.findOne({name: mealRecord.payer}, function (err, payer) {

    payer.debit(mealRecord.date, mealRecord.amount);
    payer.save(function (err) {

      var avgCost = mealRecord.amount / mealRecord.participants.length;
      Account.find().in('name', mealRecord.participants).exec(function (err, participants) {
        
        async.each(participants, function (participant, done) {
          participant.credit(mealRecord.date, avgCost);
          participant.save(function (err) { 
            done(); 
          });
        }, function (err) {
          cb(err);
        });
      });
    });
  });
};