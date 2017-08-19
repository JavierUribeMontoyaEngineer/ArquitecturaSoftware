package ejercicio3;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class AnalizadorMain {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParser analizador = null;
		SAXParserFactory factoria = SAXParserFactory.newInstance();

		analizador = factoria.newSAXParser();

		ManejadorProgramas manejador = (ManejadorProgramas) new ManejadorProgramas();
		analizador.parse("http://www.rtve.es/m/alacarta/programsbychannel/?media=tve&channel=la1&modl=canales&filterFindPrograms=todas", manejador);

		for (Programa programa : manejador.getListaProgramas()) {
			System.out.println(programa);
		}

	}

}
