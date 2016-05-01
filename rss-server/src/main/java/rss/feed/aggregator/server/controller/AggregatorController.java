package rss.feed.aggregator.server.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rss.feed.aggregator.server.entity.Feed;
import rss.feed.aggregator.server.entity.FeedItem;
import rss.feed.aggregator.server.service.AggregatorSrvice;
import rss.feed.aggregator.server.service.UserService;

@RestController
public class AggregatorController {

	@Autowired
	protected UserService userService;
	
	@Autowired
	AggregatorSrvice aggregatorSrvice;

	@RequestMapping(value = "/rest/news", method = RequestMethod.GET)
	public ResponseEntity<List<FeedItem>> listNewFeeds(@RequestHeader("login") String login, @RequestHeader("password") String password) {
		List<FeedItem> feeds;
		
		try {
			feeds = aggregatorSrvice.listNewFeeds(userService.authenticateUser(login, password));
		} catch (AuthenticationException e) {
			return new ResponseEntity<List<FeedItem>>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<List<FeedItem>>(feeds, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/activated", method = RequestMethod.GET)
	public ResponseEntity<List<Feed>> listActivatedFeeds(@RequestHeader("login") String login,
			@RequestHeader("password") String password) {
		List<Feed> feeds;
		
		try {
			feeds = aggregatorSrvice.listActivatedFeeds(userService.authenticateUser(login, password));
		} catch (AuthenticationException e) {
			return new ResponseEntity<List<Feed>>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<List<Feed>>(feeds, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/inactivated", method = RequestMethod.GET)
	public ResponseEntity<List<Feed>> listInactivatedFeeds(@RequestHeader("login") String login,
			@RequestHeader("password") String password) {
		List<Feed> feeds;
		
		try {
			feeds = aggregatorSrvice.listInactivatedFeeds(userService.authenticateUser(login, password));
		} catch (AuthenticationException e) {
			return new ResponseEntity<List<Feed>>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<List<Feed>>(feeds, HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/add/{feed}", method = RequestMethod.POST)
	public ResponseEntity<?> addFeed(@RequestHeader("login") String login, @RequestHeader("password") String password,
			@PathVariable("feed") int feed) {
		try {
			aggregatorSrvice.addFeed((userService.authenticateUser(login, password)), feed);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/rest/remove/{feed}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeFeed(@RequestHeader("login") String login, @RequestHeader("password") String password,
			@PathVariable("feed") int feed) {
		try {
			aggregatorSrvice.removeFeed((userService.authenticateUser(login, password)), feed);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rest/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateFeeds(@RequestHeader("login") String login, @RequestHeader("password") String password) {
		try {
			userService.authenticateUser(login, password);
			if (login.equals("admin")) {
				aggregatorSrvice.updateFeeds();
			}
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
