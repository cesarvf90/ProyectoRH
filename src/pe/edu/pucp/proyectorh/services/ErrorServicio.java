package pe.edu.pucp.proyectorh.services;

import android.app.Activity;
import android.app.AlertDialog;

public class ErrorServicio {

	/**
	 * Se muestra mensaje de error de conexion con el servicio
	 * 
	 * @param activity
	 *            actividad de donde proviene el error
	 */
	public static void mostrarErrorConexion(Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Error de conexión");
		builder.setMessage(ConstanteServicio.MENSAJE_PROBLEMA_CONEXION);
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}

	/**
	 * Se muestra un error de comunicacion detectado al recibir la respuesta del
	 * servicio
	 * 
	 * @param excepcion
	 * @param activity
	 */
	public static void mostrarErrorComunicacion(String excepcion, Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("Error de servicio");
		builder.setMessage(ConstanteServicio.MENSAJE_SERVICIO_NO_DISPONIBLE
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}
}
