package com.fiszki.fiszkiproject.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User extends AbstractEntity {
	
	private String password;
	
	private String email;
	
	private String displayName;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Card> cards;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Tag> tags;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Lesson> lessons;
	
	public void addCard(Card card) {
		cards.add(card);
		card.setUser(this);
    }
 
    public void addLesson(Lesson lesson) {
    	lessons.add(lesson);
    	lesson.setUser(this);
    }
    
    public void addTag(Tag tag) {
    	tags.add(tag);
    	tag.setUser(this);
    }
 
    public void removeCard(Card card) {
    	card.setUser(null);
        this.cards.remove(card);
    }
    
    public void removeLesson(Lesson lesson) {
    	lesson.setUser(null);
        this.lessons.remove(lesson);
    }
 
    public void removeTag(Tag tag) {
    	tag.setUser(null);
        this.tags.remove(tag);
    }
}

