package com.krakedev.inventarios.entidades;

import java.util.Date;

public class CabeceraPedido {
	
	private int numero;
    private String proveedor;
    private Date fecha;
    private String estado;
	
    
    
    @Override
	public String toString() {
		return "CabeceraPedido [numero=" + numero + ", proveedor=" + proveedor + ", fecha=" + fecha + ", estado="
				+ estado + "]";
	}
	public CabeceraPedido() {
		super();
	}
	public CabeceraPedido(int numero, String proveedor, Date fecha, String estado) {
		super();
		this.numero = numero;
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.estado = estado;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    


}
