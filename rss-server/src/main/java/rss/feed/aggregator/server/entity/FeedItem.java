package rss.feed.aggregator.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@javax.persistence.Entity
@Table(name="feed_items")
public class FeedItem implements Entity<Integer> {

	private static final long serialVersionUID = 6463847984029996675L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;

	@Column(name="TITLE")
	private String title;
	
	@Column(name="URL", unique=true)
	private String url;
	
	@Column(name="PUB_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pubDate;
	
	@ManyToOne
	@JoinColumn(name="FEED_ID")
	private Feed feed;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	@JsonIgnore
	public Integer getKey() {
		return id;
	}

	@Override
	public void setKey(Integer key) {
		id = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title.length() > 50) {
			this.title = title.substring(0, 50);
		} else {
			this.title = title;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getPubDate() {
		return pubDate;
	}

	@JsonIgnore
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	
	@JsonIgnore
	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public String getImage() {
		return feed.getImage();
	}

	
	public int getFeedId() {
		return this.getFeed().getId();
	}
	
}
