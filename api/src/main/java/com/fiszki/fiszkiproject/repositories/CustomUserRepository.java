package com.fiszki.fiszkiproject.repositories;

import com.fiszki.fiszkiproject.persistence.entity.User;

public interface CustomUserRepository {
	
	User findByIdWithIdValidation(Long id);

}
