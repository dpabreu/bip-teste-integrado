package com.example.backend.service;

import java.math.BigDecimal;
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
		List<Beneficio> beneficios = beneficioRepository.findByAtivoTrueOrderByNome();
		
		beneficios.forEach(beneficio -> response.add(new BeneficioResponse(beneficio)));
		
		return response;
	}
	
	public BeneficioResponse readById(Long id) {
		return new BeneficioResponse(beneficioRepository.findById(id).get());
	}
	
	@Transactional
	public Response create(BeneficioRequest request) throws Exception {
		
		try {
			Beneficio beneficio = new Beneficio(request.getId(), request.getNome(),
					request.getDescricao(), 
					new BigDecimal(request.getValor().replace(",", ".")));
			beneficioRepository.save(beneficio);
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
			record.setValor(new BigDecimal(request.getValor().replace(",", ".")));
			record.setAtivo(true);
			record.setVersion(0L);
			
			beneficioRepository.save(record);
		} catch (Exception e) {
			throw new Exception("Erro ao atualizar registro: " + e);
		}
		
		return new Response(1, "Registro atualizado com sucesso.");
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
	
	@Transactional
    public Response transfer(BeneficioRequest request) throws Exception {
        Beneficio from = beneficioRepository.findById(request.getFromId())
                .orElseThrow(() -> new IllegalArgumentException("Benefício origem não encontrado"));

        Beneficio to = beneficioRepository.findById(request.getToId())
                .orElseThrow(() -> new IllegalArgumentException("Benefício destino não encontrado"));

        BigDecimal amount = new BigDecimal(request.getQuantia().replace(",", "."));
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
        	throw new IllegalArgumentException("Valor da transferência inválido.");
        }
        
        if(from.getValor().compareTo(amount) < 0) {
        	throw new IllegalArgumentException("Saldo insuficiente.");
        }
        
        try {
	        from.setValor(from.getValor().subtract(amount));
	        to.setValor(to.getValor().add(amount));
	
	        beneficioRepository.save(from);
	        beneficioRepository.save(to);
        } catch (Exception e) {
        	throw new Exception("Erro ao transferir benefício: " + e);
        }
        
        return new Response(1, "Transferência realizada com sucesso.");
    }	
}
