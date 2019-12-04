package com.fiszki.fiszkiproject.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fiszki.fiszkiproject.persistence.entity.Tag;
import com.fiszki.fiszkiproject.repositories.CustomTagRepository;

@Repository
public class TagRepositoryImpl implements CustomTagRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Tag> findTagsByUserId(Long userId) {
		return em.createNamedQuery("Tag.findTagsByUserId", Tag.class).setParameter("id", userId).getResultList();
	}

}
