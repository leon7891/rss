package rss.feed.aggregator.server.entity;

import java.io.Serializable;

public interface Entity<K extends Serializable> extends Serializable {

	public K getKey();
	
	public void setKey(K key);
}
