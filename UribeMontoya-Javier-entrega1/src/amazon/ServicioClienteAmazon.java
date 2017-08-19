package amazon;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ServicioClienteAmazon {
	private String associateTag = "arso";

	public String getURLPeticion(String tituloPrograma)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		Map<String, String> parametros = new HashMap<>();
		SignedRequestsHelper firmador = new SignedRequestsHelper();
		parametros.put("Keywords", tituloPrograma);
		parametros.put("AssociateTag", associateTag);
		parametros.put("ResponseGroup", "Medium");

		// Index DVD se refiere a la seccion peliculas y TV
		parametros.put("SearchIndex", "DVD");
		parametros.put("Service", "AWSECommerceService");
		parametros.put("Version", "2013-08-01");
		parametros.put("Operation", "ItemSearch");
		String request = firmador.sign(parametros);
		return request;
	}
}
