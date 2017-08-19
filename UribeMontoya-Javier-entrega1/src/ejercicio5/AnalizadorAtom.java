package ejercicio5;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class AnalizadorAtom {

	public static void main(String[] args) throws TransformerException {
		System.out.println("**EJERCICIO 1.5**");
		final String documentoEntrada = "xml/plantilla/acacias-38.xml";
		final String documentoSalida = "xml/plantilla/programaAtom.xml";
		final String transformacion = "xml/plantilla/plantillaAtom.xsl";
		TransformerFactory factoria = TransformerFactory.newInstance();
		Transformer transformador = factoria.newTransformer(new StreamSource(transformacion));
		Source origen = new StreamSource(documentoEntrada);
		Result destino = new StreamResult(documentoSalida);
		transformador.transform(origen, destino);
		System.out.println("Ver programaAtom.xml");

	}

}
