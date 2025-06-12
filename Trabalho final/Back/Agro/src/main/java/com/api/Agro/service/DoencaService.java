package com.api.Agro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.Agro.model.DoencaModel;
import com.api.Agro.repository.DoencaRepository;

@Service
public class DoencaService {

	@Autowired 
	DoencaRepository doencarepository;
	
	public List<DoencaModel> findAll() {

		return doencarepository.findAll();
	}

	public Optional<DoencaModel> findById(long id) {

		return doencarepository.findById(id); 
	}

	@Transactional 
	public DoencaModel save(DoencaModel doenca) {

		return doencarepository.save(doenca);
	}

	@Transactional
	public void delete(DoencaModel doenca) {
		doencarepository.delete(doenca);		
	}
	
	
	
	
	
}
