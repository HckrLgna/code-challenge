package com.lgnasolutions.backend_challenge.infraestructure.datasource;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@IdClass(NoteTagEntity.NoteTagId.class)
@Table(name = "note_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteTagEntity {
    @Id
    @Column(name = "note_id")
    private UUID noteId;

    @Id
    @Column(name = "tag_id")
    private UUID tagId;

    @Data
    public static class NoteTagId implements Serializable {
        private UUID noteId;
        private UUID tagId;
    }
}
