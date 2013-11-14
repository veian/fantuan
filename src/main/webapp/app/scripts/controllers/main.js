/*global angular, _*/
'use strict';

var app = angular.module('Fantuan');

app.controller('MyCtrl', function($scope, Authentication, $location, Restangular, ngTableParams) {
  $scope.restaurantOptions = {
    data: [{
      "id": 0,
      "text": "斗香园"
    }, {
      "id": 1,
      "text": "友加"
    }, {
      "id": 2,
      "text": "日本料理"
    }, {
      "id": 3,
      "text": "新旺"
    }],
    multiple: false,
    createSearchChoice: function(term) {
      var timestamp = new Date().getTime();
      return {
        id: timestamp,
        text: term,
        new: true
      };
    }
  };

  $scope.restaurantOption = "";
  $scope.submitting = false;
  $scope.meal = {
    payer: Authentication.current()
  };
  $scope.enterNewMeal = false;

  $scope.tableParams = new ngTableParams({
    page: 1,
    count: 5,
    counts: []
  });

  //Fetch balance
  var getBalance = function() {
      Restangular.one("accounts", Authentication.current()).get().then(function(data) {
        $scope.balance = data.balance;
      });
    };
  $scope.balance = getBalance();

  $scope.$watch("restaurantOption", function(newValue, oldValue) {
    if (newValue) {
      $scope.meal.restaurant = newValue.text;
    }
  });
  //Create new meal
  $scope.newMeal = function() {
    if (!$scope.enterNewMeal) {
      $scope.enterNewMeal = true;
    } else {
      $scope.submitting = true;
      Restangular.all("meals").post($scope.meal).then(function() {
        getRecords($scope.tableParams);
        $scope.enterNewMeal = false;
        $scope.balance = getBalance();

        $scope.submitting = false;
      }, function() {
        $scope.submitting = false;
      });
    }
  };
  $scope.cancelMeal = function() {
    $scope.enterNewMeal = false;
  };

  // Fetch record by user
  var getPageCount = function() {
      Restangular.all("meals").customGET("count", {
        user: Authentication.current()
      }).then(function(data) {
        $scope.tableParams.total = data.count;
      });
    };
  getPageCount();

  var getRecords = function(param) {
      Restangular.all("meals").getList({
        user: Authentication.current(),
        start: (param.page - 1) * param.count,
        pageSize: param.count
      }).then(function(meals) {
        $scope.meals = meals;
      });
    };

  getRecords($scope.tableParams);

  Restangular.all("accounts").getList().then(function(users) {
    $scope.users = users;
  });

  $scope.$watch("tableParams", function(param) {
    getRecords(param);
  });
});

app.controller('TopCtrl', function($scope, Restangular, ngTableParams) {
  $scope.tableParams = new ngTableParams({
    page: 1,
    count: 5,
    counts: []
  });

  var getAccountCount = function() {
      Restangular.one("accounts").customGET("count").then(function(total) {
        $scope.tableParams.total = total.count;
      });
    };
  getAccountCount();

  var getRecords = function(param) {
      Restangular.one("accounts").customGETLIST("", {
        start: (param.page - 1) * param.count,
        pageSize: param.count
      }).then(function(users) {
        $scope.users = users;
      });
    };

  getRecords($scope.tableParams);

  var getChartData = function() {
      Restangular.one("accounts").customGETLIST("", {
        start: -1,
        pageSize: -1
      }).then(function(data) {
        $scope.topChartData = [{
            key: "Data",
            values: _.map(data, function(item) {
              return [item.name, item.balance];
            })
          }
        ];
      });
    };

  getChartData();

  $scope.$watch("tableParams", function(param) {
    getRecords(param);
  });
});


app.controller('AccountCtrl', function($scope, Restangular, Authentication, ngTableParams) {
  $scope.tableParams = new ngTableParams({
    page: 1,
    count: 5,
    counts: []
  });

  var getPageCount = function() {
      Restangular.one("accounts", Authentication.current()).customGET("entry/count").then(function(total) {
        $scope.tableParams.total = total.count;
      });
    };
  getPageCount();

  var getRecords = function(param) {
      Restangular.one("accounts", Authentication.current()).customGETLIST("entry", {
        start: (param.page - 1) * param.count,
        pageSize: param.count
      }).then(function(entries) {
        $scope.entries = entries;
      });
    };
  getRecords($scope.tableParams);

  $scope.$watch("tableParams", function(param) {
    getRecords(param);
  });
});