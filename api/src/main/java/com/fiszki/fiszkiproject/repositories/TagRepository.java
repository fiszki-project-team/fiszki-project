package com.fiszki.fiszkiproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiszki.fiszkiproject.persistence.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository {

}
