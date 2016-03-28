package com.liuyao.test;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "forum")
public class ForeumDmo {
	
	@Override
	public String toString() {
		return "ForeumDmo [id=" + id + ", username=" + username + ", city=" + city + ", topicTitle=" + topicTitle
				+ ", content=" + content + ", createTime=" + createTime + "]";
	}
	@Id
	@Column(name= "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	private String username;
	@Column(name = "city")
	private String city;
	@Column(name = "topic_title")
	private String topicTitle;
	@Column(name = "content")
	private String content;
	@Column(name = "create_time")
	private Date createTime;
	
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
