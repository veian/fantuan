"use strict";

var module = angular.module("AngularJSApp");

function AuthenticationFactory($http, $cookieStore, $q) {

function Authentication() {
  this.auth = { current: $cookieStore.get("user") };
};

Authentication.prototype.current = function() {
  return this.auth.current;
};

Authentication.prototype.set = function(current) {
  $cookieStore.remove("user");

  if (current != null)
    $cookieStore.put("user", current);
  this.auth.current = current;

};

Authentication.prototype.login = function(username, password) {
  var self = this,
      promise = $http.post("../rest/auth/login", { username: username, password: password });

  return promise.then(function(response) {
    var data = response.data;

    if (data && data.success) {
      self.set(data.user);
    }

    return data && data.success;
  });
};

Authentication.prototype.logout = function() {
  var self = this,
      promise = $http.get("../rest/auth/logout");

  return promise.then(function() {
    self.set(null);
    return true;
  });
};

return new Authentication();
};

module.factory("Authentication", AuthenticationFactory);
