package rss.feed.aggregator.server.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import rss.feed.aggregator.server.entity.Entity;

@Transactional
public abstract class AbstractDao<K extends Serializable, E extends Entity<K>> implements Dao<K, E> {
	
	private Class<E> type;
	private static final long serialVersionUID = -5068939559731201946L;
	
	public AbstractDao(Class<E> type) {
		this.type = type;
	}
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
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
