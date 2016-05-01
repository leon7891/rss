package rss.feed.aggregator.server.dao;

import java.io.Serializable;
import java.util.List;

import rss.feed.aggregator.server.entity.Entity;

public interface Dao<K extends Serializable, E extends Entity<K>> extends Serializable {

	E getByKey(K key);
	
	void save(E entity);
	
	void delete(E entity);
	
	List<E> getAll();
}
