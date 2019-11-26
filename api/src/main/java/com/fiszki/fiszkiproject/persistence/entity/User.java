package com.fiszki.fiszkiproject.persistence.entity;

import java.util.Set;

import javax.persistence.Entity;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User extends AbstractEntity {
	
	private String password;
	
	private String email;
	
	private String displayName;
	
	private Set<Card> cards;
	
	private Set<Tag> tags;
	
	private Set<Lesson> lessons;
	
}
