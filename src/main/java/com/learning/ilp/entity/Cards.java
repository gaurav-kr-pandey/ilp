package com.learning.ilp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cardid")
	private int cardId;
	private String cardTitle;
	private String imageName;
	private String alternateName;
	@Column(name = "cardtext",columnDefinition = "TEXT")
	private String cardText;
	@Column(name="courseid")
	private int courseId;
	@Column(columnDefinition = "varchar(255) default 'enable'")
	private String disabled;
	private boolean isCareerProgram;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "homeid", nullable = false)
	private Home home;

	public Cards() {
	}

	public Cards(String cardTitle, String imageName, String alternateName, String cardText, int courseId,
			boolean isCareerProgram) {
		this.cardTitle = cardTitle;
		this.imageName = imageName;
		this.alternateName = alternateName;
		this.cardText = cardText;
		this.courseId = courseId;
		this.isCareerProgram = isCareerProgram;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageUrl(String imageName) {
		this.imageName = imageName;
	}

	public String getCardText() {
		return cardText;
	}

	public void setCardText(String cardText) {
		this.cardText = cardText;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean getIsCareerProgram() {
		return isCareerProgram;
	}

	public void setIsCareerProgram(boolean isCareerProgram) {
		this.isCareerProgram = isCareerProgram;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
 
}
