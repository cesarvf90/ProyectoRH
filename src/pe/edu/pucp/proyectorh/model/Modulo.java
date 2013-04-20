package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	}

	public static List<ModuloItem> ITEMS = new ArrayList<ModuloItem>();
	public static Map<String, ModuloItem> ITEM_MAP = new HashMap<String, ModuloItem>();

	static {
		addItem(new ModuloItem("1", "Mi información"));
		addItem(new ModuloItem("2", "Administración"));
		addItem(new ModuloItem("3", "Reclutamiento"));
		addItem(new ModuloItem("4", "Evaluación 360"));
		addItem(new ModuloItem("5", "Objetivos"));
		addItem(new ModuloItem("6", "Reportes"));
	}

	private static void addItem(ModuloItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	public static List<ModuloItem> obtenerFuncionalidadesMiInformacion() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("7", "Mii111"));
		submodulos.add(new ModuloItem("8", "Mii222"));
		submodulos.add(new ModuloItem("9", "Mii333"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesAdministracion() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("10", "Adm111"));
		submodulos.add(new ModuloItem("11", "Adm222"));
		submodulos.add(new ModuloItem("12", "Adm333"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReclutamiento() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("13", "Rec111"));
		submodulos.add(new ModuloItem("14", "Rec222"));
		submodulos.add(new ModuloItem("15", "Rec333"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesEvaluacion360() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("16", "Eva111"));
		submodulos.add(new ModuloItem("17", "Eva222"));
		submodulos.add(new ModuloItem("18", "Eva333"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesObjetivos() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("19", "Obj111"));
		submodulos.add(new ModuloItem("20", "Obj222"));
		submodulos.add(new ModuloItem("21", "Obj333"));
		return submodulos;
	}

	public static List<ModuloItem> obtenerFuncionalidadesReportes() {
		List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
		submodulos.add(new ModuloItem("22", "Rep111"));
		submodulos.add(new ModuloItem("23", "Rep222"));
		submodulos.add(new ModuloItem("24", "Rep333"));
		return submodulos;
	}

}
