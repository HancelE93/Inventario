package com.krakedev.inventarios.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Venta {

	    private int codigo;
	    private Date fecha;
	    private ArrayList<DetalleVentas> detalles;
	    
	    
	    
		@Override
		public String toString() {
			return "Venta [codigo=" + codigo + ", fecha=" + fecha + ", detalles=" + detalles + "]";
		}
		public Venta() {
			super();
		}
		public Venta(int codigo, Date fecha, ArrayList<DetalleVentas> detalles) {
			super();
			this.codigo = codigo;
			this.fecha = fecha;
			this.detalles = detalles;
		}
		public int getCodigo() {
			return codigo;
		}
		public void setCodigo(int codigo) {
			this.codigo = codigo;
		}
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		public ArrayList<DetalleVentas> getDetalles() {
			return detalles;
		}
		public void setDetalles(ArrayList<DetalleVentas> detalles) {
			this.detalles = detalles;
		}
	    
	    
}

