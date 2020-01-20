package com.fiszki.fiszkiproject.repositories.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fiszki.fiszkiproject.persistence.entity.Tag;
import com.fiszki.fiszkiproject.repositories.TagRepository;

@SpringBootTest
@Transactional
class TagRepositoryImplTest {

	@Autowired
	private TagRepository tagRepository;

	@Nested
	@DisplayName("Tags list - get by User Id")
	class FindTagsByUserId {
		@ParameterizedTest
		@DisplayName("returns an empty List when User does not exist in DataBase")
		@NullSource
		@ValueSource(longs = { -3L, 0L, 2000L })
		public void returnEmptyListWhenNoUserInDataBase(Long Id) {
			assertThat(tagRepository.findTagsByUserId(Id)).isEmpty();
		}

		@ParameterizedTest
		@DisplayName("returns an empty List when User has zero Tags")
		@ValueSource(longs = { 3L })
		public void returnEmptyListWhenUserHasZeroTags(Long Id) {
			assertThat(tagRepository.findTagsByUserId(Id)).isEmpty();
		}

		@ParameterizedTest
		@DisplayName("returns a list of Tags for User with given Id")
		@ValueSource(longs = { 1L })
		public void returnListOfTagsForUser(Long Id) {
			List<Tag> tags = tagRepository.findTagsByUserId(1L);

			assertThat(tags).isNotEmpty().isNotNull();
			assertEquals(2, tags.size());
			assertEquals("holidays", tags.get(0).getDisplayName());
		}
	}

}
