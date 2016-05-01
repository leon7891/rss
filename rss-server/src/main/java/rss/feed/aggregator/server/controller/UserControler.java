package rss.feed.aggregator.server.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rss.feed.aggregator.server.service.UserService;

@RestController
public class UserControler {

	@Autowired
	protected UserService userService;
	
	@RequestMapping(value = "/rest/login", method = RequestMethod.GET)
	public ResponseEntity<?> logUserIn(@RequestHeader("login") String login, @RequestHeader("password") String password) {
		try {
			userService.authenticateUser(login, password);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rest/createUser/{login}/{password}", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@PathVariable("login") String login, @PathVariable("password") String password) {
		userService.createUser(login, password);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
