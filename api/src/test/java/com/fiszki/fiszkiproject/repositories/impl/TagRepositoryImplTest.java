package com.fiszki.fiszkiproject.repositories.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.persistence.entity.Tag;
import com.fiszki.fiszkiproject.repositories.TagRepository;

@SpringBootTest
@Transactional
class TagRepositoryImplTest {
	
	@Autowired
	private TagRepository tagRepository;

	@Test
	void shouldReturnListOfAllTagsPerUser() {
		List<Tag> tags = tagRepository.findTagsByUserId(1L);
		
		assertEquals(2, tags.size());
		assertEquals("holidays", tags.get(0).getDisplayName());
		assertEquals("work", tags.get(1).getDisplayName());
	}

	@Test
	void shouldReturnEmptyListWhenNoUserWithGivenId() {
		List<Tag> tags = tagRepository.findTagsByUserId(-3L);
		
		assertThat(tags.isEmpty());
	}
}
