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
	 * Servicio que devuelve el equipo de trabajo del usuario loggeado
	 */
	public final static String getEquipoTrabajo = "http://dp2kendo.apphb.com/WSColaborador/getEquipoTrabajo";

	/**
	 * Servicio que devuelve los contactos del usuario loggeado
	 */
	public final static String MisContactosService = "http://dp2kendo.apphb.com/WSColaborador/getContactos";
	/*
	 * /** Servicio que devuelve las ofertas laborales en PRIMERA fase cuya
	 * evaluacion esta bajo la responsabilidad del usuario
	 * 
	 * public final static String OfertasLaboralesPrimeraFase =
	 * "http://dp2kendo.apphb.com/WSOfertaLaboral/getOfertasLaboralesPrimeraFase"
	 * ;
	 */
	/**
	 * Servicio que devuelve las ofertas laborales en TERCERA fase cuya
	 * evaluacion esta bajo la responsabilidad del usuario
	 */
	public final static String OfertasLaborales = "http://dp2kendo.apphb.com//WSOfertaLaboral/getOfertasLaborales";

	/**
	 * Servicio que retorna las preguntas de la evaluacion a rendir por el
	 * postulante de PRIMERA fase seleccionado
	 */
	public final static String ObtenerEvaluacionPrimeraFase = "http://dp2kendo.apphb.com/WSOfertaLaboral/getCompetencias";

	/**
	 * Servicio que retorna las preguntas de la evaluacion a rendir por el
	 * postulante de TERCERA fase seleccionado
	 */
	public final static String ObtenerEvaluacionTerceraFase = "http://dp2kendo.apphb.com/WSOfertaLaboral/getFunciones";

	/**
	 * Servicio que retorna las eventos del usuario
	 */
	public final static String ObtenerEventos = "http://dp2kendo.apphb.com/WSEvento/getEventos";

	/**
	 * Servicio que registra las respuestas registradas por el evaluador de la
	 * entrevista de PRIMERA fase
	 */
	public final static String RegistrarRespuestasEvaluacionPrimeraFase = "http://dp2kendo.apphb.com/WSEvalua/setRespuestasXEvaluacion";

	/**
	 * Servicio que registra las respuestas registradas por el evaluador de la
	 * entrevista de TERCERA fase
	 */
	public final static String RegistrarRespuestasEvaluacionTerceraFase = "http://dp2kendo.apphb.com/WSEvalua/setRespuestasXEvaluacion";

	public final static String ListarPeriodos = "http://dp2kendo.apphb.com/WSPeriodos/ListarPeriodos";

	// PARA OBJETIVOS DEL BSC
	public final static String ListarObjetivosBSC = "http://dp2kendo.apphb.com/WSObjetivosEmpresa/ListarObjetivosEmpresa";

	public final static String ListarAllObjetivosBSC = "http://dp2kendo.apphb.com/WSObjetivosEmpresa/GetAllObjetivosEmpresa";

	public final static String CrearObjetivoBSC = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosEmpresa/CrearObjetivoEmpresa";

	public final static String ActualizaObjetivoBSC = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosEmpresa/UpdateObjetivoEmpresa";

	public final static String EliminarObjetivoBSC = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosEmpresa/Destroy";

	// PARA MIS PROPIOS OBJETIVOS
	public final static String ListarMisObjetivos = "http://dp2kendo.apphb.com/WSMisObjetivos/GetAllMisObjetivos";

	public final static String CrearObjetivoPropio = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/Create";

	public final static String ActualizaObjetivoPropio = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/Update";

	public final static String EliminarObjetivoPropio = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/Destroy";

	public final static String ListarMisObjetivosSuperiores = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/GetAllMisObjetivosSuperiores";

	// PARA OBJETIVOS PARA MIS SUBORDINADOS (EQUIPO DE TRABAJO)
	public final static String ListarObjetivosParaSubordinados = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosSubordinados/ListarObjetivosDeSubordinados";

	public final static String CrearObjetivoSub = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosSubordinados/Create";

	public final static String ActualizaObjetivoSub = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosSubordinados/Update";

	public final static String EliminarObjetivoSub = "http://dp2kendo.apphb.com/Objetivos/WSObjetivosSubordinados/Destroy";

	// PARA MANEJO DE AVANCES DE OBJETIVOS
	public final static String CrearAvance = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/RegistrarAvance";

	public final static String ModificarAvance = "http://dp2kendo.apphb.com/Objetivos/WSMisObjetivos/ModificarAvance";

	public final static String	ListarSubordinados = "";
	
	public final static String GuardaAvance = "http://dp2kendo.apphb.com/Objetivos/Acordion/guardaValidacion";
	// PARA EVALUACION 360
	public final static String ListarProcesosEvaluacion360 = "http://dp2kendo.apphb.com/Evaluacion360/WSListarProcesosXEvaluador/Read";

	public final static String ListarMisEvaluados360 = "http://dp2kendo.apphb.com/Evaluacion360/WSListarProcesosXEvaluador/ReadEvaluados";

	public final static String ListarMisEvaluaciones = "http://dp2kendo.apphb.com/Evaluacion360/WSListarProcesosXEvaluado/Read";

	public final static String ListarPreguntas = "http://dp2kendo.apphb.com/Evaluacion360/WSEvaluacion/ReadPreguntas";

	public final static String EnviarRespuestas = "http://dp2kendo.apphb.com/Evaluacion360/WSEvaluacion/ResponderPreguntas";

	public final static String ConsultarSubordinados = "http://dp2kendo.apphb.com/Evaluacion360/GestorDatosDeColaboradores/conocerEquipoDeTrabajo";

	
	public final static String ConsultarEvaluacionesDelEquipoDeTrabajo = "http://dp2kendo.apphb.com/Evaluacion360/GestorDatosDeColaboradores/consultarEvaluacionesDelEquipoDeTrabajo";
	
	public final static String ConsultarNotasDeProcesoDeEvaluaciones = "http://dp2kendo.apphb.com/Evaluacion360/GestorDatosDeColaboradores/consultarNotasDeProcesoDeEvaluaciones";
	
	


	/**
	 * Servicio que devuelve la lista de solicitudes de oferta laboral
	 * pendientes de aprobar
	 */
	public final static String AprobarSolicitudOfertaLaboral = "http://dp2kendo.apphb.com/WSOfertaLaboral/getOfertasLaboralesXEstado";

	/**
	 * Servicio que devuelve la lista de solicitudes de oferta laboral a las que
	 * el colaborador puede postular
	 */
	public final static String ObtenerOfertasParaPostulacion = "http://dp2kendo.apphb.com/WSOfertaLaboral/getOfertasLaboralesColaborador";

	/**
	 * Servicio que android da como respuesta a la aprobación o rechazo de una
	 * oferta laboral (android devuelve 1 o 0 al WS)
	 */
	public final static String RespuestaAprobarSolicitudOfertaLaboral = "http://dp2kendo.apphb.com/WSOfertaLaboral/setEstadoSolicitudOfertaLaboral";

	/**
	 * Servicio get que consiste en devolver el ID del colaborador y el ID de la
	 * oferta laboral para registrar en el ws la postulación
	 */
	public static final String EnviarPostulacionOfertaLaboral = "http://dp2kendo.apphb.com/WSOfertaLaboral/registrarPostulacion";

	public static void llamadaServicio(Activity miActividad, AsyncCall miClase,
			String request) {
		try {
			if (ConnectionManager.connect(miActividad)) {
				miClase.execute(request);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						miActividad);
				builder.setTitle("Error de conexión");
				builder.setMessage(ConstanteServicio.MENSAJE_PROBLEMA_CONEXION);
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
			}
		} catch (Exception e) {
			System.out.println("SE CAYOOOOOOOOOOOOOOOO");
			mostrarErrorComunicacion(e.toString(), miActividad);
		}
	}

	public static void mostrarErrorComunicacion(String excepcion,
			Activity miActividad) {
		AlertDialog.Builder builder = new AlertDialog.Builder(miActividad);
		builder.setTitle("Error de servicio");
		builder.setMessage(ConstanteServicio.MENSAJE_SERVICIO_NO_DISPONIBLE
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}
}
