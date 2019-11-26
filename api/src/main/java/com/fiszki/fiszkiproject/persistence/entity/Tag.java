package com.fiszki.fiszkiproject.persistence.entity;

import java.util.List;

import javax.persistence.Entity;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Tag extends AbstractEntity {

	private String displayName;
	
	private List<Card> cards;
	
}
