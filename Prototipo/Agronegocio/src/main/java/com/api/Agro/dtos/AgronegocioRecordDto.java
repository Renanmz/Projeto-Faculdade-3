package com.api.Agro.dtos;

import jakarta.validation.constraints.NotBlank;

public record AgronegocioRecordDto(@NotBlank String nomefazenda, @NotBlank String cidade, @NotBlank String nome, @NotBlank String tipo, @NotBlank String regiao, @NotBlank String estacao, @NotBlank String clima, @NotBlank String descricao) {

}
