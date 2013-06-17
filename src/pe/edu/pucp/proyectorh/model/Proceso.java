package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class Proceso {

	public String ID;
	public String elNombre;
	
	public static ArrayList<Proceso> tomarDatosTemporales()
	{
		ArrayList<Proceso> unosEventosDeEvaluacion = new ArrayList<Proceso>();
		
		unosEventosDeEvaluacion.add(new Proceso("1", "Enero 2013"));
		unosEventosDeEvaluacion.add(new Proceso("2", "Área RR.HH. - Último semestre"));
		
		return unosEventosDeEvaluacion;
	}
	
	public static ArrayList<String> entregarListado()
	{
		ArrayList<Proceso> periodos = tomarDatosTemporales();
		
		ArrayList<String> menuDeProcesos = new ArrayList<String>();
		
		for (Proceso evaluacion : periodos)
		{
			menuDeProcesos.add(evaluacion.presentadoBrevemente());
			
		}
		
		return menuDeProcesos;
		
	}

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
