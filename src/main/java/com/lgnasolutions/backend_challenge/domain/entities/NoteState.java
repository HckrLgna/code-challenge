package com.lgnasolutions.backend_challenge.domain.entities;

import java.time.Instant;

public interface NoteState {
    String getTitle();
    String getContent();
    int getVersionNumber();
    Instant getCreatedAt();
}
