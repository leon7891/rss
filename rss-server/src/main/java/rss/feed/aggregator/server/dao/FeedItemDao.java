package rss.feed.aggregator.server.dao;

import java.util.List;

import rss.feed.aggregator.server.entity.FeedItem;
import rss.feed.aggregator.server.entity.User;

public interface FeedItemDao extends Dao<Integer, FeedItem> {
	
	List<FeedItem> getNewItems(User user);
}
