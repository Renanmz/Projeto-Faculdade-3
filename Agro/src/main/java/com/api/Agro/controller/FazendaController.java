package com.api.Agro.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.Agro.dtos.FazendaRecordDto;
import com.api.Agro.model.FazendaModel;
import com.api.Agro.repository.FazendaRepository;

import jakarta.validation.Valid;

@RestController
public class FazendaController {

	@Autowired 
	FazendaRepository fazendarepository;
	
	@GetMapping(value = "/fazendas")
	public ResponseEntity<List<FazendaModel>> getFazendas() {
		List<FazendaModel> fazendas = fazendarepository.findAll();
		if(fazendas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fazendas);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(fazendas);
		}
	}
	
	@GetMapping(value = "/fazenda/{id}")
	public ResponseEntity<Object> getFazendaId(@PathVariable("id") long id) {
		Optional<FazendaModel> agroModelOptional = fazendarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}
	
	@PostMapping(value = "/newfazenda")
	public ResponseEntity<Object> saveFazenda(@RequestBody @Valid FazendaRecordDto agrorecorddto) {
		var fazendaModel = new FazendaModel();
		BeanUtils.copyProperties(agrorecorddto, fazendaModel);
		fazendaModel.setData(LocalDate.now(ZoneId.of("UTC")));
	return ResponseEntity.status(HttpStatus.CREATED).body(fazendarepository.save(fazendaModel));
	}
	
	@DeleteMapping(value = "/deletefazenda/{id}")
	public ResponseEntity<Object> deleteFazenda(@PathVariable("id") long id) {
		Optional<FazendaModel> agroModelOptional = fazendarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda not found");
		}
		fazendarepository.delete(agroModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}
	
	@PutMapping(value = "/updatefazenda/{id}")
	public ResponseEntity<Object> updateFazenda(@PathVariable("id") long id, @RequestBody @Valid FazendaRecordDto agrorecorddto) {
	    Optional<FazendaModel> agroModelOptional = fazendarepository.findById(id);
	    
	    if (!agroModelOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda not found");
	    }
	    FazendaModel fazendaToUpdate = agroModelOptional.get();
	    BeanUtils.copyProperties(agrorecorddto, fazendaToUpdate, "id", "data");
	    fazendaToUpdate.setData(fazendaToUpdate.getData());
		
	    return ResponseEntity.status(HttpStatus.OK).body(fazendarepository.save(fazendaToUpdate));
	}
}
