"use strict";

var module = angular.module("Fantuan");
// Login
var Controller = function($rootScope, $scope, $location, Authentication) {

    if (Authentication.current()) {
      $location.path("/my");
    }

    $scope.login = function () {
      Authentication.login($scope.username, $scope.password).then(function(success) {
        //Notifications.clearAll();

        if (success) {
          $rootScope.$broadcast("login.success");
          //Notifications.add({ type: "success", status: "Login", message: "Login successful", duration: 10000 });

          $location.path("/my");
        } else {
          //Notifications.addError({ status: "Login Failed", message: "Username / password are incorrect" });
        }
      });
    }
};

var RouteConfig = function($routeProvider) {
$routeProvider.when("/login", {
  templateUrl: "views/login.html",
  controller: Controller
});
};

module
.config(RouteConfig)
.controller("LoginController", Controller);

// Logout
var LogoutController = function($scope, $location, Authentication) {
    $scope.loginUser = Authentication.current();
    $scope.logout = function() {
        Authentication.logout().then(function(success) {
            //Notifications.clearAll();
            //Notifications.addMessage({ status: "Logout", message: "You have been logged out" });
            $scope.loginUser = null;
            Authentication.auth.current = null;
            $location.path("/login");
        });
    };

    $scope.$on("login.success", function () {
        $scope.loginUser = Authentication.current();
    });
};

module
    .controller("LogoutController", LogoutController)
