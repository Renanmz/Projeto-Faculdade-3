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

import com.api.Agro.dtos.DoencaRecordDto;

import com.api.Agro.model.DoencaModel;
import com.api.Agro.repository.DoencaRepository;

import jakarta.validation.Valid;

@RestController
public class DoencaController {

	@Autowired 
	DoencaRepository doencarepository;
	
	@GetMapping(value = "/doencas")
	public ResponseEntity<List<DoencaModel>> getDoencas() {
		List<DoencaModel> doencas = doencarepository.findAll();
		if(doencas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(doencas);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(doencas);
		}
	}
	
	@GetMapping(value = "/doenca/{id}")
	public ResponseEntity<Object> getDoencaId(@PathVariable("id") long id) {
		Optional<DoencaModel> agroModelOptional = doencarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doenca not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}
	
	@PostMapping(value = "/newdoenca")
	public ResponseEntity<Object> saveDoenca(@RequestBody @Valid DoencaRecordDto agrorecorddto) {
		var doencaModel = new DoencaModel();
		BeanUtils.copyProperties(agrorecorddto, doencaModel);
		doencaModel.setData(LocalDate.now(ZoneId.of("UTC")));
	return ResponseEntity.status(HttpStatus.CREATED).body(doencarepository.save(doencaModel));
	}
	
	@DeleteMapping(value = "/deletedoenca/{id}")
	public ResponseEntity<Object> deleteDoenca(@PathVariable("id") long id) {
		Optional<DoencaModel> agroModelOptional = doencarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doenca not found");
		}
		doencarepository.delete(agroModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}
	
	@PutMapping(value = "/updatedoenca/{id}")
	public ResponseEntity<Object> updateDoenca(@PathVariable("id") long id, @RequestBody @Valid DoencaRecordDto agrorecorddto) {
	    Optional<DoencaModel> agroModelOptional = doencarepository.findById(id);
	    
	    if (!agroModelOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doenca not found");
	    }
	    DoencaModel doencaToUpdate = agroModelOptional.get();
	    BeanUtils.copyProperties(agrorecorddto, doencaToUpdate, "id", "data");
	    doencaToUpdate.setData(doencaToUpdate.getData());
		
	    return ResponseEntity.status(HttpStatus.OK).body(doencarepository.save(doencaToUpdate));
	}
}
