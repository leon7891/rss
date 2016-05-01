/**
 * 
 */

'use strict';

App
		.factory(
				'UserService',
				[
						'$http',
						'$q', '$cookies',
						function($http, $q, $cookies) {
							var self = this;
							
							self.login = $cookies.get('login');
							self.password = $cookies.get('password');
							
							self.logUserIn = function() {
								return $http
								.get(
										'/rss/rest/login')
								.then(
										function(response) {
											return response.data;
										},
										function(errResponse) {
											console
													.error('Error while getting news');
											return $q
													.reject(errResponse);
										});
							};
							
						}]);