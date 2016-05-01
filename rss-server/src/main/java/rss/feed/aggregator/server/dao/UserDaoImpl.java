package rss.feed.aggregator.server.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.User;

@Transactional
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	private static final long serialVersionUID = -8121755108785865295L;

	public UserDaoImpl() {
		super(User.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rss.feed.aggregator.server.dao.IUserDao#getByLogin(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public User getByLogin(String login) {
		List<User> users = getSession().createCriteria(User.class).add(Restrictions.eq("name", login)).list();
		if (users == null || users.size() == 0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void createUser(User user) {
		// this is to preven a hibernate bug

		getSession()
				.createSQLQuery(
						"INSERT INTO users (NAME, PASSWORD) VALUES (:name, :password)")
				.setParameter("name", user.getName()).setParameter("password", user.getPassword())
				.executeUpdate();
	}

}
