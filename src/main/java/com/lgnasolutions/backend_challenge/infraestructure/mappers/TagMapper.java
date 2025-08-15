package com.lgnasolutions.backend_challenge.infraestructure.mappers;

import com.lgnasolutions.backend_challenge.domain.dto.TagDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Tag;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.TagEntity;

public class TagMapper {
    public static TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
    public static Tag toDomain(TagEntity entity) {
        return Tag.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .color(entity.getColor())
                .build();
    }

    public static TagEntity toEntity(Tag tag) {
        return TagEntity.builder()
                .id(tag.getId())
                .userId(tag.getUserId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}
