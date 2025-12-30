package com.example.backend.request;

public class TransferirBeneficioRequest {

	private Long fromId;
	private Long toId;
	private String quantia;
	
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public Long getToId() {
		return toId;
	}
	public void setToId(Long toId) {
		this.toId = toId;
	}
	public String getQuantia() {
		return quantia;
	}
	public void setQuantia(String quantia) {
		this.quantia = quantia;
	}
}
