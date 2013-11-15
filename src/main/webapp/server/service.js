var Q = require('q');
Q.longStackSupport = true;
var mongoose = require('mongoose');
var AccountEntryType = require('./model').AccountEntryType;
var Account = mongoose.model('Account');
var AccountEntry = mongoose.model('AccountEntry');
var MealRecord = mongoose.model('MealRecord');

exports.splitMealRecord = function spiltMealRecord(mealRecord) {
  return Account.findOne({name: mealRecord.payer}).exec()
  .then(function (payer) {
    payer.debit(mealRecord.date, mealRecord.amount);
    return Q.ninvoke(payer, 'save');
  })
  .then(function() {
    return Account.find().in('name', mealRecord.participants).exec();
  })
  .then(function(participants) {
    var avgCost = mealRecord.amount / mealRecord.participants.length;
    var results = participants.map(function (participant) {
      participant.credit(mealRecord.date, avgCost);
      return Q.ninvoke(participant, 'save');
    });
    return Q.all(results);
  });
};
