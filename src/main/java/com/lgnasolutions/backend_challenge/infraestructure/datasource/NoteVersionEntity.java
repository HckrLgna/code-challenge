package com.lgnasolutions.backend_challenge.infraestructure.datasource;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "note_versions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteVersionEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "note_id", nullable = false)
    private UUID noteId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
