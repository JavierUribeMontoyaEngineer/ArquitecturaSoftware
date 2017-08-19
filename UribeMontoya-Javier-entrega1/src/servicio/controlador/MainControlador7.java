package servicio.controlador;

import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import servicio.tipos.Programa;

public class MainControlador7 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException {
		
		System.out.println("1. getListadoProgramasXML():\n");
		
		ServicioALaCarta servicio = new ServicioALaCarta();
		ListadoProgramas listadoProgramas = servicio.getListadoProgramasXML();
		
		for (ProgramaResultado prog : listadoProgramas.getProgramasResultado()) {
			System.out.println("\t" + prog);
		}
		
		System.out.println("\n2. getProgramaFiltrado():\n");

		String idPrograma = "tenemos-que-hablar";
		
		Programa programa = servicio.getPrograma(idPrograma);
		if (programa != null) {
			System.out.println("\tEmisiones del programa " + idPrograma + " original:\n");
			for (Programa.Emision emision : programa.getEmision()) {
				System.out.println("\t\tTitulo emision:" + emision.getTitulo());
			}
		}
		String clave = "los";
		Programa programaFiltrado = servicio.getProgramaFiltrado(idPrograma, clave);
		if (programaFiltrado != null) {
			System.out.println(
					"\tEmisiones del programa " + programaFiltrado.getNombre() + " filtrados por la clave: " + clave + "\n");
			for (Programa.Emision emision : programaFiltrado.getEmision()) {
				System.out.println("\t\tTitulo emision:" + emision.getTitulo());
			}

		}
		
		String idPrograma2 = "acacias-38";
		Programa programa2 = servicio.getPrograma(idPrograma2);
		
		System.out.println("\n3. crearFavoritos():\n");
		String idFavoritos = servicio.crearFavoritos();
		System.out.println("\tCreado favoritos con id: " + idFavoritos);
		
		System.out.println("\n4. addProgramaFavorito():\n");
		if (programa != null) {
			servicio.addProgramaFavorito(idFavoritos, programa.getId());
			System.out.println("\tAñadido programa " + programa.getId() + " al favorito " + idFavoritos);
		}
		
		if (programa2 != null) {
			servicio.addProgramaFavorito(idFavoritos, programa2.getId());
			System.out.println("\tAñadido programa " + programa2.getId() + " al favorito " + idFavoritos);
		}
		
		if (programa2 != null) {
			System.out.println("\tAñadido programa " + programa2.getId() + " al favorito " + idFavoritos + " (REPETIDO)");
			servicio.addProgramaFavorito(idFavoritos, programa2.getId());
		}
		
		System.out.println("\tLista de programas para el favorito " + idFavoritos + ":\n");
		Favoritos fav1 = servicio.getFavoritos(idFavoritos);
		for (ProgramaResultado prog : fav1.getProgramas()) {
			System.out.println("\t\tId: " + prog.getId());
			System.out.println("\t\tNombre: " + prog.getNombre() + "\n");
		}
		
		System.out.println("\n5. removeProgramaFavorito():\n");
		if (programa2 != null) {
			servicio.removeProgramaFavorito(idFavoritos, programa2.getId());
			System.out.println("\tEliminado programa " + programa2.getId() + " del favorito " + idFavoritos);
		}
		
		System.out.println("\tLista de programas para el favorito " + idFavoritos + ":\n");
		fav1 = servicio.getFavoritos(idFavoritos);
		for (ProgramaResultado prog : fav1.getProgramas()) {
			System.out.println("\t\tId: " + prog.getId());
			System.out.println("\t\tNombre: " + prog.getNombre() + "\n");
		}

	}

}
