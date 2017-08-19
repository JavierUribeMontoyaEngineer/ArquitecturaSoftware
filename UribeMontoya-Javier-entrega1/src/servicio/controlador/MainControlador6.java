package servicio.controlador;

import java.util.List;
import servicio.tipos.Programa;

public class MainControlador6 {

	public static void main(String[] args) throws Exception {
		ServicioALaCarta servicio = new ServicioALaCarta();
		List<ProgramaResultado> listaProgramas = servicio.getListadoProgramas();
		
		System.out.println("1. Mostrar listado de programas:");
		for (ProgramaResultado prog : listaProgramas) {
			System.out.println("\t" + prog);
		}

		String idPrograma = "tenemos-que-hablar";
		System.out.println("\n2.1 Informacion programa " + idPrograma + ":\n");
		Programa programa = servicio.getPrograma(idPrograma);
		
		if (programa != null) {
			System.out.println("\tId: " + programa.getId());
			System.out.println("\tNombre: " + programa.getNombre());
			System.out.println("\tURL imagen: " + programa.getUrlImagen());
			System.out.println("\tURL programa: " + programa.getUrlPrograma());
			System.out.println("\tLista de emisiones:");
			for (Programa.Emision emision : programa.getEmision()) {
				System.out.println("\t\tTitulo emision:" + emision.getTitulo());
				System.out.println("\t\tFecha emision:" + emision.getFecha());
				System.out.println("\t\tDuracion emision:" + emision.getTiempo());
				System.out.println("\t\tURL emision:" + emision.getUrl() + "\n");
			}
		} 
		
		String idPrograma2 = "acacias-38";
		System.out.println("\n2.2 Informacion programa " + idPrograma2 + ":\n");
		Programa programa2 = servicio.getPrograma(idPrograma2);

		if (programa2 != null) {
			System.out.println("\tId: " + programa2.getId());
			System.out.println("\tNombre: " + programa2.getNombre());
			System.out.println("\tURL imagen: " + programa2.getUrlImagen());
			System.out.println("\tURL programa: " + programa2.getUrlPrograma());
			System.out.println("\tLista de emisiones:");
			for (Programa.Emision emision : programa2.getEmision()) {
				System.out.println("\t\tTitulo emision:" + emision.getTitulo());
				System.out.println("\t\tFecha emision:" + emision.getFecha());
				System.out.println("\t\tDuracion emision:" + emision.getTiempo());
				System.out.println("\t\tURL emision:" + emision.getUrl() + "\n");
			}
		} 
		
		System.out.println("\n3. Plantilla de transformacion Atom al programa " + idPrograma + ":\n");
		System.out.println(servicio.getProgramaAtom("as"));
		

	}

}
