package com.fiszki.fiszkiproject.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;
import com.fiszki.fiszkiproject.persistence.entity.common.CardStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "CARD_IN_USE")
public class CardInUse extends AbstractEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARD_ID")
	private Card card;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LESSON_ID")
	private Lesson lesson;
	
	private Long displayOrder;
	
	@Enumerated(EnumType.STRING)
	private CardStatus status;
	
	private boolean wasTouched;

}
