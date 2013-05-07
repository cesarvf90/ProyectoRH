package pe.edu.pucp.proyectorh.services;

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

}
