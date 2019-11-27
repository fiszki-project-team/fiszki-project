package com.fiszki.fiszkiproject.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lesson extends AbstractEntity {

	private String displayName;

	private String description;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CardInUse> cardsInUse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	public void addCardInUse(CardInUse card) {
		cardsInUse.add(card);
		card.setLesson(this);
    }
 
    public void removeCardInUse(CardInUse card) {
    	card.setLesson(null);
        this.cardsInUse.remove(card);
    }

}
