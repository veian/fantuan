var module = angular.module("Fantuan");

module.factory('AccountEntry', function ($resource, Authentication) {
    var AccountEntry = $resource('../api/accounts/:user/entry/:action', // URL
        {user: Authentication.current()}, // Default Parameters
        {count: {method: 'GET', isArray: false, params: {action: 'count'}}});   // Extra method
    return AccountEntry;
});

module.factory('Account', function ($resource) {
    var Account = $resource('../api/accounts/:user');
    return Account;
});

module.factory('Meal', function ($resource) {
    var Meal = $resource('../api/meals/:action', {},
        {count: {method: "GET", isArray: false, params: {action: 'count'}}
        });
    return Meal;
});