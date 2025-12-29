package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.model.Beneficio;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

	public List<Beneficio> findByAtivoTrueOrderByNome();
}
