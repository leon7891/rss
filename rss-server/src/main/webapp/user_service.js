/**
 * 
 */

'use strict';

App
		.factory(
				'UserService',
				[
						'$http',
						'$q', '$cookieStore',
						function($http, $q, $cookieStore) {
							var self = this;
							
							self.login = $cookieStore.get('login');
							self.password = $cookieStore.get('password');
							
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
							
							self.register = function(username, password) {
								return $http
								.post(
										'/rss/rest/createUser/' + username + "/" + password)
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