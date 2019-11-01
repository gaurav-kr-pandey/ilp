package com.learning.ilp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.ilp.entity.Cards;
import com.learning.ilp.entity.Home;
import com.learning.ilp.repository.CardsRepository;

@Service
public class HomeServices {
	
	@Autowired
	private CardsRepository cardsRepository;
	
	public Home startHome() {
		Home home = new Home();
		List<Cards> cards = cardsRepository.findAll();
		List<Cards> homeCards=cards.stream().filter(card->!card.getIsCareerProgram()).collect(Collectors.toList());
		home.setCards(homeCards);
		return home;
	}
	
	public Home careerCards() {
		Home home = new Home();
		List<Cards> cards = cardsRepository.findAll();
		List<Cards> homeCards=cards.stream().filter(card->card.getIsCareerProgram()).collect(Collectors.toList());
		home.setCards(homeCards);
		return home;
	}
}
