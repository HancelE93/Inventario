package com.krakedev.inventarios.servicios;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.CategoriaBDD;
import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("categorias")
public class ServiciosCategoria {
	
	    @POST
	    @Path("crear")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response crear(Categoria categoria) {
	        CategoriaBDD bdd = new CategoriaBDD();
	        try {
	            bdd.insertar(categoria);
	            return Response.ok().build();
	        } catch (KrakeDevException e) {
	            return Response.serverError().build();
	        }
	    }

	    @PUT
	    @Path("actualizar")
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response actualizar(Categoria categoria) {
	        CategoriaBDD bdd = new CategoriaBDD();
	        try {
	            bdd.actualizar(categoria);
	            return Response.ok().build();
	        } catch (KrakeDevException e) {
	            return Response.serverError().build();
	        }
	    }

	    @GET
	    @Path("todas")
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response recuperar() {
	        CategoriaBDD bdd = new CategoriaBDD();
	        try {
	            ArrayList<Categoria> lista = bdd.recuperarTodas();
	            return Response.ok(lista).build();
	        } catch (KrakeDevException e) {
	            return Response.serverError().build();
	        }
	    }
	}



