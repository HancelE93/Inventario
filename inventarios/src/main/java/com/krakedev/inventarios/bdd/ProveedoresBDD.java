package com.krakedev.inventarios.bdd;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;



public class ProveedoresBDD {
	public ArrayList<Proveedor> buscar(String subcadena) throws KrakeDevException{
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor> ();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		Proveedor proveedor= null;
		try {
			con =ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select prov.cedula_p, prov.tipo_documento,td.descripcion ,prov.nombre, prov.telefono, prov.correo, prov.direccion "
					+ "from proveedores prov,tipo_documento td "
					+ "where prov.tipo_documento=td.codigo_tp "
					+ "and upper(nombre) like ? ");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String cedula_p=rs.getString("cedula_p");
				String codigoTipoDocumento=rs.getString("tipo_documento");
				String descripcionTipoDocumento=rs.getString("descripcion");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");
				TipoDocumento td= new TipoDocumento(codigoTipoDocumento,descripcionTipoDocumento);
				
				proveedor = new Proveedor(cedula_p,td,nombre,telefono,correo,direccion);
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
	
	public void insertar(Proveedor proveedor) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps=null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into proveedores (cedula_p, tipo_documento ,nombre, telefono, correo, direccion) values(?, ?, ?, ?, ?, ?)");
			ps.setString(1, proveedor.getIdentificador());
			ps.setString(2, proveedor.getTipoDocumento().getCoodigo());
			ps.setString(3,proveedor.getNombre());
			ps.setString(4,proveedor.getTelefono());
			ps.setString(5,proveedor.getCorreo());
			ps.setString(6,proveedor.getDireccion());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertat el cliente. Detalle: "+e.getMessage());
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;

		}finally {
			if (con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
	
	
	public Proveedor buscarPorIdentificador(String identificador) throws KrakeDevException {
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Proveedor proveedor = null;

	    try {
	        con = ConexionBDD.obtenerConexion();
	        String sql = "SELECT prov.cedula_p, prov.tipo_documento, td.descripcion, prov.nombre, prov.telefono, prov.correo, prov.direccion " +
	                     "FROM proveedores prov, tipo_documento td " +
	                     "WHERE prov.tipo_documento = td.codigo_tp " +
	                     "AND prov.cedula_p = ?";   // buscar por identificador exacto

	        ps = con.prepareStatement(sql);
	        ps.setString(1, identificador);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            String cedula_p = rs.getString("cedula_p");
	            String codigoTipoDocumento = rs.getString("tipo_documento");
	            String descripcionTipoDocumento = rs.getString("descripcion");
	            String nombre = rs.getString("nombre");
	            String telefono = rs.getString("telefono");
	            String correo = rs.getString("correo");
	            String direccion = rs.getString("direccion");
	            TipoDocumento td = new TipoDocumento(codigoTipoDocumento, descripcionTipoDocumento);

	            proveedor = new Proveedor(cedula_p, td, nombre, telefono, correo, direccion);
	        }

	    } catch (KrakeDevException e) {
	        e.printStackTrace();
	        throw e;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeDevException("Error al consultar proveedor: " + e.getMessage());
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return proveedor;
	}
	
}
