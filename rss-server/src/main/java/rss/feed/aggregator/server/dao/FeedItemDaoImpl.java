package rss.feed.aggregator.server.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.Feed;
import rss.feed.aggregator.server.entity.FeedItem;
import rss.feed.aggregator.server.entity.User;

@Transactional
public class FeedItemDaoImpl extends AbstractDao<Integer, FeedItem> implements FeedItemDao {

	private static final long serialVersionUID = 2567326557514911122L;

	public FeedItemDaoImpl() {
		super(FeedItem.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FeedItem> getNewItems(User user) {
		Criteria criteria = getSession().createCriteria(FeedItem.class);

		Disjunction or = Restrictions.or();

		for (Feed feed : user.getFeeds()) {
			or.add(Restrictions.eq("feed", feed));
		}
		if (user.getLastPubDate() != null) {
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(user.getLastPubDate());
			cal.add(Calendar.DATE, -1);
			criteria.add(Restrictions.and(or, Restrictions.ge("pubDate", cal.getTime())));	
		} else {
			criteria.add(or);
		}
		return criteria.list();
	}

	@Override
	public void save(FeedItem entity) {
		// this is to preven a hibernate bug

		if (getSession().createCriteria(FeedItem.class).add(Restrictions.eqOrIsNull("url", entity.getUrl())).list()
				.size() <= 0) {
			getSession()
					.createSQLQuery(
							"INSERT INTO feed_items (TITLE, URL, PUB_DATE, FEED_ID) VALUES (:title, :url, :pubDate, :feed)")
					.setParameter("title", entity.getTitle()).setParameter("url", entity.getUrl())
					.setParameter("pubDate", entity.getPubDate()).setParameter("feed", entity.getFeed().getId())
					.executeUpdate();
		}
	}

}
