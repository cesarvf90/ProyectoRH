package pe.edu.pucp.proyectorh.services;

import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import android.app.Activity;
import android.app.AlertDialog;

public class Servicio {

	/**
	 * Servicio de autenticacion de usuario
	 */
	public final static String LoginService = "http://dp2kendo.apphb.com/WSLogin/Login";

	/**
	 * Servicio de consulta de informacion personal del usuario
	 */
	public final static String InformacionPersonalService = "http://dp2kendo.apphb.com/WSColaborador/getColaborador";
	/**
	 * Servicio que devuelve los contactos del usuario loggeado
	 */
	public final static String MisContactosService = "http://dp2kendo.apphb.com/Organizacion/Colaboradores/ColaboradoresToList";

	public final static String ListarPeriodos = "http://dp2kendo.apphb.com/WSPeriodos/ListarPeriodos";

	public final static String ListarObjetivosBSC = "http://dp2kendo.apphb.com/WSObjetivosEmpresa/ListarObjetivosEmpresa";

	public final static String CrearObjetivoBSC = "http://dp2kendo.apphb.com/WSObjetivosEmpresa/CrearObjetivoEmpresa";

	/**
	 * Servicio que devuelve la lista de solicitudes de oferta laboral pendientes de aprobar
	 */
	public final static String AprobarSolicitudOfertaLaboral = "";
	/**
	 * Servicio que android da como respuesta a la aprobación o rechazo de una oferta laboral (android devuelve 1 o 0 al WS) 
	 */
	public final static String RespuestaAprobarSolicitudOfertaLaboral = "";
	
	public static void llamadaServicio(Activity miActividad, AsyncCall miClase,String request){
		if (ConnectionManager.connect(miActividad)) {			
			miClase.execute(request);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(miActividad);
			builder.setTitle("Error de conexión");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	}
}
