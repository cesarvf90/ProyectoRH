package pe.edu.pucp.proyectorh.model;

public class Rol {

	private int ID;
	private boolean eliminado;
	private boolean web;
	private String nombre;
	private boolean permiso;
	private String area;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isPermiso() {
		return permiso;
	}

	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
