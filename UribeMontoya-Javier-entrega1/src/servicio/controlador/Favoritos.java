package servicio.controlador;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "favoritos")
@XmlType(name = "", propOrder = { "id", "programas" })
public class Favoritos {

	private String id;
	private List<ProgramaResultado> programas;

	public Favoritos() {
		programas = new LinkedList<ProgramaResultado>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<ProgramaResultado> getProgramas() {
		return programas;
	}
	
	public void setProgramas(List<ProgramaResultado> programas) {
		this.programas = programas;
	}

}
