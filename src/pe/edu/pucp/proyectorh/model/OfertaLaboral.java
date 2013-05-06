package pe.edu.pucp.proyectorh.model;

public class OfertaLaboral {

	private int puestoID;
	private Puesto puesto;
	private int estado;

	public int getPuestoID() {
		return puestoID;
	}

	public void setPuestoID(int puestoID) {
		this.puestoID = puestoID;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

}
