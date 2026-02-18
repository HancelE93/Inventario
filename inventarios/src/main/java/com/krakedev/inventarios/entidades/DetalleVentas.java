package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;

public class DetalleVentas {

	private int codigoDv;
    private int codigoCv;
    private int codigoP;
    private int cantidad;
    private BigDecimal precioVenta;
    private BigDecimal subtotal;
    private BigDecimal subtotalIva;
    
    
    
	@Override
	public String toString() {
		return "DetalleVentas [codigoDv=" + codigoDv + ", codigoCv=" + codigoCv + ", codigoP=" + codigoP + ", cantidad="
				+ cantidad + ", precioVenta=" + precioVenta + ", subtotal=" + subtotal + ", subtotalIva=" + subtotalIva
				+ "]";
	}
	public DetalleVentas() {
		super();
	}
	public DetalleVentas(int codigoDv, int codigoCv, int codigoP, int cantidad, BigDecimal precioVenta,
			BigDecimal subtotal, BigDecimal subtotalIva) {
		super();
		this.codigoDv = codigoDv;
		this.codigoCv = codigoCv;
		this.codigoP = codigoP;
		this.cantidad = cantidad;
		this.precioVenta = precioVenta;
		this.subtotal = subtotal;
		this.subtotalIva = subtotalIva;
	}
	public int getCodigoDv() {
		return codigoDv;
	}
	public void setCodigoDv(int codigoDv) {
		this.codigoDv = codigoDv;
	}
	public int getCodigoCv() {
		return codigoCv;
	}
	public void setCodigoCv(int codigoCv) {
		this.codigoCv = codigoCv;
	}
	public int getCodigoP() {
		return codigoP;
	}
	public void setCodigoP(int codigoP) {
		this.codigoP = codigoP;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getSubtotalIva() {
		return subtotalIva;
	}
	public void setSubtotalIva(BigDecimal subtotalIva) {
		this.subtotalIva = subtotalIva;
	}
    
    
}

