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
	
}
