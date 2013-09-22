var app = angular.module('Fantuan', 
  ['$strap.directives', 'ui', 'ui.bootstrap', 'restangular', 'ngTable', 'nvd3ChartDirectives'], 
  function(RestangularProvider, $httpProvider) {

  RestangularProvider.setBaseUrl("../api/");
  $httpProvider.interceptors.push(function($q, $rootScope, $location) {
    return {
      'responseError': function(response) {
        console.log(response);
        $rootScope.$broadcast('httpError', response);
        return $q.reject(response);
      }
    };
  });
});

app.factory("CheckAuth", function(Authentication, $q) {
  var deferred = $q.defer();
  Authentication.getCurrentUserFromServer().then(function() {
        deferred.resolve();
      }, function() {
        deferred.reject();
      });
  return deferred.promise;
});

app.config(function($routeProvider) {
  $routeProvider.
    when('/my', {
      controller: 'MyCtrl',
      templateUrl: 'views/my.html',
      resolve: {
        auth: 'CheckAuth'
      }
    }).when('/account', {
      controller: 'AccountCtrl',
      templateUrl: 'views/account.html',
      resolve: {
        auth: 'CheckAuth'
      }
    }).when('/top', {
      controller: 'TopCtrl',
      templateUrl: 'views/top.html'
    }).otherwise({
      redirectTo: '/my'
    });
});

app.run(function($rootScope, $location, Authentication) {
  $rootScope.$on('httpError', function(event, response) {
    if (response.status == 401) {
      Authentication.clear();
      $location.path('/login');
    }
  });
});