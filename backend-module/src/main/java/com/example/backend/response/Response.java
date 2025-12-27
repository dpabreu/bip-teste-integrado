package com.example.backend.response;

public class Response {

	private int codigo;
	private String msg;
	
	public Response() {
		
	}
	
	public Response(int codigo, String msg) {
		super();
		this.codigo = codigo;
		this.msg = msg;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
}

