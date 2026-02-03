package com.krakedev.inventarios.entidades;

public class TipoDocumento {
	
	private String coodigo;
	private String descripcion;
	
	@Override
	public String toString() {
		return "TipoDocumento [coodigo=" + coodigo + ", descripcion=" + descripcion + "]";
	}
	public TipoDocumento() {
		super();
	}
	public TipoDocumento(String coodigo, String descripcion) {
		super();
		this.coodigo = coodigo;
		this.descripcion = descripcion;
	}
	public String getCoodigo() {
		return coodigo;
	}
	public void setCoodigo(String coodigo) {
		this.coodigo = coodigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
