package pe.edu.pucp.proyectorh.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//El avance con identificador = 00 es un progreso ficticio. Representa al avance del 0%.
public class AvanceDeObjetivo {
	
	private String ID;
//	private Date fechaRegistro;
	private String fechaRegistro;
	private int logroAlcanzado;
	
	public static ArrayList<AvanceDeObjetivo> tomarPrestadoDataDePrueba()
	{
//		try {
			
			ArrayList<AvanceDeObjetivo> avanceDeUnObjetivoCualquiera = new ArrayList<AvanceDeObjetivo>();
			
//			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("00", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"), 0));
//			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("10", new SimpleDateFormat("dd/MM/yyyy").parse("11/01/2013"), 10));
//			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("20", new SimpleDateFormat("dd/MM/yyyy").parse("21/01/2013"), 20));
//			
			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("00", "01/01/2000", 0));
			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("10", "11/01/2013", 10));
			avanceDeUnObjetivoCualquiera.add(new AvanceDeObjetivo("20", "21/01/2013", 20));
						
			
			return avanceDeUnObjetivoCualquiera;
			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
		
	}	
	
//	public AvanceDeObjetivo(String iD, Date fechaRegistro, int logroAlcanzado) {
	public AvanceDeObjetivo(String id, String fechaRegistro, int logroAlcanzado) {
		super();
//		ID = iD;
		this.ID = id;
		this.fechaRegistro = fechaRegistro;
		this.logroAlcanzado = logroAlcanzado;
	}

	
	
	public AvanceDeObjetivo() {
		super();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

//	public Date getFechaRegistro() {
//		return fechaRegistro;
//	}
//
//	public void setFechaRegistro(Date fechaRegistro) {
//		this.fechaRegistro = fechaRegistro;
//	}


	
	public int getLogroAlcanzado() {
		return logroAlcanzado;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setLogroAlcanzado(int logroAlcanzado) {
		this.logroAlcanzado = logroAlcanzado;
	}

	@Override
	public String toString() {
		return "AvanceDeObjetivo [ID=" + ID + ", fechaRegistro="
				+ fechaRegistro + ", logroAlcanzado=" + logroAlcanzado + "]";
	}
	
//	public String descritoBrevemente() 
//	{
//		if (ID.compareTo("00") == 0) {
//			return "Este objetivo aún se encuentra sin progreso";
//		} else {
//			return "El " + new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES")).format(fechaRegistro) + " alcancé el " + logroAlcanzado + "%"; 
//		}
//	}
	
	public String descritoBrevemente() 
	{
		if (ID.compareTo("00") == 0) {
			return "Este objetivo aún se encuentra sin progreso";
		} else {
			return "El " + fechaRegistro + " alcancé el " + logroAlcanzado + "%"; 
		}
	}	
	
}
