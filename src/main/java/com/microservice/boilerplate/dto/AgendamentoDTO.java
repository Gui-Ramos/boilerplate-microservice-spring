package com.microservice.boilerplate.dto;

import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class AgendamentoDTO extends AbstractDTO {
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private UUID sala;
}
