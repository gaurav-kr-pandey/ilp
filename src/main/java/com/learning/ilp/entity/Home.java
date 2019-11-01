package com.learning.ilp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Home {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="homeid")
	private int homeId;
	@OneToMany(mappedBy = "home",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Cards> cards;
	
	public int getHomeId() {
		return homeId;
	}
	public void setHomeId(int homeId) {
		this.homeId = homeId;
	}
	public List<Cards> getCards() {
		return cards;
	}
	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}
	
}
