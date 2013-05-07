package pe.edu.pucp.proyectorh.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Periodo {
    public String Nombre;
    public Date FechaInicio;
    public Date FechaFin;

    public int BSCID;
    public int id;

    public Periodo(String nombre, String FechaInicio, String FechaFin, int BSCID, int id) {
    	this.Nombre = nombre;
    	try {
			this.FechaInicio = new SimpleDateFormat("MMMM d, yyyy").parse(FechaInicio);
	    	this.FechaFin = new SimpleDateFormat("MMMM d, yyyy").parse(FechaFin);
    	}catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("error-periodo="+e.toString());
		}
    	this.BSCID = BSCID;
    	this.id = id;
    	System.out.println("CREADO OK");
    }

}
