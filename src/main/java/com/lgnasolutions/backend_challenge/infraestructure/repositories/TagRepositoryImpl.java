package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.Tag;
import com.lgnasolutions.backend_challenge.domain.ports.TagRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.TagEntity;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteMapper;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private final TagJpaRepository tagJpaRepository;

    @Autowired
    public TagRepositoryImpl(TagJpaRepository tagJpaRepository) {
        this.tagJpaRepository = tagJpaRepository;
    }

    @Override
    public Tag create(Tag tag) {
        TagEntity tagEntity = TagMapper.toEntity(tag);
        TagEntity savedTagEntity = tagJpaRepository.save(tagEntity);
        return TagMapper.toDomain(savedTagEntity);
    }

    @Override
    public Optional<Tag> findById(UUID id) {
        return tagJpaRepository.findById(id).map(TagMapper::toDomain);
    }

    @Override
    public List<Tag> findByUserId(UUID userId) {
        return tagJpaRepository.findByUserId(userId)
                .stream()
                .map(TagMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        tagJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Tag> findByUserIdAndName(UUID userId, String name) {
        return tagJpaRepository.findByUserIdAndName(userId, name)
                .map(TagMapper::toDomain);
    }
}
