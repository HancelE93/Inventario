package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("proveedores")
public class ServiciosProveedores {
	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub")String subcadena){
		ProveedoresBDD provBDD= new ProveedoresBDD();
		ArrayList<Proveedor>proveedores=null;
		try {
			proveedores = provBDD.buscar(subcadena);
			return Response.ok(proveedores).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear (Proveedor proveedor) {
		ProveedoresBDD provBDD= new ProveedoresBDD();
		try {
			provBDD.insertar(proveedor);;
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	 // GET para buscar un proveedor por identificador
    @Path("buscarP/{idProveedor}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProveedor(@PathParam("idProveedor") String idProveedor) {
        ProveedoresBDD provBDD = new ProveedoresBDD();
        try {
            Proveedor proveedor = provBDD.buscarPorIdentificador(idProveedor);
            
            if (proveedor != null) {
                // Si se encuentra, devuelve JSON
                return Response.ok(proveedor).build();
            } else {
                // Si no existe, devuelve 404
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Proveedor no encontrado").build();
            }
        } catch (KrakeDevException e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
	


	
	
