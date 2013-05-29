'use strict';

var app = angular.module('AngularJSApp', ['$strap.directives', 'ui', 'ngCookies', 'ui.bootstrap']);

app.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/', {
            controller: 'MainCtrl',
            templateUrl:'views/main.html'
        }).when('/my', {
            controller: 'MyCtrl',
            templateUrl:'views/my.html'
        }).when('/top', {
            controller: 'TopCtrl',
            templateUrl:'views/top.html'
        }).otherwise({redirectTo:'/'});
}]);
