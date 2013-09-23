"use strict";

var module = angular.module("Fantuan");
// Login
var Controller = function($rootScope, $scope, $location, Authentication, Restangular) {

    $scope.login = function() {
      Authentication.login($scope.username, $scope.password).then(function(success) {
        if (success) {
          $rootScope.$broadcast("login.success");
          $location.path("/my");
        } else {
          //Notifications.addError({ status: "Login Failed", message: "Username / password are incorrect" });
        }
      });
    };

    $scope.register = function() {
      var newAccount = {
        name: $scope.username,
        password: $scope.password,
        balance: 0
      };

      Restangular.all("accounts").post(newAccount).then(function(success) {
        if (success) {
          $scope.login();
        }
      }, function() {});
    };

  };

var RouteConfig = function($routeProvider) {
    $routeProvider.when("/login", {
      templateUrl: "views/login.html",
      controller: Controller
    });
  };

module.config(RouteConfig).controller("LoginController", Controller);

// Logout
var LogoutController = function($scope, $location, Authentication) {
    $scope.loginUser = Authentication.current();
    $scope.logout = function() {
      Authentication.logout().then(function(success) {
        $scope.loginUser = null;
        $location.path("/login");
      });
    };

    $scope.$on("login.success", function() {
      $scope.loginUser = Authentication.current();
    });
  };

module.controller("LogoutController", LogoutController);