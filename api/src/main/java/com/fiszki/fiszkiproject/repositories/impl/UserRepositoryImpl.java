package com.fiszki.fiszkiproject.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fiszki.fiszkiproject.persistence.entity.User;
import com.fiszki.fiszkiproject.repositories.CustomUserRepository;
import com.fiszki.fiszkiproject.services.exceptions.InvalidIdException;
import com.fiszki.fiszkiproject.services.exceptions.common.APIErrors;

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
		try {
			User user = em.find(User.class, id);

			if (user != null) {
				return user;
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidIdException(APIErrors.USER_NOT_IN_DATABASE.name());
		}
		throw new InvalidIdException(APIErrors.USER_NOT_IN_DATABASE.name());
	}

}
