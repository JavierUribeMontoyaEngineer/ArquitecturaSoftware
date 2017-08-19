package rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.xml.sax.SAXException;

import servicio.controlador.Favoritos;
import servicio.controlador.ProgramasException;
import servicio.controlador.ServicioALaCarta;
import servicio.tipos.Programa;

@Path("programas")
public class ServicioProgramas {
	@Context
	private UriInfo uriInfo;

	private ServicioALaCarta controlador;

	@PostConstruct
	public void init() throws ParserConfigurationException, SAXException, IOException {
		controlador = new ServicioALaCarta();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getListadoProgramas() {
		try {
			return Response.status(Status.OK).entity(controlador.getListadoProgramasXML()).build();
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPrograma(@PathParam("id") String id) {
		try {
			Programa programa = controlador.getPrograma(id);
			if (programa != null)
				return Response.status(Status.OK).entity(programa).build();
			else
				return Response.status(Status.NOT_FOUND).build();
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GET
	@Path("{id}/atom")
	@Produces({ MediaType.APPLICATION_ATOM_XML })
	public Response getProgramaAtom(@PathParam("id") String id) {
		String programa = null;
		try {
			programa = controlador.getProgramaAtom(id);
			if (programa != null)
				return Response.status(Status.OK).entity(programa).build();
			else
				return Response.status(Status.NOT_FOUND).build();

		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Ejemplo de
	// uso:http://localhost:8080/ProyectoRest/rest/programas/alaska-y-segura/filtro?titulo=malditos
	@GET
	@Path("{id}/filtro")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProgramasFiltrados(@PathParam("id") String id, @QueryParam("titulo") String titulo) {
		try {
			Programa programa = controlador.getProgramaFiltrado(id, titulo);
			if (programa != null)
				return Response.status(Status.OK).entity(programa).build();
			else
				return Response.status(Status.NOT_FOUND).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@POST
	@Path("favoritos")
	public Response crearFavoritos() {
		try {
			String idFavoritos = controlador.crearFavoritos();
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(idFavoritos);
			return Response.created(builder.build()).build();
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("favoritos/{idFavoritos}/programa/{idPrograma}")
	public Response addProgramaFavorito(@PathParam("idFavoritos") String idFavoritos,
			@PathParam("idPrograma") String idPrograma) {
		try {
			boolean favoritoAnadido = controlador.addProgramaFavorito(idFavoritos, idPrograma);
			if (favoritoAnadido) {
				return Response.noContent().build();
			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DELETE
	@Path("favoritos/{idFavoritos}/programa/{idPrograma}")
	public Response removeProgramaFavorito(@PathParam("idFavoritos") String idFavoritos,
			@PathParam("idPrograma") String idPrograma) {
		try {
			boolean favoritoEliminado = controlador.removeProgramaFavorito(idFavoritos, idPrograma);
			if (favoritoEliminado) {
				return Response.noContent().build();
			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GET
	@Path("favoritos/{idFavoritos}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getFavoritos(@PathParam("idFavoritos") String idFavoritos) {
		try {
			Favoritos favoritos = controlador.getFavoritos(idFavoritos);

			if (favoritos != null) {
				return Response.status(Response.Status.OK).entity(controlador.getFavoritos(idFavoritos)).build();
			} else {
				return Response.status(Status.BAD_REQUEST).build();
			}
		} catch (ProgramasException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

}
