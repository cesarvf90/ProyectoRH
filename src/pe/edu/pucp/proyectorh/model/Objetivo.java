package pe.edu.pucp.proyectorh.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Objetivo {
	
	private String ID;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private int peso;
	private ArrayList<AvanceDeObjetivo> susAvances;
	private Colaborador dueño;
	
	public static ArrayList<Objetivo> solicitarServidorPorData()
	{
		
		Colaborador admin = new Colaborador();
		admin.setNombres("admin");
		admin.setId("1");	
		
		ArrayList<Objetivo> datosSupuestamenteDelServidor = new ArrayList<Objetivo>();
		
		try {
			datosSupuestamenteDelServidor.add(new Objetivo(
					"1", "Aumentar las ventas", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2013"),
					new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2013"), 20,
					new ArrayList<AvanceDeObjetivo>(), admin));
			return datosSupuestamenteDelServidor;
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		ArrayList<Objetivo> nada = null;
		
		return nada;
	}
	
	public static ArrayList<Objetivo> tomarPrestadoDataDePrueba()
	{
		Colaborador admin = new Colaborador();
		admin.setNombres("admin");
		admin.setId("1");		
		
		ArrayList<Objetivo> objetivosDataDePrueba = new ArrayList<Objetivo>();
		
		try {
			objetivosDataDePrueba.add(new Objetivo(
					"1", "Aumentar las ventas", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2013"),
					new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2013"), 20,
					new ArrayList<AvanceDeObjetivo>(), admin));
			objetivosDataDePrueba.add(new Objetivo(
					"1", "Certificarme en gestión de proyectos", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2013"),
					new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2013"), 50,
					new ArrayList<AvanceDeObjetivo>(), admin));			
			
			
			List<AvanceDeObjetivo> avanceDeParticiparEnTalleres = new ArrayList<AvanceDeObjetivo>();
			
//			avanceDeParticiparEnTalleres.add(new AvanceDeObjetivo("10", new SimpleDateFormat("dd/MM/yyyy").parse("11/01/2013"), 10));
//			avanceDeParticiparEnTalleres.add(new AvanceDeObjetivo("20", new SimpleDateFormat("dd/MM/yyyy").parse("21/01/2013"), 20));
//			
			avanceDeParticiparEnTalleres.add(new AvanceDeObjetivo("10", "11/01/2013", 10));
			avanceDeParticiparEnTalleres.add(new AvanceDeObjetivo("20", "21/01/2013", 20));
			
			objetivosDataDePrueba.add(new Objetivo(
					"1", "Participar en talleres de gestión de la innovación", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2013"),
					new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2013"), 30,
					(ArrayList<AvanceDeObjetivo>) avanceDeParticiparEnTalleres, admin));	
			
			return objetivosDataDePrueba;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}


	
	public Objetivo(String iD, String descripcion, Date fechaInicio,
			Date fechaFin, int peso, ArrayList<AvanceDeObjetivo> susAvances,
			Colaborador dueño) {
		super();
		ID = iD;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.peso = peso;
		this.susAvances = susAvances;
		this.dueño = dueño;
	}



	public Objetivo() {
		super();
	}

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public ArrayList<AvanceDeObjetivo> getSusAvances() {
		return susAvances;
	}
	public void setSusAvances(ArrayList<AvanceDeObjetivo> susAvances) {
		this.susAvances = susAvances;
	}
	public Colaborador getDueño() {
		return dueño;
	}
	public void setDueño(Colaborador dueño) {
		this.dueño = dueño;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Objetivo [ID=" + ID + ", descripcion=" + descripcion
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", peso=" + peso + ", susAvances=" + susAvances + ", dueño="
				+ dueño + "]";
	}	
	
//	public String descritoBrevemente()
//	{
//		return descripcion + " (Valor de " + peso +  "%): Lo alcanzaré el " + new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES")).format(fechaFin);
//	}
//	
	public String descritoBrevemente()
	{
//		return descripcion + " (Valor de " + peso +  "%): Lo alcanzaré el " + new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES")).format(fechaFin);
		return descripcion + " (Valor de " + peso +  "%)";
	}	
	
}
