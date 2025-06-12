package com.api.Agro.dtos;

import jakarta.validation.constraints.NotBlank;

public record OcorrenciaRecordDto(@NotBlank String estacao, @NotBlank String clima, @NotBlank String descricao, Long fazendaid, Long doencaid) {

}
