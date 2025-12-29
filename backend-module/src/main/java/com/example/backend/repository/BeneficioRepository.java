package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.backend.model.Beneficio;

import jakarta.persistence.LockModeType;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

	public List<Beneficio> findByAtivoTrueOrderByNome();
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Optional<Beneficio> findById(Long id);
}
