package pe.edu.pucp.proyectorh.services;

public class ConstanteServicio {

	/**
	 * La llamada al servicio fue exitosa
	 */
	public static final String SERVICIO_OK = "true";

	/**
	 * La llamada al servicio fallo
	 */
	public static final String SERVICIO_ERROR = "false";

	/**
	 * Mensaje a mostrar cuando no se puede salir a internet para comunicar al
	 * servicio
	 */
	public static final String MENSAJE_PROBLEMA_CONEXION = "No se pudo conectar con el servidor. Revise su conexión a Internet.";

	/**
	 * Mensaje de servicio no disponible
	 */
	public static final String MENSAJE_SERVICIO_NO_DISPONIBLE = "El servicio solicitado no está disponible en el servidor.";
}
