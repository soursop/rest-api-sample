package com.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@NotEmpty
	@ApiModelProperty(value = "title of Article")
	private String title;
	@NotEmpty
	@ApiModelProperty(value = "contents of Article")
	private String contents;
	@NotEmpty
	@ApiModelProperty(value = "writer of Article")
	private String writer;
	
	public Article() { }
	
	public Article(long id, String title, String writer) {
		this.id = id;
		this.title = title;
		this.writer = writer;
	}

	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public void update(Article target) {
		if(target.title != null) {
			this.title = target.title;
		}
		if(target.writer != null) {
			this.writer = target.writer;
		}
		if(target.contents != null) {
			this.contents = target.contents;
		}
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", contents="
				+ contents + ", writer=" + writer + "]";
	}

}
