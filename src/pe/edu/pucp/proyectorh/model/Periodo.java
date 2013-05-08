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

    public Periodo(String nombre,int BSCID) {
    	this.Nombre = nombre;    	
    	this.BSCID = BSCID;
    	System.out.println("CREADO OK");
    }

}
