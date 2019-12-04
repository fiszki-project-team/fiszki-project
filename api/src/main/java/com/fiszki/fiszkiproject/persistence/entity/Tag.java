package com.fiszki.fiszkiproject.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.fiszki.fiszkiproject.persistence.entity.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQuery(name = "Tag.findTagsByUserId", query = "SELECT tag FROM Tag tag WHERE tag.user.id = :id ORDER BY tag.displayName ASC")
public class Tag extends AbstractEntity {

	private String displayName;

	@ManyToMany(mappedBy = "tags")
	private Set<Card> cards = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

}
