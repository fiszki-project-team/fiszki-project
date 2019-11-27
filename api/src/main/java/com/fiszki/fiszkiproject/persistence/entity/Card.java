package com.fiszki.fiszkiproject.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Card extends AbstractEntity {

	private String text;

	private String translation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "card_tag", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();
	
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CardInUse> cardsInUse;
	
	public void addCardInUse(CardInUse card) {
		cardsInUse.add(card);
		card.setCard(this);
    }
 
    public void removeCardInUse(CardInUse card) {
    	card.setCard(null);
        this.cardsInUse.remove(card);
    }

}
