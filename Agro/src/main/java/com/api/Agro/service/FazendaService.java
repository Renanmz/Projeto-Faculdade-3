package com.api.Agro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Agro.model.FazendaModel;
import com.api.Agro.repository.FazendaRepository;

@Service
public class FazendaService {

	@Autowired 
	FazendaRepository fazendarepository;
	
	public List<FazendaModel> findAll() {

		return fazendarepository.findAll();
	}

	public Optional<FazendaModel> findById(long id) {

		return fazendarepository.findById(id); 
	}

	@Transactional 
	public FazendaModel save(FazendaModel fazenda) {

		return fazendarepository.save(fazenda);
	}

	@Transactional
	public void delete(FazendaModel fazenda) {
		fazendarepository.delete(fazenda);		
	}
}
