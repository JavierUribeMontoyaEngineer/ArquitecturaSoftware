package servicio.controlador;
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ManejadorProgramas extends DefaultHandler {
	private Stack<ProgramaResultado> pilaProgramasProvisional = new Stack<ProgramaResultado>();
	private ArrayList<ProgramaResultado> listaProgramas = new ArrayList<ProgramaResultado>();
	private Stack<String> elementStack = new Stack<String>();
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.elementStack.push(qName);
		ProgramaResultado programa;
		/*El objeto programa se compone de nombre(<h3>) e id(<a>) asi que se
		crea cuando se ve un "<h3>" se anade a la pila y cuando se ve "<a>"
		se modifica el contenido de la pila con peek para a�adir el id */
		if ("h3".equals(qName)) {
			programa = new ProgramaResultado();
			this.pilaProgramasProvisional.push(programa);
		}else if("a".equals(qName)){
			programa = this.pilaProgramasProvisional.peek();
			programa.setId(obtenerId(attributes.getValue("href")));
		}
	}

	private String obtenerId(String href) {
		String[] parts = href.split("/");
		return parts[4];
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Sacamos el elemento actual de la pila de elementos
		this.elementStack.pop();
		//El programa esta terminado porque el id se ha sacado ya asi que se a�ade
		//a la lista definitiva de programas
		if ("a".equals(qName)) {
			ProgramaResultado programa = this.pilaProgramasProvisional.pop();
			this.listaProgramas.add(programa);
		} 	
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch, start, length).trim();

		// No tenemos en cuenta los espacios
		if (value.length() == 0) {
			return;
		}

		//El contenido de h3 se a�ade al nombre del programa
		if ("h3".equals(currentElement())) {
			ProgramaResultado programa = (ProgramaResultado) this.pilaProgramasProvisional.peek();
			programa.setNombre(value);
		}  

	}
	
	private String currentElement() {
		return this.elementStack.peek();
	}
	
	public ArrayList<ProgramaResultado> getListaProgramas() {
		return listaProgramas;
	}

}
