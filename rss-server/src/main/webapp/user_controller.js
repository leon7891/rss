/**
 * 
 */

'use strict';

App.controller('UserController', [ '$scope', 'UserService',
		function($scope, UserService) {
			var self = this;

			
			self.createUser = function() {
				console.debug("createUser");
				UserService.createUser(self.user.login, self.user.password);
				$location.path("/rss/");
			};
			
		} ]);