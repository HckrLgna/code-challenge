package com.lgnasolutions.backend_challenge.domain.entities;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteVersion implements NoteState{
    private UUID id;
    private UUID noteId;
    private String title;
    private String content;
    private int versionNumber;
    private Instant createdAt;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getVersionNumber() {
        return versionNumber;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }
}
