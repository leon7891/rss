/**
 * 
 */

'use strict';

App.controller('UserController', [ '$scope', '$location', 'UserService',
		function($scope, $location, UserService) {
			var self = this;

			
			self.createUser = function() {
				console.debug("createUser");
				UserService.createUser(self.user.login, self.user.password).then(function(d) {
					$location.path('/../');
				}, function(errResponse) {
				});
			};
			
		} ]);