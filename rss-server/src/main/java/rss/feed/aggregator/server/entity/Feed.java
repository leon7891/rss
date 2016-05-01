package rss.feed.aggregator.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@javax.persistence.Entity
@Table(name="FEEDS")
public class Feed implements Entity<Integer> {

	private static final long serialVersionUID = 8665055254346612292L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	
	@Column(name="URL", unique=true)
	private String url;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="LAST_PUB_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPubDate;
	
	@Column(name="IMAGE")
	private String image;
	
	@OneToMany(mappedBy="feed")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FeedItem> feedItems;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Feed)) {
			return false;
		}
		return ((Feed) obj).id == this.id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonIgnore
	public Date getLastPubDate() {
		return lastPubDate;
	}

	public void setLastPubDate(Date lastPubDate) {
		this.lastPubDate = lastPubDate;
	}

	@JsonIgnore
	public List<FeedItem> getFeedItems() {
		return feedItems;
	}

	public void setFeedItems(List<FeedItem> feedItems) {
		this.feedItems = feedItems;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
