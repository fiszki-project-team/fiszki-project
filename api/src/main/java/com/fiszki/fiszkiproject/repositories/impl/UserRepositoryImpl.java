package com.fiszki.fiszkiproject.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.CustomUserRepository;
import com.fiszki.fiszkiproject.services.exceptions.InvalidIdException;
import com.fiszki.fiszkiproject.services.exceptions.common.StringMessages;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {

	@PersistenceContext
	private EntityManager em;

	/**
	 * <b>Returns:</b> User with given id; throws InvalidIdException when user does
	 * not occur in Database.
	 */
	@Override
	public User findByIdWithIdValidation(Long id) {
		User user = em.find(User.class, id);

		if (user != null) {
			return user;
		} else {
			throw new InvalidIdException(StringMessages.USER_NOT_IN_DATABASE);
		}
	}

}
