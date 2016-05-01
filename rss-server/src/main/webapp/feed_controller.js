/**
 * 
 */

'use strict';

App.controller('FeedController', [ '$scope', 'FeedService',
		function($scope, FeedService) {
			var self = this;
			self.feeds = [];

			self.getNewFeeds = function() {
				console.debug("get new feeds");
				FeedService.getNewFeeds().then(function(d) {
					self.feeds = d;
				}, function(errResponse) {
				});
			};
			
			self.getNewFeeds();
		} ]);