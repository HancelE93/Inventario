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

import com.krakedev.inventarios.entidades.DetalleVentas;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class VentasBDD {

	public void guardarVentas(Venta venta) throws KrakeDevException {

		Connection con = null;
		PreparedStatement psCabV = null;
		PreparedStatement psDet = null;
		PreparedStatement psHist = null;
		PreparedStatement psStock = null;
		PreparedStatement psUpdateCab = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();

			// 1️⃣ Actualizar cabecera
			psCabV = con.prepareStatement(
					"INSERT INTO cabecera_ventas (fecha, total_sin_iva, iva, total) " + "VALUES (?, 0, 0, 0)",
					Statement.RETURN_GENERATED_KEYS);

			psCabV.setTimestamp(1, fechaHoraActual);

			psCabV.executeUpdate();
			rsClave = psCabV.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			// 2️⃣ Recorrer detalles
			ArrayList<DetalleVentas> detallesV = venta.getDetalles();

			BigDecimal totalSinIva = BigDecimal.ZERO;
			BigDecimal totalIva = BigDecimal.ZERO;

			for (DetalleVentas detV : detallesV) {

				BigDecimal precio = detV.getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(detV.getCantidad());
				BigDecimal subtotal = precio.multiply(cantidad);

				totalSinIva = totalSinIva.add(subtotal);

				BigDecimal ivaProducto = subtotal.multiply(new BigDecimal("0.12"));
				totalIva = totalIva.add(ivaProducto);

				BigDecimal subtotalConIva = subtotal.add(ivaProducto);

				// 🔹 Actualizar detalle_ventas
				psDet = con.prepareStatement("INSERT INTO detalle_ventas "
						+ "(codigo_cv, codigo_p, cantidad, precio_venta, subtotal, subtotal_iva) "
						+ "VALUES(?, ?, ?, ?, ?, ?)");

				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, detV.getCodigoP());
				psDet.setInt(3, detV.getCantidad());
				psDet.setBigDecimal(4, precio);
				psDet.setBigDecimal(5, subtotal);
				psDet.setBigDecimal(6, subtotalConIva);

				psDet.executeUpdate();

				// 🔥 INSERT historial_stock (cantidad negativa)
				psHist = con
						.prepareStatement("INSERT INTO historial_stock (fecha, referencia, codigo_producto, cantidad) "
								+ "VALUES (?, ?, ?, ?)");

				psHist.setTimestamp(1, fechaHoraActual);
				psHist.setString(2, "VENTA " + codigoCabecera);
				psHist.setInt(3, detV.getCodigoP());
				psHist.setInt(4, detV.getCantidad() * -1);

				psHist.executeUpdate();

				// 🔥 ACTUALIZAR stock (restar)
				psStock = con.prepareStatement("UPDATE producto SET stock = stock - ? WHERE codigo_p = ?");

				psStock.setInt(1, detV.getCantidad());
				psStock.setInt(2, detV.getCodigoP());

				psStock.executeUpdate();
			}

			// 3️⃣ ACTUALIZAR CABECERA CON TOTALES
			BigDecimal totalFinal = totalSinIva.add(totalIva);

			psUpdateCab = con
					.prepareStatement("UPDATE cabecera_ventas SET total_sin_iva=?, iva=?, total=? WHERE codigo_cv=?");

			psUpdateCab.setBigDecimal(1, totalSinIva);
			psUpdateCab.setBigDecimal(2, totalIva);
			psUpdateCab.setBigDecimal(3, totalFinal);
			psUpdateCab.setInt(4, codigoCabecera);

			psUpdateCab.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al guardar venta. Detalle: " + e.getMessage());

		} finally {
			try {
				if (rsClave != null)
					rsClave.close();
				if (psCabV != null)
					psCabV.close();
				if (psDet != null)
					psDet.close();
				if (psHist != null)
					psHist.close();
				if (psStock != null)
					psStock.close();
				if (psUpdateCab != null)
					psUpdateCab.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}