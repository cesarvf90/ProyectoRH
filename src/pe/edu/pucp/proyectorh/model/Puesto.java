package pe.edu.pucp.proyectorh.model;

/**
 * Clase puesto mapeada contra PuestoDTO
 * @author Cesar
 *
 */
public class Puesto {

	private int areaID;
	private Area area;
	private String nombre;
	private String descripcion;
	private int puestoSuperiorID;

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPuestoSuperiorID() {
		return puestoSuperiorID;
	}

	public void setPuestoSuperiorID(int puestoSuperiorID) {
		this.puestoSuperiorID = puestoSuperiorID;
	}

}
