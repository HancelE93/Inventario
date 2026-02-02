package com.krakedev.invetnarios.entidades;

public class UnidadDeMedida {

	private String codigo;
	private String nombre;
	private String descripcion;
	private CategoriaUDM  categoriaUnidadMedida;
	
	
	
	@Override
	public String toString() {
		return "UnidadDeMedida [codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", categoriaUnidadMedida=" + categoriaUnidadMedida + "]";
	}
	public UnidadDeMedida() {
		super();
	}
	public UnidadDeMedida(String codigo, String nombre, String descripcion, CategoriaUDM categoriaUnidadMedida) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CategoriaUDM getCategoriaUnidadMedida() {
		return categoriaUnidadMedida;
	}
	public void setCategoriaUnidadMedida(CategoriaUDM categoriaUnidadMedida) {
		this.categoriaUnidadMedida = categoriaUnidadMedida;
	}
	
	
	
	
	
	
}
