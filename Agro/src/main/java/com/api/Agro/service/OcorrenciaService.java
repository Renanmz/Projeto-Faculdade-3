package com.api.Agro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Agro.model.OcorrenciaModel;
import com.api.Agro.repository.OcorrenciaRepository;

@Service
public class OcorrenciaService {

	@Autowired 
	OcorrenciaRepository ocorrenciarepository;
	
	public List<OcorrenciaModel> findAll() {

		return ocorrenciarepository.findAll();
	}

	public Optional<OcorrenciaModel> findById(long id) {

		return ocorrenciarepository.findById(id); 
	}

	@Transactional 
	public OcorrenciaModel save(OcorrenciaModel ocorrencia) {

		return ocorrenciarepository.save(ocorrencia);
	}

	@Transactional
	public void delete(OcorrenciaModel ocorrencia) {
		ocorrenciarepository.delete(ocorrencia);		
	}
}
