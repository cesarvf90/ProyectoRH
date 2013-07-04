package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class Proceso {

	public String ID;
	public String elNombre;
	


	public Proceso() {
		super();
	}
	
	public Proceso(String iD, String elNombre) {
		super();
		ID = iD;
		this.elNombre = elNombre;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getElNombre() {
		return elNombre;
	}

	public void setElNombre(String elNombre) {
		this.elNombre = elNombre;
	}
	
	public String presentadoBrevemente()
	{
		return elNombre;	
	}

	@Override
	public String toString() {
		return "Proceso [ID=" + ID + ", elNombre=" + elNombre + "]";
	}
	
}
