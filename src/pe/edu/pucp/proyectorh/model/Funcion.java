package pe.edu.pucp.proyectorh.model;

public class Funcion {

	private int ID;
	private String descripcion;
	private String puestoID;

	public Funcion() {
		super();
	}

	public Funcion(int iD, String descripcion, String puestoID) {
		super();
		ID = iD;
		this.descripcion = descripcion;
		this.puestoID = puestoID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPuestoID() {
		return puestoID;
	}

	public void setPuestoID(String puestoID) {
		this.puestoID = puestoID;
	}

	@Override
	public String toString() {
		return descripcion;
	}

}
