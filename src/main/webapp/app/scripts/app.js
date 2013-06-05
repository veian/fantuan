'use strict';

var app = angular.module('Fantuan', ['$strap.directives', 'ui', 'ngCookies', 'ui.bootstrap', 'dangle']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
       when('/my', {
            controller: 'MyCtrl',
            templateUrl:'views/my.html'
        }).when('/account', {
            controller: 'AccountCtrl',
            templateUrl:'views/account.html'
        }).when('/top', {
            controller: 'TopCtrl',
            templateUrl:'views/top.html'
        }).otherwise({redirectTo:'/my'});
}]);
