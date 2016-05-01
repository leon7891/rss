/**
 * 
 */

'use strict';

App
		.factory(
				'FeedService',
				[
						'$http',
						'$q',
						function($http, $q) {
							$http.defaults.headers.common['login'] = 'user';
							$http.defaults.headers.common['password'] = 'test';

							return {
								getNewFeeds : function() {
									return $http
											.get(
													'/rss/rest/news')
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

								getActivatedFeeds : function() {
									return $http
											.get(
													'/rss/rest/activated')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while getting activated');
														return $q
																.reject(errResponse);
													});
								},

								getInactivatedFeeds : function() {
									return $http
											.get(
													'/rss/rest/inactivated')
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while getting inactivated');
														return $q
																.reject(errResponse);
													});
								},

								addFeed : function(id) {
									return $http
											.post(
													'/rss/rest/add/'
															+ id)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while adding');
														return $q
																.reject(errResponse);
													});
								},

								removeFeed : function(id) {
									return $http.delete(
													'/rss/rest/remove/'
															+ id)
											.then(
													function(response) {
														return response.data;
													},
													function(errResponse) {
														console
																.error('Error while removing');
														return $q
																.reject(errResponse);
													});
								}

							};

						}])