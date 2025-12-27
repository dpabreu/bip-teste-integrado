package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.request.BeneficioRequest;
import com.example.backend.response.BeneficioResponse;
import com.example.backend.response.Response;

@Service
public class BeneficioService {

	@Autowired
	private BeneficioRepository beneficioRepository;
	
	public List<BeneficioResponse> readAll(){
		
		List<BeneficioResponse> response = new ArrayList<BeneficioResponse>();
		List<Beneficio> beneficios = beneficioRepository.findAllAtivoTrueOrderByNome();
		
		beneficios.forEach(beneficio -> response.add(new BeneficioResponse(beneficio)));
		
		return response;
	}
	
	public BeneficioResponse readById(Long id) {
		return new BeneficioResponse(beneficioRepository.findByIdAndAtivo(id));
	}
	
	@Transactional
	public Response create(BeneficioRequest request) throws Exception {
		
		try {
			beneficioRepository.save(new Beneficio(request));
		} catch (Exception e) {
			throw new Exception("Erro ao criar registro: " + e);
		}
		
		return new Response(1, "Registro incluído com sucesso.");
	}
	
	@Transactional
	public Response update(BeneficioRequest request) throws Exception {
		
		try {
			Beneficio record = beneficioRepository.findById(request.getId()).get();
			record.setNome(request.getNome());
			record.setDescricao(request.getDescricao());
			record.setValor(request.getValor());
			
			beneficioRepository.save(record);
		} catch (Exception e) {
			throw new Exception("Erro ao atualizar registro: " + e);
		}
		
		return new Response(1, "Registro atualziado com sucesso.");
	}
	
	@Transactional
	public Response delete(Long id) throws Exception{
		
		try {
			Beneficio record = beneficioRepository.findById(id).get();
			record.setAtivo(false);
			
			beneficioRepository.save(record);
		} catch (Exception e) {
			throw new Exception("Erro ao excluir registro: " + e);
		}
		
		return new Response(1, "Registro excluído com sucesso.");
	}
}
