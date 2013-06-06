'use strict';

var app = angular.module('Fantuan');

app.controller('MyCtrl', function ($scope, Authentication, $location, Account, Meal) {
    if (Authentication.current() == null) {
        $location.path("/login");
        return;
    }

    $scope.restaurantOptions = {
        data: [{"id":0,"text":"斗香园"},{"id":1,"text":"友加"},{"id":2,"text":"日本料理"},{"id":3,"text":"新旺"}],
        multiple: false,
        createSearchChoice: function(term) {
            var timestamp = new Date().getTime();
            return {id: timestamp, text: term, new: true};
        }
    };

    $scope.restaurantOption = "";
    $scope.noOfPages = 1;
    $scope.currentPage = 1;
    $scope.pageSize = 10;
    $scope.submitting = false;
    $scope.meal = { payer : Authentication.current() };
    $scope.enterNewMeal = false;

    //Fetch balance
    var getBalance = function () {
          Account.get({user: Authentication.current()}, function(data) {
              $scope.balance = data.balance;
          });
    };
    $scope.balance = getBalance();

    $scope.$watch("restaurantOption", function(newValue, oldValue) {
        if (newValue != null)
            $scope.meal.restaurant = newValue.text;
    });
    //Create new meal
    $scope.newMeal = function () {
        if (!$scope.enterNewMeal)
            $scope.enterNewMeal = true;
        else {
            $scope.submitting = true;
            Meal.save({}, $scope.meal, function() {
                getRecords();
                $scope.enterNewMeal = false;
                $scope.balance = getBalance();

                $scope.submitting = false;
            }, function() {
                $scope.submitting = false;
            });
        }
    }
    $scope.cancelMeal = function() {
        $scope.enterNewMeal = false;
    };

    // Fetch record by user
    var getPageCount = function() {
        Meal.count({user: Authentication.current()}, function(data) {
            $scope.noOfPages = Math.ceil(data.count / $scope.pageSize);
            if ($scope.noOfPages == 0)
                $scope.noOfPages = 1;
        });
    }
    getPageCount();

    var getRecords = function() {
        $scope.meals = Meal.query(
               {user: Authentication.current(),
                start : ($scope.currentPage - 1) * $scope.pageSize,
                pageSize : $scope.pageSize})
    }

    getRecords();
    $scope.$watch("currentPage", function(newValue, oldValue) {
        getRecords();
    });

    $scope.users = Account.query();
});

app.controller('TopCtrl', function (Account, $scope) {
    Account.query(function(data) {
        $scope.users = data;

        var chart_data = {
            negative : {
                negative : true,
                terms : []
            },
            positive : {
                negative : false,
                terms : []
            }
        };

        var negativeAccount = _.filter(data, function(item) { return item.balance < 0});
        var terms = _.map(negativeAccount, function(item) { return {term : item.name, count : item.balance * -1}});
        chart_data.negative.terms = terms;

        var positiveAccount = _.filter(data, function(item) { return item.balance >= 0});
        var postiveTerms = _.map(positiveAccount, function(item) { return {term : item.name, count : item.balance}});
        chart_data.positive.terms = postiveTerms;

        $scope.chart_data = chart_data;
    });
});


app.controller('AccountCtrl', function ($scope, $resource, AccountEntry) {
    $scope.noOfPages = 1;
    $scope.currentPage = 1;
    $scope.pageSize = 10;

    var getPageCount = function() {
        AccountEntry.count(function(total) {
            $scope.noOfPages = Math.ceil(total.count / $scope.pageSize);
            if ($scope.noOfPages == 0)
                $scope.noOfPages = 1;
        });
    }
    getPageCount();

    var getRecords = function() {
        $scope.entries = AccountEntry.query(
            {start : ($scope.currentPage - 1) * $scope.pageSize, pageSize : $scope.pageSize});
    }
    getRecords();
    $scope.$watch("currentPage", function(newValue, oldValue) {
        getRecords();
    });
});
