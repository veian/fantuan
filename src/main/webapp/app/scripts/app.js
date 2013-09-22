var app = angular.module('Fantuan', 
  ['$strap.directives', 'ui', 'ui.bootstrap', 'restangular', 'ngTable', 'nvd3ChartDirectives'], 
  function($routeProvider, RestangularProvider, $httpProvider) {
  $routeProvider.
  when('/my', {
    controller: 'MyCtrl',
    templateUrl: 'views/my.html',
    resolve: {
      auth: function(Authentication, $q) {
        var deferred = $q.defer();
        Authentication.getCurrentUserFromServer().then(function() {
          deferred.resolve();
        }, function() {
          deferred.reject();
        })
        return deferred.promise;
      }
    }
  }).when('/account', {
    controller: 'AccountCtrl',
    templateUrl: 'views/account.html'
  }).when('/top', {
    controller: 'TopCtrl',
    templateUrl: 'views/top.html'
  }).otherwise({
    redirectTo: '/my'
  });

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

app.run(function($rootScope, $location, Authentication) {
  $rootScope.$on('httpError', function(event, response) {
    if (response.status == 401) {
      Authentication.clear();
      $location.path('/login');
    }
  });
});