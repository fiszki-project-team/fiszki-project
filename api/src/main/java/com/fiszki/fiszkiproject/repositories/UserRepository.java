package com.fiszki.fiszkiproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiszki.fiszkiproject.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByDisplayName(String displayName);
}
