/**
 * 
 */

'use strict';

App
		.factory(
				'UserService',
				[
						'$http',
						'$q',
						function($http, $q) {
							
							return {
							logUserIn : function() {
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
							},
							
							createUser : function(username, password) {
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
							}}
							
						}]);