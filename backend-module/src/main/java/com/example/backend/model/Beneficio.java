package com.example.backend.model;

import java.math.BigDecimal;

import com.example.backend.request.BeneficioRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "beneficio", schema = "public")
public class Beneficio {
	
	@Id
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "descricao", length = 255)
	private String descricao;
	
	@Column(name = "valor", nullable = false, precision = 15, scale = 2)
	private BigDecimal valor;
	
	@Column(name = "ativo", columnDefinition = "boolean default true")
	private Boolean ativo;
	
	@Column(name = "version", columnDefinition = "bigint default 0")
	private Long version;

	public Beneficio() {
		
	}
	
	public Beneficio(BeneficioRequest request) {
		this.id = request.getId();
		this.nome = request.getNome();
		this.descricao = request.getDescricao();
		this.valor = request.getValor();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
