package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductoBDD {

	
	public ArrayList<Producto> buscar(String subcadena) throws KrakeDevException{
		ArrayList<Producto> productos = new ArrayList<Producto> ();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		Producto producto= null;
		try {
			con =ConexionBDD.obtenerConexion();
			ps=con.prepareStatement("select prod.codigo_p, prod.nombre as nombre_producto, "
					+ "udm.codigo_u_m as nombre_udm, udm.descripcion as descripcion_udm, "
					+ "cast(prod.precio_venta as decimal(6,2)), prod.tiene_iva,cast(prod.costo as decimal(5,4)), "
					+ "prod.codigo_cat, cat.nombre as nombre_categoria, stock "
					+ "from producto prod,unidad_medida udm, categorias cat "
					+ "where prod.codigo_u_m=udm.codigo_u_m "
					+ "and prod.codigo_cat =cat.codigo_cat "
					+ "and upper(prod.nombre) like ? ");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				
				int codigoProducto=rs.getInt("codigo_p");
				String nombreProducto=rs.getString("nombre_producto");
				String nombreUnidadMedida=rs.getString("nombre_udm");
				String descripcionUnidadMedida=rs.getString("descripcion_udm");
				BigDecimal precioVenta=rs.getBigDecimal("precio_venta");
				boolean tieneIVA=rs.getBoolean("tiene_iva");
				BigDecimal costo=rs.getBigDecimal("costo");
				int codigoCategoria=rs.getInt("codigo_cat");
				String nombreCategotia=rs.getString("nombre_categoria");
				int stock=rs.getInt("stock");
				
				UnidadDeMedida udm= new UnidadDeMedida();
				udm.setNombre(nombreUnidadMedida);
				udm.setDescripcion(descripcionUnidadMedida);
				
				Categoria categoria= new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategotia);
				
				producto= new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecioVenta(precioVenta);
				producto.setTieneIva(tieneIVA);
				producto.setCoste(costo);
				producto.setCategoria(categoria);
				producto.setStock(stock);
				
				productos.add(producto);
				
			}
				
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: "+e.getMessage());
		}
		
		return productos;
	}
	
	public void insertar(Producto producto) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps=null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into producto (nombre, codigo_u_m,precio_venta,tiene_iva,costo,codigo_cat,stock) values(?, ?, ?, ?, ?, ?, ?)");

			   ps.setString(1, producto.getNombre());
			   
			   // Unidad de medida → objeto → código
		        ps.setString(2, producto.getUnidadMedida().getNombre());

		        // BigDecimal
		        ps.setBigDecimal(3, producto.getPrecioVenta());

		        // boolean
		        ps.setBoolean(4, producto.isTieneIva());

		        // BigDecimal
		        ps.setBigDecimal(5, producto.getCoste());

		        // Categoría → objeto → código
		        ps.setInt(6, producto.getCategoria().getCodigo());

		        // int
		        ps.setInt(7, producto.getStock());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertat el producto. Detalle: "+e.getMessage());
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
	

}
