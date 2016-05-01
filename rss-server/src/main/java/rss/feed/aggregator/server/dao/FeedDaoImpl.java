package rss.feed.aggregator.server.dao;

import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.Feed;

@Transactional
public class FeedDaoImpl extends AbstractDao<Integer, Feed> implements FeedDao {

	private static final long serialVersionUID = 2695629301447683995L;

	public FeedDaoImpl() {
		super(Feed.class);
	}

}
