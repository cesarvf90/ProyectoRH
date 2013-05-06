package pe.edu.pucp.proyectorh.services;

public class Servicio {

	/**
	 * Servicio de autenticacion de usuario
	 */
	public final static String LoginService = "http://dp2kendo.apphb.com/WSLogin/Login";

	/**
	 * Servicio de consulta de informacion personal del usuario
	 */
<<<<<<< HEAD
	public final static String InformacionPersonalService = "http://dp2kendo.apphb.com/WSColaborador/getColaborador";
=======
	// public final static String InformacionPersonalService =
	// "http://dp2kendo.apphb.com/Evaluacion360/WSCompetencias/test?nombre=cesar";
	public final static String InformacionPersonalService = "http://10.0.2.2:2642/Organizacion/Colaboradores/InformacionColaborador";
	/**
	 * Servicio que devuelve los contactos del usuario loggeado
	 */
	public final static String MisContactosService = "http://dp2kendo.apphb.com/Organizacion/Colaboradores/ColaboradoresToList";
>>>>>>> 637af605680ffa6ef3baf5d7fc69343bb7753941

}
