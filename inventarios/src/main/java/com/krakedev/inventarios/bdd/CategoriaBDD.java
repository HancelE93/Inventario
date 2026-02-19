package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriaBDD {

	 public void insertar(Categoria categoria) throws KrakeDevException {
	        Connection con = null;
	        PreparedStatement ps = null;

	        try {
	            con = ConexionBDD.obtenerConexion();

	            ps = con.prepareStatement(
	                "insert into categorias (nombre, categoria_padre) values (?, ?)"
	            );

	            ps.setString(1, categoria.getNombre());

	            if (categoria.getCategoriaPadre() != null) {
	                ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
	            } else {
	                ps.setNull(2, Types.INTEGER);
	            }

	            ps.executeUpdate();

	        } catch (SQLException e) {
	            throw new KrakeDevException("Error al insertar categoria: " + e.getMessage());
	        } finally {
	            if (con != null)
	                try { con.close(); } catch (SQLException e) {}
	        }
	    }

	    public void actualizar(Categoria categoria) throws KrakeDevException {
	        Connection con = null;
	        PreparedStatement ps = null;

	        try {
	            con = ConexionBDD.obtenerConexion();

	            ps = con.prepareStatement(
	                "update categorias set nombre=?, categoria_padre=? where codigo_cat=?"
	            );

	            ps.setString(1, categoria.getNombre());

	            if (categoria.getCategoriaPadre() != null) {
	                ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
	            } else {
	                ps.setNull(2, Types.INTEGER);
	            }

	            ps.setInt(3, categoria.getCodigo());

	            ps.executeUpdate();

	        } catch (SQLException e) {
	            throw new KrakeDevException("Error al actualizar categoria: " + e.getMessage());
	        } finally {
	            if (con != null)
	                try { con.close(); } catch (SQLException e) {}
	        }
	    }

	    public ArrayList<Categoria> recuperarTodas() throws KrakeDevException {
	        ArrayList<Categoria> lista = new ArrayList<>();
	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            con = ConexionBDD.obtenerConexion();

	            ps = con.prepareStatement(
	                "select codigo_cat, nombre, categoria_padre from categorias"
	            );

	            rs = ps.executeQuery();

	            while (rs.next()) {
	                Categoria c = new Categoria();
	                c.setCodigo(rs.getInt("codigo_cat"));
	                c.setNombre(rs.getString("nombre"));

	                int codigoPadre = rs.getInt("categoria_padre");
	                if (!rs.wasNull()) {
	                    Categoria padre = new Categoria();
	                    padre.setCodigo(codigoPadre);
	                    c.setCategoriaPadre(padre);
	                }

	                lista.add(c);
	            }

	        } catch (SQLException e) {
	            throw new KrakeDevException("Error al recuperar categorias: " + e.getMessage());
	        }

	        return lista;
	    }
	}
