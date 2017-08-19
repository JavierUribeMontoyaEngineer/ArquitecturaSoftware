package servicio.controlador;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="listadoProgramas")
public class ListadoProgramas {

	private List<ProgramaResultado> programasResultado;

	public List<ProgramaResultado> getProgramasResultado() {
		if (programasResultado == null)
			programasResultado = new LinkedList<ProgramaResultado>();
		return programasResultado;
	}

	public void setProgramasResultado(List<ProgramaResultado> programasResultado) {
		this.programasResultado = programasResultado;
	}
}
