var app = angular.module('Fantuan', 
  ['ngRoute', 'chieffancypants.loadingBar', 
   'ui.bootstrap', 'restangular', 'ngTable', 'nvd3ChartDirectives', 'localytics.directives']);

app.config(function(RestangularProvider, $httpProvider) {

  RestangularProvider.setBaseUrl("../api/");
  $httpProvider.interceptors.push(['$q', '$rootScope', '$location', function($q, $rootScope, $location) {
    return {
      responseError: function(response) {
        //console.log(response);
        $rootScope.$broadcast('httpError', response);
        return $q.reject(response);
      }
    };
  }]);
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
      $rootScope.loginErrorMessage = "需要登录系统访问！";
      $location.path('/login');
    } else if (response.status == 403) {
      $rootScope.loginErrorMessage = "没有足够的权限！请更换拥有更高权限的用户登录";
      $location.path('/login');
    }
  });
});