'use strict';

var app = angular.module('AngularJSApp');

app.controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      '我的饭团',
      '各类排行榜'
    ];
  });

app.controller('MyCtrl', function ($http, $scope, Authentication, $location) {
    if (Authentication.current() == null) {
        $location.path("/login");
        return;
    }

    $scope.noOfPages = 10;
    $scope.currentPage = 1;
    $scope.pageSize = 5;

    $scope.meal = {};
    $scope.enterNewMeal = false;
    var getBalance = function () {
        $http.get('../rest/account/' + Authentication.current()).success(function (data, status, headers, config) {
            $scope.balance = data.balance;
        });
    };
    $scope.balance = getBalance();

    $scope.newMeal = function () {
        if (!$scope.enterNewMeal)
            $scope.enterNewMeal = true;
        else {
            $http.post('../rest/meal', $scope.meal).success(function (data, status, headers, config) {
                getRecords();
                $scope.enterNewMeal = false;
                $scope.balance = getBalance();
            });
        }
    }
    $scope.cancelMeal = function() {
        $scope.enterNewMeal = false;
    };

    var getRecords = function() {
        $http.get('../rest/meal/user',
            {params: {user: Authentication.current(), start : ($scope.currentPage - 1) * $scope.pageSize, pageSize : $scope.pageSize}})
            .success(function (data, status, headers, config) {
            $scope.meals = data;
        });
    }
    getRecords();
    $scope.$watch("currentPage", function(newValue, oldValue) {
        getRecords();
    });

    $http.get('../rest/account').success(function (data, status, headers, config) {
        $scope.users = data;
    });
});

app.controller('TopCtrl', function ($http, $scope) {
    $http.get('../rest/account').success(function (data, status, headers, config) {
        $scope.users = data;
    });
});
