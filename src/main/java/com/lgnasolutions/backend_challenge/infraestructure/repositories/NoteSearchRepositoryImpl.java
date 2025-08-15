package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.ports.NoteSearchRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteEntity;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteTagEntity;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NoteSearchRepositoryImpl implements NoteSearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Note> searchNotes(NoteSearchCriteriaDTO criteria) {


        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NoteEntity> cq = cb.createQuery(NoteEntity.class);
        Root<NoteEntity> note = cq.from(NoteEntity.class);

        Predicate predicate = buildPredicate(cb, note, criteria);
        cq.where(predicate).distinct(true);

        List<NoteEntity> entities = entityManager.createQuery(cq).getResultList();
        return mapEntitiesToDomain(entities);
    }

    private Predicate buildPredicate(CriteriaBuilder cb, Root<NoteEntity> note, NoteSearchCriteriaDTO criteria) {
        Predicate predicate = cb.conjunction();

        if (criteria.getUserId() != null) {
            predicate = cb.and(predicate, cb.equal(note.get("userId"), criteria.getUserId()));
        }
        if (isNotEmpty(criteria.getTitle())) {
            predicate = cb.and(predicate, cb.like(cb.lower(note.get("title")), "%" + criteria.getTitle().toLowerCase() + "%"));
        }
        if (isNotEmpty(criteria.getContent())) {
            predicate = cb.and(predicate, cb.like(cb.lower(note.get("content")), "%" + criteria.getContent().toLowerCase() + "%"));
        }
        if (criteria.getTagIds() != null && !criteria.getTagIds().isEmpty()) {
            Join<NoteEntity, NoteTagEntity> join = note.join("noteTags");
            predicate = cb.and(predicate, join.get("tagId").in(criteria.getTagIds()));
        }
        return predicate;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    private List<Note> mapEntitiesToDomain(List<NoteEntity> entities) {
        return entities.stream()
                .map(NoteMapper::toDomain)
                .collect(Collectors.toList());
    }
}
