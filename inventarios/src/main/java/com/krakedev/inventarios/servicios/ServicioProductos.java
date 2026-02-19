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

import com.krakedev.inventarios.bdd.ProductoBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("productos")
public class ServicioProductos {

	
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub")String subcadena){
		ProductoBDD prodBDD= new ProductoBDD();
		ArrayList<Producto>productos=null;
		try {
			productos = prodBDD.buscar(subcadena);
			return Response.ok(productos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear (Producto producto) {
		ProductoBDD prodBDD= new ProductoBDD();
		try {
			prodBDD.insertar(producto);;
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Producto producto) {
	    ProductoBDD prodBDD = new ProductoBDD();
	    try {
	        prodBDD.actualizar(producto);
	        return Response.ok().build();
	    } catch (KrakeDevException e) {
	        e.printStackTrace();
	        return Response.serverError().build();
	    }
	}
	
	
	 // GET para buscar un producto por identificador
    @Path("buscarProd/{idProducto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProducto(@PathParam("idProducto") int idProducto) {
    	ProductoBDD prodBDD = new ProductoBDD();
        try {
            Producto producto = prodBDD.buscarPorCodigo(idProducto); // método en BDD que busca por código
            
            if (producto != null) {
                return Response.ok(producto).build(); // devuelve JSON
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Producto no encontrado").build();
            }
        } catch (KrakeDevException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
