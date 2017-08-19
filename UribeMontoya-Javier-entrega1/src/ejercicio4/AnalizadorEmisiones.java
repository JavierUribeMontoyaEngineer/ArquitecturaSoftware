package ejercicio4;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class AnalizadorEmisiones {

	public static void main(String[] args) throws XMLStreamException, ParserConfigurationException, SAXException, IOException, ParseException {
		System.out.println("**EJERCICIO 1.4**");
		final String urlListado = "http://www.rtve.es/m/alacarta/programsbychannel/?media=tve&channel=la1&modl=canales&filterFindPrograms=todas";

		final String id = "acacias-38";

		final String ficheroResultado = "xml/acacias-38-procesado.xml";

		// DOM: Obtener la factoria y el analizador (builder)
		DocumentBuilderFactory factoriaDOM = DocumentBuilderFactory.newInstance();
		DocumentBuilder analizadorDOM = factoriaDOM.newDocumentBuilder();
		Document documento = analizadorDOM.parse(urlListado);

		// StAX: Obtener un writer para generar el fichero resultado
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = xof.createXMLStreamWriter(new FileOutputStream(ficheroResultado));

		// StAX: escribir la cabecera del documento: elemento raiz, espacios de
		// nombres, enlace con esquema, etc.
		writer.writeStartDocument();
		writer.writeStartElement("programa");
		writer.writeNamespace("", "http://www.um.es/programasRTVE");
		writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

		writer.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation",
				"http://www.um.es/programasRTVE schema/programa.xsd");

		/*** Parte 1: analizar el documento de listado de programas ***/
		NodeList etiquetasNombre = documento.getElementsByTagName("h3");
		NodeList etiquetasA = documento.getElementsByTagName("a");
		NodeList etiquetasImg = documento.getElementsByTagName("img");

		for (int i = 0; i < etiquetasNombre.getLength(); i++) {
			Element etiquetaA = (Element) etiquetasA.item(i);
			String urlString = etiquetaA.getAttribute("href");
			String[] parts = urlString.split("/");
			String idPrograma = parts[4];
			if (idPrograma.equals(id)) {
				Element nombre = (Element) etiquetasNombre.item(i);
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

		/*** Parte 2: analizar el documento con las emisiones del programa ***/

		// Parche: a�adir un elemento raiz al documento (se adjunta ayuda en el
		// enunciado)

		// Esta URL hay que componerla en funcion del identificador
		// A titulo de ejemplo, se utiliza acacias-38
		// Solo sacaremos las emisiones del programa acacias-38
		String urlEmisiones = "http://www.rtve.es/m/alacarta/videos/" + id
				+ "/multimedialist_pag.shtml/?media=tve&programName=" + id + "&media=tve&paginaBusqueda=1";
		// Abrimos la conexion con la URL indicando que la codificacion de
		// caracteres es UTF-8
		URL url = new URL(urlEmisiones);
		InputStream is = url.openConnection().getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");

		Scanner scanner = new Scanner(isr);

		// Utilizamos StringWriter para componer en memoria el documento XML
		// bien formato
		StringWriter stringWriter = new StringWriter();

		stringWriter.append("<emisiones>");
		while (scanner.hasNextLine()) {
			stringWriter.append(scanner.nextLine());
		}
		stringWriter.append("</emisiones>");
		stringWriter.close();
		scanner.close();

		// Abrimos un flujo de lectura al documento en memoria
		StringReader reader = new StringReader(stringWriter.toString());

		// El objeto reader puede ser el parametro del metodo build del
		// analizador DOM

		DocumentBuilder builder = factoriaDOM.newDocumentBuilder();

		Document documentoEmisiones = builder.parse(new InputSource(reader));

		// DOM: obtener el arbol DOM (del documento modificado en memoria)

		// DOM: para cada emision:

		// DOM: extraer la informacion: titulo, fecha, tiempo y url
		NodeList etiquetasH4 = documentoEmisiones.getElementsByTagName("h4");
		NodeList etiquetasP = documentoEmisiones.getElementsByTagName("p");
		etiquetasA = documentoEmisiones.getElementsByTagName("a");

		for (int i = 0; i < etiquetasP.getLength(); i++) {
			Element etiquetaH4 = (Element) etiquetasH4.item(i);
			Element etiquetaP = (Element) etiquetasP.item(i);
			Element etiquetaA = (Element) etiquetasA.item(i);
			
			// StAX: escribir cada emision en el documento XML
			writer.writeStartElement("emision");
			
			String fechaDuracion = etiquetaP.getTextContent();
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

		// StAX: finalizar elemenento raiz y documento
		writer.writeEndElement();
		// StAX: cerrar el flujo de escritura
		writer.writeEndDocument();
		writer.flush();
		writer.close();
		System.out.println("Ver "+ficheroResultado);

	}

}
