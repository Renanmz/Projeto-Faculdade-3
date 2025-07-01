package com.api.Agro.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.Agro.dtos.OcorrenciaRecordDto;
import com.api.Agro.model.DoencaModel;
import com.api.Agro.model.FazendaModel;
import com.api.Agro.model.OcorrenciaModel;
import com.api.Agro.repository.DoencaRepository;
import com.api.Agro.repository.FazendaRepository;
import com.api.Agro.repository.OcorrenciaRepository;

import jakarta.validation.Valid;

@RestController
public class OcorrenciaController {

	@Autowired 
	OcorrenciaRepository ocorrenciarepository;
	
	@Autowired
	DoencaRepository doencaRepository;

	@Autowired
	FazendaRepository fazendaRepository;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/ocorrencias")
	public ResponseEntity<List<OcorrenciaModel>> getOcorrencias() {
		List<OcorrenciaModel> ocorrencias = ocorrenciarepository.findAll();
		if(ocorrencias.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ocorrencias);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(ocorrencias);
		}
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/ocorrencia/{id}")
	public ResponseEntity<Object> getOcorrenciaId(@PathVariable("id") long id) {
		Optional<OcorrenciaModel> agroModelOptional = ocorrenciarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorrencia not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/newocorrencia")
	public ResponseEntity<Object> saveOcorrencia(@RequestBody @Valid OcorrenciaRecordDto agrorecorddto) {

	    FazendaModel fazenda = fazendaRepository.findById(agrorecorddto.fazendaid())
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fazenda não encontrada"));


	    DoencaModel doenca = doencaRepository.findById(agrorecorddto.doencaid())
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doença não encontrada"));

		var ocorrenciaModel = new OcorrenciaModel();
		BeanUtils.copyProperties(agrorecorddto, ocorrenciaModel);
		ocorrenciaModel.setData(LocalDate.now(ZoneId.of("UTC")));
		ocorrenciaModel.setFazenda(fazenda);
		ocorrenciaModel.setDoenca(doenca);
	return ResponseEntity.status(HttpStatus.CREATED).body(ocorrenciarepository.save(ocorrenciaModel));
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/deleteocorrencia/{id}")
	public ResponseEntity<Object> deleteOcorrencia(@PathVariable("id") long id) {
		Optional<OcorrenciaModel> agroModelOptional = ocorrenciarepository.findById(id);
		
		if (!agroModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorrencia not found");
		}
		ocorrenciarepository.delete(agroModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(agroModelOptional.get());
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = "/updateocorrencia/{id}")
	public ResponseEntity<Object> updateOcorrencia(@PathVariable("id") long id, @RequestBody @Valid OcorrenciaRecordDto agrorecorddto) {
	    Optional<OcorrenciaModel> agroModelOptional = ocorrenciarepository.findById(id);
	    
	    if (!agroModelOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorrencia not found");
	    }
	    OcorrenciaModel ocorrenciaToUpdate = agroModelOptional.get();
	    
	    FazendaModel fazenda = fazendaRepository.findById(agrorecorddto.fazendaid())
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fazenda não encontrada"));


		    DoencaModel doenca = doencaRepository.findById(agrorecorddto.doencaid())
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doença não encontrada"));
		
		    BeanUtils.copyProperties(agrorecorddto, ocorrenciaToUpdate, "id");
	    ocorrenciaToUpdate.setData(LocalDate.now(ZoneId.of("UTC")));
	    ocorrenciaToUpdate.setNumero(agrorecorddto.numero());
	    ocorrenciaToUpdate.setEstacao(agrorecorddto.estacao());
	    ocorrenciaToUpdate.setClima(agrorecorddto.clima());
	    ocorrenciaToUpdate.setStatus(agrorecorddto.status());
	    ocorrenciaToUpdate.setDescricao(agrorecorddto.descricao());
	    ocorrenciaToUpdate.setFazenda(fazenda);
	    ocorrenciaToUpdate.setDoenca(doenca);
	    
	    OcorrenciaModel updated = ocorrenciarepository.save(ocorrenciaToUpdate);
	    return ResponseEntity.status(HttpStatus.OK).body(ocorrenciarepository.save(updated));
	}
}