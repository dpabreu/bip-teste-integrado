package com.example.backend.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.request.BeneficioRequest;
import com.example.backend.response.BeneficioResponse;
import com.example.backend.response.Response;
import com.example.backend.service.BeneficioService;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioService beneficioService;
    
	@GetMapping
    public List<String> list() {
        return Arrays.asList("Beneficio A", "Beneficio B");
    }
	
	@CrossOrigin(origins="*")
	@PostMapping("/listaBeneficios")
	public ResponseEntity<List<BeneficioResponse>> readAll(){
		List<BeneficioResponse> responseList = beneficioService.readAll();
		return ResponseEntity.ok().body(responseList);
	}
		
	@CrossOrigin(origins="*")
	@PostMapping(value="/criaBeneficio", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> create(@RequestBody BeneficioRequest requisicao){
		Response response;
		try {
			response = beneficioService.create(requisicao);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response = new Response(0, e.getMessage());
			return ResponseEntity.ok().body(response);
		}
	}	
	
	@CrossOrigin(origins="*")
	@PostMapping( value="/atualizaBeneficio", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update(@RequestBody BeneficioRequest request){        		
		Response response;
		try {
			response = beneficioService.update(request);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response = new Response(0, e.getMessage());
			return ResponseEntity.ok().body(response);
		} 
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/excluirBeneficio/{id}")
	public ResponseEntity<Response> delete(@PathVariable Long id){        		
		Response response;
		try {
			response = beneficioService.delete(id);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response = new Response(0, e.getMessage());
			return ResponseEntity.ok().body(response);
		}
	}	
}
