package rss.feed.aggregator.server.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.Entity;

@Transactional
public abstract class AbstractDao<K extends Serializable, E extends Entity<K>> implements Dao<K, E> {

	private static final Logger T = Logger.getLogger(AbstractDao.class);

	private Class<E> type;
	private static final long serialVersionUID = -5068939559731201946L;

	public AbstractDao(Class<E> type) {
		this.type = type;
	}

	private static boolean isDbInitialized = false;

	private static void init(Session session) {
		if (!isDbInitialized) {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					DatabaseMetaData dbm = connection.getMetaData();
					ResultSet tables = dbm.getTables(null, null, "USERS", null);
					if (tables.next()) {
						isDbInitialized = true;
					}
				}
			});
		}

		if (!isDbInitialized) {
			InputStream is = AbstractDao.class.getResourceAsStream("/rssfeeds.sql");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			try {
				while (true) {
					String sql = reader.readLine();

					if (sql == null) {
						break;
					}
					if (!sql.equals("")) {
						session.createSQLQuery(sql).executeUpdate();
					}
				}
				is.close();

			} catch (IOException e1) {
				T.warn("couldn't creat db.");
			}
			isDbInitialized = true;
		}
	}

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		init(session);
		return session;
	}

	@Override
	public E getByKey(K key) {
		return getSession().get(type, key);
	}

	@Override
	public void save(E entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void delete(E entity) {
		getSession().delete(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return getSession().createCriteria(type).list();
	}

}
