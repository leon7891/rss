package rss.feed.aggregator.server.dao;

import rss.feed.aggregator.server.entity.User;

public interface UserDao extends Dao<Integer, User> {

	User getByLogin(String login);
	
	void createUser(User user);

}