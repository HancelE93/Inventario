package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProveedoresBDD;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.invetnarios.entidades.TipoDocumento;

@Path("tipodocumento")
public class ServiciosTipoDocumento {

	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerTipo (){
		ProveedoresBDD provBDD= new ProveedoresBDD();
		ArrayList<TipoDocumento>tipoDocumentos=null;
		try {
			tipoDocumentos = provBDD.obtenerTipo();
			return Response.ok(tipoDocumentos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	} 
}

