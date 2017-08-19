package ejercicio4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

	/* 
	 * El método retorna una fecha en el pasado haciendo referencia al día: pasado lunes, pasado martes, etc.
	 * El parámetro referencia es una constante de Calendar: Calendar.SUNDAY, Calendar.MONDAY, etc.
	*/
	public static Date calcularFecha(int referencia) {

		Calendar calendar = Calendar.getInstance();
		int hoy = calendar.get(Calendar.DAY_OF_WEEK);

		int diferencia = hoy - referencia;

		if (diferencia > 0) {
			calendar.add(Calendar.DATE, -diferencia);
		} else {
			calendar.add(Calendar.DATE, -7 - diferencia);
		}
		return calendar.getTime();
	}

	public static Date ayer() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
	public static Date hoy() {

		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	// Ejemplo de patrón: 30 ene 2017
	public static Date convertirTextoFecha(String fecha) throws ParseException {

		SimpleDateFormat formateador = new SimpleDateFormat("dd MMM yyyy", new Locale("es"));

		return formateador.parse(fecha);
	}

	// Salida en formato XML Schema
	public static String convertirFechaTexto(Date fecha) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		
		return formateador.format(fecha);
	}

	public static String obtenerFechaString(String fechaDuracion) throws ParseException {
		return convertirFechaTexto(getFecha(fechaDuracion));
	}

	public static String obtenerTiempoString(String fechaDuracion) {
		// Creo un LocalTime con la duracion obtenida por getTiempo.
		// MIDNIGHT establece la referencia a las 00:00, y desde ahi sumamos la Duracion con plus.
		LocalTime t = LocalTime.MIDNIGHT.plus(getTiempo(fechaDuracion));
		String tiempoFormateado = DateTimeFormatter.ofPattern("HH:mm:ss").format(t);
		return tiempoFormateado;
	}
	
	private static Date getFecha(String fechaTiempo) throws ParseException {
		Date fecha = null;
		String[] parts = fechaTiempo.split(" - ");
		String trozoFecha = parts[0];
		String tipoFecha = trozoFecha.split(" ")[0];
		if (tipoFecha.equals("pasado")) {
			String dia = trozoFecha.split(" ")[1];

			switch (dia) {
			case "lunes":
				fecha = calcularFecha(Calendar.MONDAY);
				break;
			case "martes":
				fecha = calcularFecha(Calendar.TUESDAY);
				break;
			case "miércoles":
				fecha = calcularFecha(Calendar.WEDNESDAY);
				break;
			case "jueves":
				fecha = calcularFecha(Calendar.THURSDAY);
				break;
			case "viernes":
				fecha = calcularFecha(Calendar.FRIDAY);
				break;
			case "sábado":
				fecha = calcularFecha(Calendar.SATURDAY);
				break;
			case "domingo":
				fecha = calcularFecha(Calendar.SUNDAY);
				break;
			default:
				break;
			}

		} else if (tipoFecha.equals("ayer"))
			fecha = ayer();
		else if (tipoFecha.equals("hoy"))
			fecha = hoy();
		else
			fecha = convertirTextoFecha(trozoFecha);

		return fecha;
	}
	
	private static Duration getTiempo(String fechaTiempo) {
		// Se trocea usando el delimitador "-"
		// En parts[0] esta la fecha, y en parts[1] el tiempo
		String[] parts = fechaTiempo.split(" - ");
		// Cojo el segundo trozo
		String tiempo = parts[1];
		// Se obtienen horas, minutos y segundos usando el delimitador ":"
		parts = tiempo.split(":");
		// Solo tiene minutos y segundos
		if (parts.length == 2) {
			long minutos = Long.valueOf(parts[0]);
			long segundos = Long.valueOf(parts[1]);
			return Duration.of(minutos, ChronoUnit.MINUTES).plusSeconds(segundos);
		}
		// Tiene horas, minutos y segundos
		else if (parts.length == 3) {
			long horas = Long.valueOf(parts[0]);
			long minutos = Long.valueOf(parts[1]);
			long segundos = Long.valueOf(parts[2]);
			return Duration.of(horas, ChronoUnit.HOURS).plusMinutes(minutos).plusSeconds(segundos);
		}
		return null;

	}
	
}
