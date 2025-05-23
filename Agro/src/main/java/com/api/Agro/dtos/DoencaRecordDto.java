package com.api.Agro.dtos;

import jakarta.validation.constraints.NotBlank;

public record DoencaRecordDto(@NotBlank String nome, @NotBlank String tipo, @NotBlank String descricao) {

}
