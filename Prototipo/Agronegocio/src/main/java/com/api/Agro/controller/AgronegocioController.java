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

import com.api.Agro.dtos.AgronegocioRecordDto;
import com.api.Agro.model.AgronegocioPostModel;
import com.api.Agro.repository.AgronegocioPostRepository;

import jakarta.validation.Valid;

@RestController
public class AgronegocioController {
	
	@Autowired // para ser o constructor da classe
	AgronegocioPostRepository agronegociorepository;
	
	@GetMapping(value = "/posts")
	public ResponseEntity<List<AgronegocioPostModel>> getPosts() {
		List<AgronegocioPostModel> posts = agronegociorepository.findAll();
		if(posts.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(posts);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(posts);
		}
	}
	
	@GetMapping(value = "/posts/{id}")
	public ResponseEntity<Object> gePostDetails(@PathVariable("id") long id) {
		Optional<AgronegocioPostModel> blogappModelOptional = agronegociorepository.findById(id);
		
		if (!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Occurence not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(blogappModelOptional.get());
	}
	
	@PostMapping(value = "/newpost")
	public ResponseEntity<Object> savePost(@RequestBody @Valid AgronegocioRecordDto agronegociorecorddto) {
		var postModel = new AgronegocioPostModel();
		BeanUtils.copyProperties(agronegociorecorddto, postModel);
		postModel.setData(LocalDate.now(ZoneId.of("UTC")));
	return ResponseEntity.status(HttpStatus.CREATED).body(agronegociorepository.save(postModel));
	}
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Object> deletePost(@PathVariable(value = "id") long id){
		Optional<AgronegocioPostModel> blogappModelOptional = agronegociorepository.findById(id);
		if (!blogappModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Occurence not found");
		}
		agronegociorepository.delete(blogappModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Occurence deleted successfuly.");
	}
	
	@PutMapping(value = "/posts/{id}")
	public ResponseEntity<Object> updatePost(@PathVariable("id") long id, @RequestBody @Valid AgronegocioRecordDto agronegociorecorddto) {
	    Optional<AgronegocioPostModel> blogappModelOptional = agronegociorepository.findById(id);
	    
	    if (!blogappModelOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
	    }
	    AgronegocioPostModel postToUpdate = blogappModelOptional.get();
	    BeanUtils.copyProperties(agronegociorecorddto, postToUpdate, "id", "data");
	    postToUpdate.setData(postToUpdate.getData());
		
	    return ResponseEntity.status(HttpStatus.OK).body(agronegociorepository.save(postToUpdate));
	}
	

}
