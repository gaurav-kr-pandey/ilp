package com.learning.ilp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Syllabus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="syllabusId")
	private long syllabusId;
	private String topic;
	public long getSyllabusId() {
		return syllabusId;
	}
	public void setSyllabusId(long syllabusId) {
		this.syllabusId = syllabusId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

}
