/*global angular*/
var module = angular.module("Fantuan");

module.factory("Authentication", function ($http, $q, $rootScope) {

  function Authentication() {
    this.auth = {};  
  }

  Authentication.prototype.getCurrentUserFromServer = function() {
    var self = this;
    return $http.get("../api/auth/user").success(function(data) {
      self.set(data);
      if (data != "anonymousUser")
        $rootScope.$broadcast("login.success");
    });
  };

  Authentication.prototype.current = function() {
    return this.auth.current;
  };

  Authentication.prototype.set = function(current) {
    this.auth.current = current;
  };

  Authentication.prototype.login = function(username, password) {
    var self = this;
    var form = $.param({ 'j_username': username, 'j_password': password, '_spring_security_remember_me': true });
    var promise = $http({
      method: 'POST',
      url: "../j_spring_security_check",
      data: form,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    });

    return promise.then(function(response) {
      self.set(username);
      $rootScope.loginErrorMessage = null;
      return true;
    }, function(response) {
      $rootScope.loginErrorMessage = "用户名或者密码不正确！";
      return false;
    });
  };

  Authentication.prototype.logout = function() {
    var self = this,
      promise = $http.post("../j_spring_security_logout");

    return promise.then(function() {
      self.set("anonymousUser");
      return true;
    });
  };

  Authentication.prototype.clear = function() {
    this.set("anonymousUser");
  };

  return new Authentication();
});