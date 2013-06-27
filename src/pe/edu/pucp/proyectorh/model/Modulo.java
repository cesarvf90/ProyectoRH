package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.pucp.proyectorh.utils.Constante;

public class Modulo {

	// Indicadores de indice de permiso cada funcionalidad dentro del modulo
	public static String INFORMACION_PERSONAL = "";
	public static String EQUIPO_TRABAJO = "";
	public static String CONTACTOS = "";
	public static String AGENDA = "";

	public static String APROBAR_SOLICITUDES = "";
	public static String EVALUACION_FASE1 = "";
	public static String EVALUACION_FASE3 = "";
	public static String POSTULAR_OFERTA = "";

	public static String PENDIENTES = "";
	public static String ROL_EVALUADO = "";
	public static String SUBORDINADOS = "";

	public static String OBJETIVOS_EMPRESARIALES = "";
	public static String OBJETIVOS_PROPIOS = "";
	public static String OBJETIVOS_EQUIPO = "";
	public static String REGISTRAR_AVANCE = "";
	public static String VISUALIZAR_AVANCE = "";
	public static String MONITOREO = "";

	public static String COMPARAR_CAPACIDADES = "";
	public static String CANDIDATOS_X_PUESTO = "";

	public static String REPORTE_EVALUACION_360 = "";
	public static String REPORTE_HISTORICO_OBJETIVOS = "";
	public static String REPORTE_OFERTAS_LABORALES = "";
	public static String REPORTE_OBJETIVOS_BSC = "";

	public static class ModuloItem {

		public String id;
		public String nombre;

		public ModuloItem(String id, String nombre) {
			this.id = id;
			this.nombre = nombre;
		}

		@Override
		public String toString() {
			return nombre;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

	}

	public static List<ModuloItem> MODULOS_MOSTRADOS_ACTUAL = new ArrayList<ModuloItem>();
	public static int MODULO_ACTUAL = Constante.PRINCIPAL;
	public static List<ModuloItem> MODULOS = new ArrayList<ModuloItem>();
	public static Map<String, ModuloItem> MODULOS_MAP = new HashMap<String, ModuloItem>();

	static {
		addItem(new ModuloItem("1", "Mi información"));
		addItem(new ModuloItem("2", "Reclutamiento"));
		addItem(new ModuloItem("3", "Evaluación 360"));
		addItem(new ModuloItem("4", "Objetivos"));
		addItem(new ModuloItem("5", "Línea de carrera"));
		addItem(new ModuloItem("6", "Reportes"));
		MODULOS_MOSTRADOS_ACTUAL = MODULOS;
	}

	private static void addItem(ModuloItem item) {
		MODULOS.add(item);
		MODULOS_MAP.put(item.id, item);
	}

	public static List<ModuloItem> obtenerFuncionalidadesMiInformacion(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();

		if (usuario.getRoles().get("Información personal").isPermiso()) {
			INFORMACION_PERSONAL = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(INFORMACION_PERSONAL,
					"Información personal"));
		}
		if (usuario.getRoles().get("Mi equipo de trabajo").isPermiso()) {
			EQUIPO_TRABAJO = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(EQUIPO_TRABAJO,
					"Mi equipo de trabajo"));
		}
		if (usuario.getRoles().get("Mis contactos").isPermiso()) {
			CONTACTOS = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(CONTACTOS, "Mis contactos"));
		}
		if (usuario.getRoles().get("Agenda").isPermiso()) {
			AGENDA = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(AGENDA, "Agenda"));
		}
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReclutamiento(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		if (usuario.getRoles().get("Aprobar solicitudes de oferta laboral")
				.isPermiso()) {
			APROBAR_SOLICITUDES = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(APROBAR_SOLICITUDES,
					"Aprobar solicitudes de oferta laboral"));
		}
		if (usuario.getRoles().get("Evaluar postulantes 1ra fase").isPermiso()) {
			EVALUACION_FASE1 = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(EVALUACION_FASE1,
					"Evaluar postulantes 1ra fase"));
		}
		if (usuario.getRoles().get("Evaluar postulantes 3ra fase").isPermiso()) {
			EVALUACION_FASE3 = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(EVALUACION_FASE3,
					"Evaluar postulantes 3ra fase"));
		}
		if (usuario.getRoles().get("Postular a oferta laboral").isPermiso()) {
			POSTULAR_OFERTA = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(POSTULAR_OFERTA,
					"Postular a oferta laboral"));
		}
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesEvaluacion360(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		if (usuario.getRoles().get("Mis Pendientes").isPermiso()) {
			PENDIENTES = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(PENDIENTES, "Mis Pendientes"));
		}
		if (usuario.getRoles().get("Rol de Evaluado").isPermiso()) {
			ROL_EVALUADO = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(ROL_EVALUADO, "Rol de Evaluado"));
		}
		if (usuario.getRoles().get("Mis subordinados").isPermiso()) {
			SUBORDINADOS = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(SUBORDINADOS, "Mis subordinados"));
		}
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesObjetivos(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		if (usuario.getRoles().get("Objetivos Empresariales").isPermiso()) {
			OBJETIVOS_EMPRESARIALES = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(OBJETIVOS_EMPRESARIALES,
					"Objetivos Empresariales"));
		}
		if (usuario.getRoles().get("Objetivos Propios").isPermiso()) {
			OBJETIVOS_PROPIOS = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(OBJETIVOS_PROPIOS,
					"Objetivos Propios"));
		}
		if (usuario.getRoles().get("Objetivos para Equipo").isPermiso()) {
			OBJETIVOS_EQUIPO = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(OBJETIVOS_EQUIPO,
					"Objetivos para Equipo"));
		}
		if (usuario.getRoles().get("Registrar Avance").isPermiso()) {
			REGISTRAR_AVANCE = String.valueOf(idSecuencial++);
			submodulos
					.add(new ModuloItem(REGISTRAR_AVANCE, "Registrar Avance"));
		}
		if (usuario.getRoles().get("Visualizar Avances").isPermiso()) {
			VISUALIZAR_AVANCE = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(VISUALIZAR_AVANCE,
					"Visualizar Avances"));
		}
		if (usuario.getRoles().get("Monitoreo").isPermiso()) {
			MONITOREO = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(MONITOREO, "Monitoreo"));
		}
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesLineaDeCarrera(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		if (usuario.getRoles().get("Comparar capacidades").isPermiso()) {
			COMPARAR_CAPACIDADES = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(COMPARAR_CAPACIDADES,
					"Comparar capacidades"));
		}
		if (usuario.getRoles().get("Candidatos por puesto").isPermiso()) {
			CANDIDATOS_X_PUESTO = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(CANDIDATOS_X_PUESTO,
					"Candidatos por puesto"));
		}
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReportes(
			Usuario usuario) {
		int idSecuencial = 1;
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		if (usuario.getRoles().get("Reporte de Evaluación 360").isPermiso()) {
			REPORTE_EVALUACION_360 = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(REPORTE_EVALUACION_360,
					"Reporte de Evaluación 360"));
		}
		if (usuario.getRoles().get("Reporte histórico de Objetivos")
				.isPermiso()) {
			REPORTE_HISTORICO_OBJETIVOS = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(REPORTE_HISTORICO_OBJETIVOS,
					"Reporte histórico de Objetivos"));
		}
		if (usuario.getRoles().get("Reporte de Cubrimiento de Puestos")
				.isPermiso()) {
			REPORTE_OFERTAS_LABORALES = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(REPORTE_OFERTAS_LABORALES,
					"Reporte de Ofertas Laborales"));
		}
		if (usuario.getRoles().get("Reporte de Objetivos BSC").isPermiso()) {
			REPORTE_OBJETIVOS_BSC = String.valueOf(idSecuencial++);
			submodulos.add(new ModuloItem(REPORTE_OBJETIVOS_BSC,
					"Reporte de Objetivos BSC"));
		}
		return submodulos;
	}
}
