describe "Unit: Login Controllers", ->
  beforeEach module("Fantuan")

  it "should change location and broadcast after login successfully", ->
    capturedPath = undefined

    module ($provide) ->
      $provide.value "$location",
        path: (path) ->
          capturedPath = path
      $provide.factory "Authentication", ($q) ->
        login: (username, password) ->
          $q.when(true)
      # must add this explicit return, otherwise angular will throw an weird exception
      return

    inject ($location, $rootScope, $controller, $q) ->
      $scope = $rootScope.$new()
      spyOn($rootScope, "$broadcast").andCallThrough()
      $controller "LoginController", $scope: $scope
      $scope.login()
      # must add this explicit digest, otherwise $q will not be fired
      $rootScope.$digest()

      expect(capturedPath).toEqual '/my'
      expect($rootScope.$broadcast).toHaveBeenCalledWith 'login.success'


  