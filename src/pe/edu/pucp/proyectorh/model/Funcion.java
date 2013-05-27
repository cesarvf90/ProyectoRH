package pe.edu.pucp.proyectorh.model;

public class Funcion {

	private String ID;
	private String descripcion;
	private String puestoID;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
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
