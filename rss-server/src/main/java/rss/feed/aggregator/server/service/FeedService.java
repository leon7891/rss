package rss.feed.aggregator.server.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

import rss.feed.aggregator.server.AggregatorException;
import rss.feed.aggregator.server.dao.FeedDao;
import rss.feed.aggregator.server.entity.Feed;

@Service
public class FeedService {
	
	@Autowired
	protected FeedDao feedDao;
	
	private static final Logger T = Logger.getLogger(FeedService.class);

	public SyndFeed lookupFeed(String url) throws AggregatorException {
		try {
			URL feedUrl = new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			return input.build(new InputStreamReader(feedUrl.openStream()));
		} catch (IllegalArgumentException | FeedException | IOException e) {
			T.debug("Error occured retriving feed at '" + url + "' :" + e.getMessage());
			throw new AggregatorException(e);
		}
	}
	
	public Feed getFeed(int feed) {
		return feedDao.getByKey(feed);
	}

	public List<Feed> getInactivatedFeeds(List<Feed> feeds) {
		return feedDao.getAll().stream().filter(f -> !feeds.contains(f))
				.collect(Collectors.toList());
	}
}
