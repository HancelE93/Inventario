package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;
import com.krakedev.invetnarios.entidades.Proveedor;
import com.krakedev.invetnarios.entidades.TipoDocumento;



public class ProveedoresBDD {
	public ArrayList<Proveedor> buscar(String subcadena) throws KrakeDevException{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor> ();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		Proveedor proveedor= null;
		try {
			con =ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select cedula_p, tipo_documento ,nombre, telefono, correo, direccion "
					+ "from proveedores "
					+ "where upper(nombre) like ? ");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String cedula_p=rs.getString("cedula_p");
				String tipo_documento=rs.getString("tipo_documento");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");
				
				proveedor = new Proveedor(cedula_p,tipo_documento,nombre,telefono,correo,direccion);
				proveedores.add(proveedor);
			}
				
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: "+e.getMessage());
		}
		
		return proveedores;
	}
	
	public ArrayList<TipoDocumento> obtenerTipo() throws KrakeDevException{
		ArrayList<TipoDocumento> tipoDocumentos = new ArrayList<TipoDocumento> ();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		TipoDocumento tipoD= null;
		try {
			con =ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select * from tipo_documento");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String codigo_tp=rs.getString("codigo_tp");
				String descripcion=rs.getString("descripcion");
				
				tipoD = new TipoDocumento(codigo_tp,descripcion);
				tipoDocumentos.add(tipoD);
			}
				
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: "+e.getMessage());
		}
		
		return tipoDocumentos;
	}
	
	
}
