package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {
	public void insertar(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		ResultSet rsClave=null;
		int codigoCabecera=0;
		
		Date fechaActual= new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
		
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into cabecera_pedido (proveedor, fecha, estado) "
					+ " values(?, ?, ?)",Statement.RETURN_GENERATED_KEYS);//despues de insertar retorna las claves insertadas
			
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3,"S");
			
			
			ps.executeUpdate();

			rsClave=ps.getGeneratedKeys();
			
			if(rsClave.next()) {
				codigoCabecera=rsClave.getInt(1);
			}
			
			ArrayList<DetallePedido> detallesPedidos=pedido.getDetalles();
			DetallePedido det;
			for (int i=0;i<detallesPedidos.size();i++) {
				det = detallesPedidos.get(i);
				psDet=con.prepareStatement("INSERT INTO detalle_pedido "
						+ "(numero_pedido, codigo_producto, cantidad_solicitada, cantidad_recibida, subtotal) "
						+ "VALUES (?, ?, ?, ?, ?);");
			
				psDet.setInt(1, codigoCabecera);	
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				psDet.setInt(4, 0);
				
				BigDecimal pv=det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal =pv.multiply(cantidad);
				
				psDet.setBigDecimal(5, subtotal);
				
				psDet.executeUpdate();
				
			}
			
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
	
	public void recibir(Pedido pedido) throws KrakeDevException {

	    Connection con = null;
	    PreparedStatement psCab = null;
	    PreparedStatement psDet = null;
	    PreparedStatement psHist = null;
	    PreparedStatement psStock = null;

	    Date fechaActual = new Date();
	    Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());

	    try {
	        con = ConexionBDD.obtenerConexion();

	        // 1️⃣ Actualizar cabecera
	        psCab = con.prepareStatement(
	            "UPDATE cabecera_pedido SET estado = 'R' WHERE numero = ?"
	        );

	        psCab.setInt(1, pedido.getCodigo());
	        psCab.executeUpdate();

	        // 2️⃣ Recorrer detalles
	        ArrayList<DetallePedido> detalles = pedido.getDetalles();

	        for (DetallePedido det : detalles) {

	            // 🔹 Actualizar detalle_pedido
	            psDet = con.prepareStatement(
	                "UPDATE detalle_pedido " +
	                "SET cantidad_recibida = ?, subtotal = ? " +
	                "WHERE codigo_dp = ?"
	            );

	            psDet.setInt(1, det.getCantidadRecibida());

	            BigDecimal precio = det.getProducto().getPrecioVenta();
	            BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
	            BigDecimal subtotal = precio.multiply(cantidad);

	            psDet.setBigDecimal(2, subtotal);
	            psDet.setInt(3, det.getCodigo());
	            psDet.executeUpdate();

	            // 🔥 INSERT EN historial_stock
	            psHist = con.prepareStatement(
	                "INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad) " +
	                "VALUES (?, ?, ?, ?)"
	            );

	            psHist.setTimestamp(1, fechaHoraActual);
	            psHist.setString(2, "RECIBO PEDIDO " + pedido.getCodigo());
	            psHist.setInt(3, det.getProducto().getCodigo()); // debe ser codigo_p
	            psHist.setInt(4, det.getCantidadRecibida());

	            psHist.executeUpdate();

	            // 🔥 Actualizar stock del producto
	            psStock = con.prepareStatement(
	                "UPDATE producto SET stock = stock + ? WHERE codigo_p = ?"
	            );

	            psStock.setInt(1, det.getCantidadRecibida());
	            psStock.setInt(2, det.getProducto().getCodigo());

	            psStock.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new KrakeDevException("Error al recibir pedido. Detalle: " + e.getMessage());

	    } finally {
	        try {
	            if (psCab != null) psCab.close();
	            if (psDet != null) psDet.close();
	            if (psHist != null) psHist.close();
	            if (psStock != null) psStock.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public ArrayList<Pedido> buscarPedidosPorProveedor(String idProveedor) throws KrakeDevException {
	    ArrayList<Pedido> pedidos = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement psPedido = null;
	    PreparedStatement psDetalle = null;
	    ResultSet rsPedido = null;
	    ResultSet rsDetalle = null;

	    try {
	        con = ConexionBDD.obtenerConexion();

	        // 1️⃣ Consultar todos los pedidos del proveedor
	        String sqlPedidos = "SELECT numero, proveedor, fecha, estado FROM cabecera_pedido WHERE proveedor = ?";
	        psPedido = con.prepareStatement(sqlPedidos);
	        psPedido.setString(1,idProveedor); // convertir int a string sin valueOf
	        rsPedido = psPedido.executeQuery();

	        while (rsPedido.next()) {
	            Pedido pedido = new Pedido();
	            pedido.setCodigo(rsPedido.getInt("numero"));
	            pedido.setFecha(rsPedido.getDate("fecha"));

	            // 🔹 Mapear estado usando tu clase EstadoPedido
	            EstadoPedido estado = new EstadoPedido();
	            String codigoEstado = rsPedido.getString("estado"); // 'S' o 'R'
	            estado.setCodigo_ep(codigoEstado);

	            // Asignar descripción según código
	            if ("S".equals(codigoEstado)) {
	                estado.setDescripcion("SOLICITADO");
	            } else if ("R".equals(codigoEstado)) {
	                estado.setDescripcion("RECIBIDO");
	            } else {
	                estado.setDescripcion("DESCONOCIDO"); // opcional
	            }

	            pedido.setEstado(estado);

	            // 🔹 Configurar proveedor
	            Proveedor proveedor = new Proveedor();
	            proveedor.setIdentificador(rsPedido.getString("proveedor")); // string según tu DB
	            pedido.setProveedor(proveedor);

	            // 2️⃣ Consultar detalles del pedido
	            String sqlDetalles = "SELECT codigo_dp, codigo_producto, cantidad_solicitada, cantidad_recibida, subtotal "
	                               + "FROM detalle_pedido WHERE numero_pedido = ?";
	            psDetalle = con.prepareStatement(sqlDetalles);
	            psDetalle.setInt(1, pedido.getCodigo());
	            rsDetalle = psDetalle.executeQuery();

	            ArrayList<DetallePedido> detalles = new ArrayList<>();
	            while (rsDetalle.next()) {
	                DetallePedido detalle = new DetallePedido();
	                detalle.setCodigo(rsDetalle.getInt("codigo_dp"));
	                detalle.setCantidadSolicitada(rsDetalle.getInt("cantidad_solicitada"));
	                detalle.setCantidadRecibida(rsDetalle.getInt("cantidad_recibida"));
	                detalle.setSubtotal(rsDetalle.getBigDecimal("subtotal"));

	                // Producto asociado
	                Producto producto = new Producto();
	                producto.setCodigo(rsDetalle.getInt("codigo_producto"));
	                detalle.setProducto(producto);

	              
	                detalles.add(detalle);
	            }

	            pedido.setDetalles(detalles);

	            if (rsDetalle != null) rsDetalle.close();
	            if (psDetalle != null) psDetalle.close();

	            pedidos.add(pedido);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new KrakeDevException("Error al buscar pedidos por proveedor: " + e.getMessage());
	    } finally {
	        try {
	            if (rsPedido != null) rsPedido.close();
	            if (psPedido != null) psPedido.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return pedidos;
	}
}