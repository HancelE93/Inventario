package com.krakedev.invetnarios.entidades;

public class Categoria {

	private int codigo;
	private String nombre;
	private Categoria CategoriaPadre;
	
	
	
	@Override
	public String toString() {
		return "Categoria [codigo=" + codigo + ", nombre=" + nombre + ", CategoriaPadre=" + CategoriaPadre + "]";
	}
	public Categoria() {
		super();
	}
	public Categoria(int codigo, String nombre, Categoria categoriaPadre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		CategoriaPadre = categoriaPadre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Categoria getCategoriaPadre() {
		return CategoriaPadre;
	}
	public void setCategoriaPadre(Categoria categoriaPadre) {
		CategoriaPadre = categoriaPadre;
	}
	
	
	
	
}
