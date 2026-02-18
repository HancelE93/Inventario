package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.util.Date;

public class CabeceraVentas {
	
	 private int codigoCv;
	    private Date fecha;
	    private BigDecimal totalSinIva;
	    private BigDecimal iva;
	    private BigDecimal total;
		
	    
	    
	    
	    @Override
		public String toString() {
			return "CabeceraVentas [codigoCv=" + codigoCv + ", fecha=" + fecha + ", totalSinIva=" + totalSinIva
					+ ", iva=" + iva + ", total=" + total + "]";
		}
		public CabeceraVentas() {
			super();
		}
		public CabeceraVentas(int codigoCv, Date fecha, BigDecimal totalSinIva, BigDecimal iva, BigDecimal total) {
			super();
			this.codigoCv = codigoCv;
			this.fecha = fecha;
			this.totalSinIva = totalSinIva;
			this.iva = iva;
			this.total = total;
		}
		public int getCodigoCv() {
			return codigoCv;
		}
		public void setCodigoCv(int codigoCv) {
			this.codigoCv = codigoCv;
		}
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
		public BigDecimal getTotalSinIva() {
			return totalSinIva;
		}
		public void setTotalSinIva(BigDecimal totalSinIva) {
			this.totalSinIva = totalSinIva;
		}
		public BigDecimal getIva() {
			return iva;
		}
		public void setIva(BigDecimal iva) {
			this.iva = iva;
		}
		public BigDecimal getTotal() {
			return total;
		}
		public void setTotal(BigDecimal total) {
			this.total = total;
		}

	    
}
