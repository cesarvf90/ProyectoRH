package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.pucp.proyectorh.utils.Constante;

public class Modulo {

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
		addItem(new ModuloItem("1", "Mi informaci�n"));
		addItem(new ModuloItem("2", "Administraci�n"));
		addItem(new ModuloItem("3", "Reclutamiento"));
		addItem(new ModuloItem("4", "Evaluaci�n 360"));
		addItem(new ModuloItem("5", "Objetivos"));
		addItem(new ModuloItem("6", "L�nea de carrera"));
		addItem(new ModuloItem("7", "Reportes"));
		MODULOS_MOSTRADOS_ACTUAL = MODULOS;
	}

	private static void addItem(ModuloItem item) {
		MODULOS.add(item);
		MODULOS_MAP.put(item.id, item);
	}

	public static List<ModuloItem> obtenerFuncionalidadesMiInformacion() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Informaci�n personal"));
		submodulos.add(new ModuloItem("2", "Mi equipo de trabajo"));
		submodulos.add(new ModuloItem("3", "Mis contactos"));
		submodulos.add(new ModuloItem("4", "Agenda"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesAdministracion() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Administrar empleados"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReclutamiento() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Aprobar postulante"));
		submodulos
				.add(new ModuloItem("2", "Aceptar solicitudes de oferta laboral"));
		submodulos.add(new ModuloItem("3", "Aceptar solicitudes de promoci�n"));
		submodulos.add(new ModuloItem("4", "Evaluar postulante"));
		submodulos.add(new ModuloItem("5", "Postular a convocatoria"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesEvaluacion360() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Mis evaluaciones"));
		submodulos.add(new ModuloItem("2", "Rol evaluador"));
		submodulos.add(new ModuloItem("3", "Mis subordinados"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesObjetivos() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Objetivos Empresa"));
		submodulos.add(new ModuloItem("2", "Mis objetivos"));
		submodulos.add(new ModuloItem("3", "Objetivos subordinados"));
		submodulos.add(new ModuloItem("4", "Avance"));
		submodulos.add(new ModuloItem("5", "Monitoreo"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesLineaDeCarrera() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Comparar capacidades"));
		submodulos.add(new ModuloItem("2", "Candidatos por puesto"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReportes() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("1", "Reporte de Evaluaci�n 360"));
		submodulos
				.add(new ModuloItem("2", "Reporte de Evaluaci�n de Objetivos"));
		submodulos.add(new ModuloItem("3", "Reporte de Cubrimiento de Puestos"));
		submodulos.add(new ModuloItem("4", "Reporte de Objetivos BSC"));
		return submodulos;
	}

}
