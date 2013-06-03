'use strict';

var app = angular.module('Fantuan');

app.controller('MyCtrl', function ($http, $scope, Authentication, $location) {
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
        $http.get('../rest/account/' + Authentication.current()).success(function (data, status, headers, config) {
            $scope.balance = data.balance;
        });
    };
    $scope.balance = getBalance();

    $scope.$watch("restaurantOption", function(newValue, oldValue) {
        $scope.meal.restaurant = newValue.text;
    });
    //Create new meal
    $scope.newMeal = function () {
        if (!$scope.enterNewMeal)
            $scope.enterNewMeal = true;
        else {
            $scope.submitting = true;
            $http.post('../rest/meal', $scope.meal).success(function (data, status, headers, config) {
                getRecords();
                $scope.enterNewMeal = false;
                $scope.balance = getBalance();

                $scope.submitting = false;
            }).error(function(data, status, headers, config) {
                $scope.submitting = false;
            });
        }
    }
    $scope.cancelMeal = function() {
        $scope.enterNewMeal = false;
    };

    // Fetch record by user
    var getPageCount = function() {
        $http.get('../rest/meal/user/count',
            {params: {user: Authentication.current()}})
            .success(function (data, status, headers, config) {
                $scope.noOfPages = Math.ceil(data / $scope.pageSize);
                if ($scope.noOfPages == 0)
                    $scope.noOfPages = 1;
            });
    }
    getPageCount();

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


app.controller('AccountCtrl', function ($scope, $http, Authentication) {
    $scope.noOfPages = 1;
    $scope.currentPage = 1;
    $scope.pageSize = 10;

    var getPageCount = function() {
        $http.get('../rest/account/' + Authentication.current() + "/entry/count")
            .success(function (data, status, headers, config) {
                $scope.noOfPages = Math.ceil(data / $scope.pageSize);
                if ($scope.noOfPages == 0)
                    $scope.noOfPages = 1;
            });
    }
    getPageCount();

    var getRecords = function() {
        $http.get('../rest/account/' + Authentication.current() +"/entry",
            {params: {start : ($scope.currentPage - 1) * $scope.pageSize, pageSize : $scope.pageSize}})
            .success(function (data, status, headers, config) {
                $scope.entries = data;
            });
    }
    getRecords();
    $scope.$watch("currentPage", function(newValue, oldValue) {
        getRecords();
    });
});
