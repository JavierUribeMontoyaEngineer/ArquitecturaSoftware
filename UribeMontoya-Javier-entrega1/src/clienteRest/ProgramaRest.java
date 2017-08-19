package clienteRest;

import javax.ws.rs.core.MediaType;

import servicio.controlador.ListadoProgramas;
import servicio.controlador.ProgramaResultado;
import servicio.tipos.ProductoAmazon;
import servicio.tipos.Programa;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ProgramaRest {

	private static final String URL_SERVICIO = "http://localhost:8080/UribeMontoya-Javier-entrega2/rest/";

	public static void main(String[] args) {

		System.out.println("******* Obtener listado de programas XML *******");
		Client cliente = Client.create();
		String path = "programas";
		WebResource recurso = cliente.resource(URL_SERVICIO + path);
		Builder builder = recurso.accept(MediaType.APPLICATION_XML);
		ClientResponse respuesta = builder.method("GET", ClientResponse.class);
		ListadoProgramas listaProgramas = respuesta.getEntity(ListadoProgramas.class);
		for (ProgramaResultado programa : listaProgramas.getProgramasResultado()) {
			System.out.println(programa.getNombre());
		}

		System.out.println("\n******* Recuperar Acacias 38 XML *******");
		String programaURL = URL_SERVICIO + path + "/acacias-38";
		recurso = cliente.resource(programaURL);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		Programa programaAcacias = respuesta.getEntity(Programa.class);
		System.out.println("Numero emisiones acacias-38: " + programaAcacias.getEmision().size());

		System.out.println("\n******* Recuperar Acacias 38 con filtro 48 *******");
		programaURL = URL_SERVICIO + path + "/acacias-38/filtro";
		recurso = cliente.resource(programaURL).queryParam("titulo", "48");
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		Programa programaFiltrado = respuesta.getEntity(Programa.class);
		System.out.println("Numero emisiones acacias-38 con filtro 48: " + programaFiltrado.getEmision().size());

		System.out.println("\n******* Crear favoritos y registrar acacias-38 y aguila-roja *******");
		programaURL = URL_SERVICIO + path + "/favoritos";
		recurso = cliente.resource(programaURL);
		respuesta = recurso.method("POST", ClientResponse.class);
		String favoritosURL = respuesta.getLocation().toString();
		recurso = cliente.resource(favoritosURL + "/programa/acacias-38");
		respuesta = recurso.method("POST", ClientResponse.class);
		recurso = cliente.resource(favoritosURL + "/programa/aguila-roja");
		respuesta = recurso.method("POST", ClientResponse.class);
		System.out.println("Ver fichero generado en xml-bd");

		System.out.println("\n******* Recuperar favoritos XML *******");
		recurso = cliente.resource(favoritosURL);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		String favoritos = respuesta.getEntity(String.class);
		// Favoritos favoritos = respuesta.getEntity(Favoritos.class);
		System.out.println(favoritos);

		System.out.println("\n******* Borrar Aguila Roja de favoritos *******");
		recurso = cliente.resource(favoritosURL + "/programa/aguila-roja");
		respuesta = recurso.method("DELETE", ClientResponse.class);

		System.out.println("\n******* Recuperar favoritos XML *******");
		recurso = cliente.resource(favoritosURL);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		favoritos = respuesta.getEntity(String.class);
		// Favoritos favoritos = respuesta.getEntity(Favoritos.class);
		System.out.println(favoritos);

		System.out.println("\n******* Obtener listado de programas JSON *******");
		recurso = cliente.resource(URL_SERVICIO + path);
		builder = recurso.accept(MediaType.APPLICATION_JSON);
		respuesta = builder.method("GET", ClientResponse.class);
		String listaProgramasJSON = respuesta.getEntity(String.class);
		System.out.println(listaProgramasJSON);

		System.out.println("\n******* EXTRA: Recuperar Aguila Roja con sus Productos de Amazon *******");
		programaURL = URL_SERVICIO + path + "/aguila-roja";
		recurso = cliente.resource(programaURL);
		builder = recurso.accept(MediaType.APPLICATION_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		Programa programaAguilaRoja = respuesta.getEntity(Programa.class);
		System.out.println("\tId: " + programaAguilaRoja.getId());
		System.out.println("\tNombre: " + programaAguilaRoja.getNombre());
		System.out.println("\tURL imagen: " + programaAguilaRoja.getUrlImagen());
		System.out.println("\tURL programa: " + programaAguilaRoja.getUrlPrograma());
		System.out.println("\tLista de emisiones:");
		for (Programa.Emision emision : programaAguilaRoja.getEmision()) {
			System.out.println("\t\tTitulo emision: " + emision.getTitulo());
			System.out.println("\t\tFecha emision: " + emision.getFecha());
			System.out.println("\t\tDuracion emision: " + emision.getTiempo());
			System.out.println("\t\tURL emision: " + emision.getUrl() + "\n");
		}
		System.out.println("\tLista de productos Amazon:");
		for (ProductoAmazon producto : programaAguilaRoja.getProductoAmazon()) {
			System.out.println("\t\tASIN: " + producto.getASIN());
			System.out.println("\t\tTitulo: " + producto.getTitulo());
			System.out.println("\t\tURL detalles: " + producto.getUrlDetallesProducto());
			System.out.println("\t\tPrecio: " + producto.getPrecioMasBajo());
			System.out.println("\t\tURL imagen pequeña: " + producto.getSmallImage());
			System.out.println("\t\tURL imagen grande: " + producto.getLargeImage() + "\n\n");
		}

		System.out.println("\n******* EXTRA: Recuperar Acacias 38 en formato ATOM *******");
		recurso = cliente.resource(URL_SERVICIO + path + "/acacias-38/atom");
		builder = recurso.accept(MediaType.APPLICATION_ATOM_XML);
		respuesta = builder.method("GET", ClientResponse.class);
		System.out.println("Código de retorno: " + respuesta.getStatusInfo());
		String acaciasATOM = respuesta.getEntity(String.class);
		System.out.println(acaciasATOM);

	}

}
