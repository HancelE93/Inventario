package com.krakedev.invetnarios.entidades;

public class Categoria {

	private int cogido;
	private String nombre;
	private Categoria CategoriaPadre;
	
	@Override
	public String toString() {
		return "Categoria [cogido=" + cogido + ", nombre=" + nombre + ", CategoriaPadre=" + CategoriaPadre + "]";
	}
	public Categoria() {
		super();
	}
	public Categoria(int cogido, String nombre, Categoria categoriaPadre) {
		super();
		this.cogido = cogido;
		this.nombre = nombre;
		CategoriaPadre = categoriaPadre;
	}
	public int getCogido() {
		return cogido;
	}
	public void setCogido(int cogido) {
		this.cogido = cogido;
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
