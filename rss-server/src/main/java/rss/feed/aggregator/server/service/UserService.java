package rss.feed.aggregator.server.service;

import javax.naming.AuthenticationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rss.feed.aggregator.server.dao.UserDao;
import rss.feed.aggregator.server.entity.User;

@Service
public class UserService {

	@Autowired
	protected UserDao userDao;
	
	private static final Logger T = Logger.getLogger(UserService.class);

	public User authenticateUser(String login, String password) throws AuthenticationException {
		User user = userDao.getByLogin(login);
		
		if (user == null || !user.getPassword().equals(password)) {
			throw new AuthenticationException("Wrong login/password.");
		}
		T.debug("user: '" + user.getName() + "' correctly authenticated.");
		return user;
		
	}
	
	public void updateUser(User user) {
		userDao.save(user);
	}

	public boolean createUser(String login, String password) {
		if (userDao.getByLogin(login) == null) {
			User user = new User();
			
			user.setName(login);
			user.setPassword(password);
			userDao.createUser(user);
			return true;
		}
		return false;
	}
}