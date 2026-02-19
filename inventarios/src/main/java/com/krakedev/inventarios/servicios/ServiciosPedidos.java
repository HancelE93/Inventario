package com.krakedev.inventarios.servicios;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("pedidos")
public class ServiciosPedidos {
	
	
	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear (Pedido pedido) {
		PedidosBDD pedBDD= new PedidosBDD();
		try {
			pedBDD.insertar(pedido);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar (Pedido pedido) {
		System.out.println("ACTUALIZADO >>>>>"+pedido);
		PedidosBDD pedBDD= new PedidosBDD();
		try {
			pedBDD.recibir(pedido);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	 
	@Path("proveedor/{idProveedor}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorProveedor(@PathParam("idProveedor") String idProveedor) {
	    PedidosBDD pedBDD = new PedidosBDD();
	    try {
	        // Guardamos la lista de pedidos
	        ArrayList<Pedido> pedidos = pedBDD.buscarPedidosPorProveedor(idProveedor);
	        // Retornamos la lista como JSON
	        return Response.ok(pedidos, MediaType.APPLICATION_JSON).build();
	    } catch (KrakeDevException e) {
	        e.printStackTrace();
	        return Response.serverError().entity(e.getMessage()).build();
	    }
	}
	
	
}