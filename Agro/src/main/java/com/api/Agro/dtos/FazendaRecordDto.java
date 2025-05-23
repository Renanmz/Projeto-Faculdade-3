package com.api.Agro.dtos;

import jakarta.validation.constraints.NotBlank;

public record FazendaRecordDto(@NotBlank String nomefazenda, @NotBlank String cidade, @NotBlank String regiao) {

}
