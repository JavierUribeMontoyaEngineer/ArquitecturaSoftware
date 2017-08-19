package ejercicio3;

public class Programa {

	private String id;
	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String url) {
		this.id = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Programa [id=" + id + ", nombre=" + nombre + "]";
	}

	

}
