package rss.feed.aggregator.server.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@javax.persistence.Entity
@Table(name="USERS")
public class User implements Entity<Integer> {

	private static final long serialVersionUID = -2794331168781981898L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="LAST_PUB_DATE", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPubDate;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="PASSWORD")
	private String password;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(
		      name="SUBSCRIPTIONS",
		      joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
		      inverseJoinColumns=@JoinColumn(name="FEED_ID", referencedColumnName="ID"))
	private List<Feed> feeds;
	
	
	@Override
	@JsonIgnore
	public Integer getKey() {
		return id;
	}

	@Override
	public void setKey(Integer key) {
		id = key;
	}

	public Date getLastPubDate() {
		return lastPubDate;
	}

	public void setLastPubDate(Date lastPubDate) {
		this.lastPubDate = lastPubDate;
	}

	@JsonIgnore
	public List<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		return ((User) obj).name.equals(this.name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
