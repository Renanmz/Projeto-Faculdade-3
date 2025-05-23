package com.api.Agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.Agro.model.FazendaModel;

public interface FazendaRepository extends JpaRepository<FazendaModel, Long> {
	
}
