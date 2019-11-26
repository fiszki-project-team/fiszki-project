package com.fiszki.fiszkiproject.persistence.entity;

import javax.persistence.Entity;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Card extends AbstractEntity {

	private String text;

	private String translation;

}
