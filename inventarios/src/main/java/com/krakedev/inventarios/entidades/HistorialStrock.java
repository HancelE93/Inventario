package com.krakedev.inventarios.entidades;

import java.util.Date;

public class HistorialStrock {
	
	private Date fecha;
    private String referencia;
    private int productoFk;
    private int cantidad;
	
    
    
    @Override
	public String toString() {
		return "HistorialStrock [fecha=" + fecha + ", referencia=" + referencia + ", productoFk=" + productoFk
				+ ", cantidad=" + cantidad + "]";
	}
	public HistorialStrock() {
		super();
	}
	public HistorialStrock(Date fecha, String referencia, int productoFk, int cantidad) {
		super();
		this.fecha = fecha;
		this.referencia = referencia;
		this.productoFk = productoFk;
		this.cantidad = cantidad;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public int getProductoFk() {
		return productoFk;
	}
	public void setProductoFk(int productoFk) {
		this.productoFk = productoFk;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

    
    
    
}
