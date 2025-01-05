package com.microservice.boilerplate.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class AbstractDTO {
    private UUID id;
    private Long version;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
