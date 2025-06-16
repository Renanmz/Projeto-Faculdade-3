package com.api.Agro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.Agro.model.DoencaModel;

public interface DoencaRepository extends JpaRepository<DoencaModel, Long> {
	Optional<DoencaModel> findByNome(String nome);
}
