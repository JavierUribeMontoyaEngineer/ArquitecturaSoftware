package amazon;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import servicio.controlador.ServicioALaCarta;
import servicio.tipos.ProductoAmazon;
import servicio.tipos.Programa;

public class ProgramaAmazon {

	public static void main(String[] args)
			throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, SAXException,
			ParserConfigurationException, TransformerException, XPathExpressionException {
		
		ServicioALaCarta servicio = new ServicioALaCarta();

		String idPrograma = "aguila-roja";
		System.out.println("Informacion programa " + idPrograma + ":\n");
		Programa programa = servicio.getPrograma(idPrograma);

		if (programa != null) {
			System.out.println("\tId: " + programa.getId());
			System.out.println("\tNombre: " + programa.getNombre());
			System.out.println("\tURL imagen: " + programa.getUrlImagen());
			System.out.println("\tURL programa: " + programa.getUrlPrograma());
			System.out.println("\tLista de emisiones:");
			for (Programa.Emision emision : programa.getEmision()) {
				System.out.println("\t\tTitulo emision: " + emision.getTitulo());
				System.out.println("\t\tFecha emision: " + emision.getFecha());
				System.out.println("\t\tDuracion emision: " + emision.getTiempo());
				System.out.println("\t\tURL emision: " + emision.getUrl() + "\n");
			}
			System.out.println("\tLista de productos Amazon:");
			for (ProductoAmazon producto : programa.getProductoAmazon()) {
				System.out.println("\t\tASIN: " + producto.getASIN());
				System.out.println("\t\tTitulo: " + producto.getTitulo());
				System.out.println("\t\tURL detalles: " + producto.getUrlDetallesProducto());
				System.out.println("\t\tPrecio: " + producto.getPrecioMasBajo());
				System.out.println("\t\tURL imagen pequeña: " + producto.getSmallImage());
				System.out.println("\t\tURL imagen grande: " + producto.getLargeImage() + "\n\n");
			}
		}

	}

}
