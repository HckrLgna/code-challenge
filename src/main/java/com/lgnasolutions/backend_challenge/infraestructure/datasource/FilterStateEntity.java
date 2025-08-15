package com.lgnasolutions.backend_challenge.infraestructure.datasource;

import com.lgnasolutions.backend_challenge.infraestructure.mappers.JsonMapConverter;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "filter_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterStateEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, Object> filters;
}
