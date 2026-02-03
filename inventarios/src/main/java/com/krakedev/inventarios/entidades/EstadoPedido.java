package com.krakedev.inventarios.entidades;

public class EstadoPedido {
	
	public String codigo_ep;
	public String descripcion;
	
	
	
	@Override
	public String toString() {
		return "EstadoPedido [codigo_ep=" + codigo_ep + ", descripcion=" + descripcion + "]";
	}
	public EstadoPedido() {
		super();
	}
	public EstadoPedido(String codigo_ep, String descripcion) {
		super();
		this.codigo_ep = codigo_ep;
		this.descripcion = descripcion;
	}
	public String getCodigo_ep() {
		return codigo_ep;
	}
	public void setCodigo_ep(String codigo_ep) {
		this.codigo_ep = codigo_ep;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
