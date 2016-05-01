package rss.feed.aggregator.server.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.User;

@Transactional
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	private static final long serialVersionUID = -8121755108785865295L;

	public UserDaoImpl() {
		super(User.class);
	}

	/* (non-Javadoc)
	 * @see rss.feed.aggregator.server.dao.IUserDao#getByLogin(java.lang.String)
	 */
	@Override
	public User getByLogin(String login)  {
		return (User) getSession().createCriteria(User.class)
				.add(Restrictions.eq("name", login)).list().get(0);
	}
	
}
