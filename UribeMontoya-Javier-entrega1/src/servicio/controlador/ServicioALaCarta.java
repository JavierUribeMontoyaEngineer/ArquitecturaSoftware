package servicio.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import amazon.ServicioClienteAmazon;
import ejercicio4.Utils;
import servicio.tipos.Programa;
import servicio.tipos.Programa.Emision;

public class ServicioALaCarta {
	private static final String RUTA_PROGRAMAS = "http://www.rtve.es/m/alacarta/programsbychannel/?media=tve&channel=la1&modl=canales&filterFindPrograms=todas";
	public static final String RUTA_BD = "xml-bd/";

	private DocumentBuilderFactory factoriaDOM;
	private DocumentBuilder analizadorDOM;
	private Document documentoPrograma;
	private XPathFactory factoriaXPath;
	private XPath xpath;
	static {
		File folder = new File("xml-bd");
		if (!folder.exists())
			folder.mkdir();
	}

	public ServicioALaCarta() throws ParserConfigurationException, SAXException, IOException {
		factoriaDOM = DocumentBuilderFactory.newInstance();
		analizadorDOM = factoriaDOM.newDocumentBuilder();
		documentoPrograma = analizadorDOM.parse(RUTA_PROGRAMAS);
		factoriaXPath = XPathFactory.newInstance();
		xpath = factoriaXPath.newXPath();
	}

	public List<ProgramaResultado> getListadoProgramas() {
		try {
			SAXParserFactory factoriaSAX = SAXParserFactory.newInstance();
			SAXParser analizadorSAX;
			analizadorSAX = factoriaSAX.newSAXParser();
			ManejadorProgramas manejador = (ManejadorProgramas) new ManejadorProgramas();
			analizadorSAX.parse(RUTA_PROGRAMAS, manejador);
			return manejador.getListaProgramas();

		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ProgramasException("Error al parsear con SAX el listado de programas");
		}

	}

	public Programa getPrograma(String id) {
		try {
			Programa programa = null;
			File ficheroResultado = null;
			ficheroResultado = recuperarPrograma(id);
			// Analizamos el programa del xml generado antes y construimos el
			// objeto programa
			JAXBContext contexto;
			contexto = JAXBContext.newInstance("servicio.tipos");
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			programa = (Programa) unmarshaller.unmarshal(ficheroResultado);
			return programa;
		} catch (SAXException e1) {
			System.out.println("ERROR SAX: ID erroneo, no se ha podido parsear el fichero");
			return null;
		} catch (JAXBException e) {
			throw new ProgramasException("ERROR JAXB unmarshall: XML de origen " + id
					+ ".xml mal formado, imposible convertir a objetos Java");
		} catch (Exception e) {
			throw new ProgramasException("Error al procesar el programa con id " + id);
		}
	}

	private File recuperarPrograma(String id) throws Exception {
		String rutaPrograma = RUTA_BD + id + ".xml";
		File programa = new File(rutaPrograma);
		if (programa.exists()) {
			if (estaActualizado(programa)) {
				System.out.println("XML existe y esta actualizado-> no hacer nada");
			} else {
				System.out.println("Desactualizado->generando xml actualizado...");
				generarXML(id, rutaPrograma);
			}
		} else {
			System.out.println("No existe el xml->generando xml en " + programa.getAbsolutePath() + " ...");
			generarXML(id, rutaPrograma);
		}
		return programa;
	}

	private boolean estaActualizado(File programa) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		long fechaAyer = calendario.getTimeInMillis();
		return programa.lastModified() >= fechaAyer;
	}

	private NodeList evaluarConsulta(String cons, Document documento) throws XPathExpressionException {
		XPathExpression consulta = xpath.compile(cons);
		return (NodeList) consulta.evaluate(documento, XPathConstants.NODESET);
	}

	// Contiene el ejercicio 1.4: analiza el documento programasWeb.xml para
	// escribir id del programa,url y urlImagen
	// y analiza la url de la web de emisiones y las escribe. Todo esto se
	// genera en xml-bd/idPrograma.xml
	private void generarXML(String id, String ficheroResultado) throws Exception {

		// Se analiza programasWeb.xml para escribir
		// nombrePrograma,urlPrograma,id y urlImagen
		XMLOutputFactory xof = null;
		XMLStreamWriter writer = null;
		NodeList etiquetasNombre = documentoPrograma.getElementsByTagName("h3");
		NodeList etiquetasA = documentoPrograma.getElementsByTagName("a");
		NodeList etiquetasImg = documentoPrograma.getElementsByTagName("img");
		Element nombre = null;
		// Recorremos todos los programas y buscamos el que tenga el id
		// deseado
		for (int i = 0; i < etiquetasNombre.getLength(); i++) {
			Element etiquetaA = (Element) etiquetasA.item(i);
			String urlString = etiquetaA.getAttribute("href");
			String[] parts = urlString.split("/");
			String idPrograma = parts[4];
			if (idPrograma.equals(id)) {
				xof = XMLOutputFactory.newInstance();
				writer = xof.createXMLStreamWriter(new FileOutputStream(ficheroResultado), "UTF-8");
				writer.writeStartDocument();
				writer.writeStartElement("programa");
				writer.writeNamespace("", "http://www.um.es/programasRTVE");
				writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
				writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
						"http://www.um.es/programasRTVE ../xml/schema/programa.xsd");
				nombre = (Element) etiquetasNombre.item(i);
				Element etiquetaImg = (Element) etiquetasImg.item(i);
				String urlPrograma = "http://www.rtve.es" + urlString;
				String urlImagen = etiquetaImg.getAttribute("src");

				writer.writeAttribute("id", idPrograma);
				writer.writeAttribute("urlPrograma", urlPrograma);
				writer.writeAttribute("urlImagen", urlImagen);
				writer.writeStartElement("nombre");
				writer.writeCharacters(nombre.getTextContent());
				writer.writeEndElement();
				break;
			}
		}

		// Se analiza el documento de la web para sacar las emisiones y
		// escribirlas
		String urlEmisiones = "http://www.rtve.es/m/alacarta/videos/" + id
				+ "/multimedialist_pag.shtml/?media=tve&programName=" + id + "&media=tve&paginaBusqueda=1";
		URL url = new URL(urlEmisiones);
		InputStream is = url.openConnection().getInputStream();

		InputStreamReader isr = new InputStreamReader(is, "UTF-8");

		Scanner scanner = new Scanner(isr);

		StringWriter stringWriter = new StringWriter();
		stringWriter.append("<emisiones>");
		while (scanner.hasNextLine()) {
			stringWriter.append(scanner.nextLine());
		}

		stringWriter.append("</emisiones>");
		stringWriter.close();
		scanner.close();
		Document documentoEmisiones = null;
		DocumentBuilder builder = null;
		StringReader reader = null;

		reader = new StringReader(stringWriter.toString());
		builder = factoriaDOM.newDocumentBuilder();
		documentoEmisiones = builder.parse(new InputSource(reader));

		// Extraemos la informacion de las emisiones: titulo (h4), fecha (p),
		// tiempo (p) y url (a)
		NodeList etiquetasH4 = documentoEmisiones.getElementsByTagName("h4");
		NodeList etiquetasP = documentoEmisiones.getElementsByTagName("p");

		etiquetasA = documentoEmisiones.getElementsByTagName("a");
		for (int i = 0; i < etiquetasP.getLength(); i++) {
			Element etiquetaH4 = (Element) etiquetasH4.item(i);
			Element etiquetaP = (Element) etiquetasP.item(i);
			Element etiquetaA = (Element) etiquetasA.item(i);
			// Aqui tenemos la fecha y la duracion
			String fechaDuracion = etiquetaP.getTextContent();
			writer.writeStartElement("emision");

			writer.writeAttribute("fecha", Utils.obtenerFechaString(fechaDuracion));
			writer.writeAttribute("tiempo", Utils.obtenerTiempoString(fechaDuracion));

			String urlEmision = "http://www.rtve.es" + etiquetaA.getAttribute("href");
			writer.writeStartElement("titulo");
			writer.writeCharacters(etiquetaH4.getTextContent());
			writer.writeEndElement();
			writer.writeStartElement("url");
			writer.writeCharacters(urlEmision);
			writer.writeEndElement();
			writer.writeEndElement();
		}

		//Se construye la url con la clase ServicioClienteAmazon
		ServicioClienteAmazon servicioAmazon = new ServicioClienteAmazon();
		String urlPeticion = servicioAmazon.getURLPeticion(nombre.getTextContent());
		Document documento = analizadorDOM.parse(urlPeticion);

		NodeList resultadoAsin = evaluarConsulta("//Items/Item/ASIN", documento);
		NodeList resultadoDetalles = evaluarConsulta("//Items/Item/DetailPageURL", documento);
		NodeList resultadoSmallImage = evaluarConsulta("//Items/Item/SmallImage/URL", documento);
		NodeList resultadoLargeImage = evaluarConsulta("//Items/Item/LargeImage/URL", documento);
		NodeList resultadoTitulo = evaluarConsulta("//Items/Item/ItemAttributes/Title", documento);
		NodeList resultadoPrecio = evaluarConsulta("//Items/Item/OfferSummary/LowestNewPrice/FormattedPrice",
				documento);
		for (int i = 0; i < resultadoAsin.getLength() && i < 3; i++) {
			Node nodoAsin = resultadoAsin.item(i);
			Node nodoDetalles = resultadoDetalles.item(i);
			Node nodoSmallImage = resultadoSmallImage.item(i);
			Node nodoLargeImage = resultadoLargeImage.item(i);
			Node nodoTitulo = resultadoTitulo.item(i);
			Node nodoPrecio = resultadoPrecio.item(i);
			String precio = nodoPrecio.getTextContent().split("EUR ")[1].replace(",", ".");
			writer.writeStartElement("productoAmazon");
			writer.writeAttribute("urlDetallesProducto", nodoDetalles.getTextContent());
			writer.writeAttribute("titulo", nodoTitulo.getTextContent());
			writer.writeAttribute("ASIN", nodoAsin.getTextContent());
			writer.writeAttribute("smallImage", nodoSmallImage.getTextContent());
			writer.writeAttribute("largeImage", nodoLargeImage.getTextContent());
			writer.writeAttribute("precioMasBajo", precio);
			writer.writeEndElement();
		}

		writer.writeEndElement();
		writer.writeEndDocument();
		writer.flush();
		writer.close();

	}

	public String getProgramaAtom(String id) {

		try {

			// En primer lugar, intentamos recuperar la plantilla de la raíz del
			// archivo JAR
			// Funcionará si el código está empaquetado en un JAR

			URL recurso = getClass().getResource("/plantilla/plantillaAtom.xsl");

			InputStream is = null;

			if (recurso != null) // La ha encontrado en el JAR
				is = recurso.openStream();
			else // Está en el sistema de ficheros
				is = new FileInputStream("xml/plantilla/plantillaAtom.xsl");

			TransformerFactory factoria = TransformerFactory.newInstance();
			Transformer transformador = factoria.newTransformer(new StreamSource(is));

			JAXBContext contexto = JAXBContext.newInstance("servicio.tipos");

			Programa programa = getPrograma(id);

			// Si getPrograma no ha generado problemas
			if (programa != null) {
				Source origen = new JAXBSource(contexto, programa);
				StringWriter documentoDestino = new StringWriter();
				Result destino = new StreamResult(documentoDestino);
				transformador.transform(origen, destino);
				return documentoDestino.toString();
			}
			return null;
		} catch (IOException | JAXBException | TransformerException e) {
			throw new ProgramasException("ERROR getProgramaAtom");
		}

	}

	public ListadoProgramas getListadoProgramasXML() {
		ListadoProgramas listadoProgramas = new ListadoProgramas();
		List<ProgramaResultado> programas = getListadoProgramas();
		listadoProgramas.setProgramasResultado(programas);
		return listadoProgramas;
	}

	public Programa getProgramaFiltrado(String id, String titulo) {
		Programa programa = getPrograma(id);

		// Si getPrograma no ha generado problemas
		if (programa != null) {
			// Se copia el programa en programaFiltrado y se rellena con los
			// mismos datos del programa recuperado pero con las emisiones
			// filtradas
			Programa programaFiltrado = new Programa();
			programaFiltrado.setId(programa.getId());
			programaFiltrado.setNombre(programa.getNombre());
			programaFiltrado.setUrlImagen(programa.getUrlImagen());
			programaFiltrado.setUrlPrograma(programa.getUrlPrograma());
			for (Emision emision : programa.getEmision()) {
				if (emision.getTitulo().contains(titulo)) {
					programaFiltrado.getEmision().add(emision);
				}
			}
			return programaFiltrado;
		}
		return null;
	}

	public String crearFavoritos() {
		try {
			String id = UUID.randomUUID().toString();
			Favoritos favoritos = new Favoritos();
			favoritos.setId(id);
			executeMarshalFavoritos(favoritos, id);
			return id;
		} catch (JAXBException e) {
			throw new ProgramasException("ERROR JAXB unmarshall");
		}
	}

	private void executeMarshalFavoritos(Favoritos favoritos, String id) throws JAXBException {
		JAXBContext contexto = JAXBContext.newInstance(Favoritos.class);
		Marshaller marshaller = contexto.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", true);
		marshaller.marshal(favoritos, new File(RUTA_BD + "favoritos-" + id + ".xml"));
	}

	private boolean existeProgramaEnFavoritos(Favoritos favoritos, ProgramaResultado programa) {
		List<ProgramaResultado> listaProgramasFavorito = favoritos.getProgramas();
		for (ProgramaResultado programaResultadoFavorito : listaProgramasFavorito) {
			if (programaResultadoFavorito.getId().equals(programa.getId())) {
				System.out.println("Ya existe el programa" + programa.getNombre() + " en favoritos");
				return true;
			}
		}
		return false;
	}

	public boolean addProgramaFavorito(String idFavoritos, String idPrograma) {
		try {
			Favoritos favoritos = getFavoritos(idFavoritos);
			List<ProgramaResultado> programas = getListadoProgramas();

			// Controlamos que el idFavoritos sea correcto
			if (favoritos != null) {
				for (ProgramaResultado programaResultado : programas) {
					if (programaResultado.getId().equals(idPrograma)) {
						// Si no existe se añade a la lista
						if (!existeProgramaEnFavoritos(favoritos, programaResultado)) {
							favoritos.getProgramas().add(programaResultado);
							executeMarshalFavoritos(favoritos, idFavoritos);
						}
						return true;
					}
				}
				// Controlamos que el idPrograma exista en la bd
				System.out.println("No existe el programa " + idPrograma
						+ ".xml para añadirlo al documento de favoritos " + idFavoritos + ".xml");
			}
			return false;
		} catch (JAXBException e) {
			throw new ProgramasException("ERROR JAXB unmarshall: XML de favoritos origen " + idFavoritos
					+ ".xml no existe o esta mal formado, imposible convertir a objetos Java");
		}

	}

	public boolean removeProgramaFavorito(String idFavoritos, String idPrograma) {

		try {
			Favoritos favoritos = getFavoritos(idFavoritos);
			// Controlamos que el idFavoritos sea correcto
			if (favoritos != null) {
				for (Iterator<ProgramaResultado> it = favoritos.getProgramas().iterator(); it.hasNext();) {
					ProgramaResultado prog = it.next();
					// Comprobamos que exista ese programa en el
					// favorito
					if (prog.getId().equals(idPrograma)) {
						it.remove();
						executeMarshalFavoritos(favoritos, idFavoritos);
						return true;
					}
				}
				System.out.println("El documento de favoritos " + idFavoritos + ".xml no tiene el programa "
						+ idPrograma + ".xml para eliminarlo");
				return false;
			}
			return false;
		} catch (JAXBException e) {
			throw new ProgramasException("ERROR JAXB unmarshall: XML de favoritos origen " + idFavoritos
					+ ".xml no existe o esta mal formado, imposible convertir a objetos Java");
		}

	}

	public Favoritos getFavoritos(String idFavoritos) {
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(Favoritos.class);
		} catch (JAXBException e) {
			throw new ProgramasException("ERROR JAXB getFavoritos");
		}
		try {
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
			Favoritos favoritos = (Favoritos) unmarshaller
					.unmarshal(new File(RUTA_BD + "favoritos-" + idFavoritos + ".xml"));
			return favoritos;
		} catch (JAXBException e) {
			System.out.println("ERROR en getFavoritos, id incorrecto");
			return null;
		}

	}

}
