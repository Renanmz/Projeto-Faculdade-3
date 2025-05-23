package com.api.Agro.dtos;

import com.api.Agro.model.DoencaModel;
import com.api.Agro.model.FazendaModel;

import jakarta.validation.constraints.NotBlank;

public record OcorrenciaRecordDto(@NotBlank String estacao, @NotBlank String clima, @NotBlank String descricao, Long fazendaid, Long doencaid) {

}
