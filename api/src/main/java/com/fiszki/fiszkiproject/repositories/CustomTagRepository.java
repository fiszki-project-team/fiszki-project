package com.fiszki.fiszkiproject.repositories;

import java.util.List;

import com.fiszki.fiszkiproject.persistence.entity.Tag;

public interface CustomTagRepository {

	List<Tag> findTagsByUserId(Long id);

}
