package com.lgnasolutions.backend_challenge.infraestructure.datasource;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(name = "is_archived")
    private boolean archived=false;
    //TODO: Fix version number

    @OneToMany
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    private java.util.Set<NoteTagEntity> noteTags = new java.util.HashSet<>();
}
