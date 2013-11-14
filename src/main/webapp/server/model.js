var _ = require('lodash');
var async = require('async');
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var Type = {
  DEBIT: "充值",
  CREDIT: "消费"
};
exports.AccountEntryType = Type;

var accountEntrySchema = new Schema({
  type: String,
  amount: Number,
  date: Date,
  description: String
});
var AccountEntry = mongoose.model('AccountEntry', accountEntrySchema);

var accountSchema = new Schema({
  name:  String,
  password: String,
  enabled:  {type: Boolean, default: true},
  balance: Number,
  entries: [accountEntrySchema],
});
accountSchema.methods.debit = function (date, amount, description) {
  accountEntry = new AccountEntry({date: date, type: Type.DEBIT, amount: amount, description: description});
  this.entries.push(accountEntry);
  this.balance = this.balance + amount;
};
accountSchema.methods.credit = function (date, amount, description) {
  accountEntry = new AccountEntry({date: date, type: Type.CREDIT, amount: amount, description: description});
  this.entries.push(accountEntry);
  this.balance = this.balance - amount;
};
var Account = mongoose.model('Account', accountSchema);

var mealRecordSchema = new Schema({
  amount: Number,
  restaurant: String,
  date: Date,
  payer: String,
  participants: [String]
});
mongoose.model('MealRecord', mealRecordSchema);
