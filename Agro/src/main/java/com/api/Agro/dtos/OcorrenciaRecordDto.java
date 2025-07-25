package com.api.Agro.dtos;

import jakarta.validation.constraints.NotBlank;

public record OcorrenciaRecordDto(@NotBlank String numero, @NotBlank String estacao, @NotBlank String clima, @NotBlank String status, @NotBlank String descricao, Long fazendaid, Long doencaid) {

}
