package com.learning.ilp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.ilp.entity.Cards;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Integer> {

	Cards findByCardId(int cardId);

}
