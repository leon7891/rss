package rss.feed.aggregator.server.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import rss.feed.aggregator.server.AggregatorException;
import rss.feed.aggregator.server.dao.FeedDao;
import rss.feed.aggregator.server.dao.FeedItemDao;
import rss.feed.aggregator.server.entity.Feed;
import rss.feed.aggregator.server.entity.FeedItem;
import rss.feed.aggregator.server.entity.User;

@Service
public class AggregatorSrvice {

	private static final Logger T = Logger.getLogger(AggregatorSrvice.class);

	@Autowired
	protected FeedService feedService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected FeedDao feedDao;
	
	@Autowired
	FeedItemDao feedItemDao;

	private void updatFeed(Feed feed) {
		Date feedPubDate = feed.getLastPubDate();
		
		try {
			SyndFeed sFeed = feedService.lookupFeed(feed.getUrl());
		
			T.debug("Updating: " + feed.getTitle() + " size: " + sFeed.getEntries().size());
			
			if (feedPubDate == null || sFeed.getPublishedDate().after(feedPubDate)) {
				for (SyndEntry entity : sFeed.getEntries()) {
					if (feedPubDate == null || entity.getPublishedDate() == null || entity.getPublishedDate().after(feedPubDate)) {
						FeedItem item = new FeedItem();

						T.debug("New item for: " + feed.getTitle() + " adding " + entity.getTitle());
						item.setTitle(entity.getTitle());
						item.setUrl(entity.getLink());
						item.setPubDate(entity.getPublishedDate());
						item.setImage(entity.getUri());
						item.setFeed(feed);
						if (item.getPubDate() == null) {
							item.setPubDate(new Date());
						}
						feed.getFeedItems().add(item);
						
						feedItemDao.save(item);
					}
				}
			}
		} catch (AggregatorException | RuntimeException e) {
			T.warn("Error while reading feed: '" + feed.getUrl() + "' : ", e);
		}
		feed.setLastPubDate(new Date());
		feedDao.save(feed);
		T.debug("save done feed");
	}
	
	public void updateFeeds() {
		T.debug("Running feed update.");
		List<Feed> feeds = feedDao.getAll();

		for (Feed feed : feeds) {
			updatFeed(feed);
		}
	}

	public List<FeedItem> listNewFeeds(User user) {
		T.debug("Retriving new feeds for: '" + user.getName() + "'");
		List<FeedItem> items = feedItemDao.getNewItems(user);
		
		for (Feed feed : user.getFeeds()) {
			if (feed.getLastPubDate() == null) {
				updatFeed(feed);
			}
		}
		
		user.setLastPubDate(new Date());
		userService.updateUser(user);
		return items;
	}

	public List<Feed> listActivatedFeeds(User user) {
		T.debug("Retriving activated feeds for: '" + user.getName() + "'");
		return user.getFeeds();
	}

	public List<Feed> listInactivatedFeeds(User user) {
		T.debug("Retriving inactivated feeds for: '" + user.getName() + "'");
		return feedService.getInactivatedFeeds(user.getFeeds());
	}

	public void addFeed(User user, int feed) {
		T.debug("Adding feed to user: '" + user.getName() + "'");
		user.getFeeds().add(feedService.getFeed(feed));
		userService.updateUser(user);
	}

	public void removeFeed(User user, int feed) {
		T.debug("Removing feed to user: '" + user.getName() + "'");
		user.getFeeds().remove(feedService.getFeed(feed));
		userService.updateUser(user);
	}
}
