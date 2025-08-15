package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.entities.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository {
    Tag create(Tag tag);
    Optional<Tag> findById(UUID id);
    List<Tag> findByUserId(UUID userId);
    void delete(UUID id);
    Optional<Tag> findByUserIdAndName(UUID userId, String name);

}
