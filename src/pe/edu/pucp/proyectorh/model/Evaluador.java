package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class Evaluador extends Colaborador{

	public int elEventoDeEvaluacionQueParticipa;
	public String laFase;
	
	
	public static ArrayList<ArrayList<ArrayList<Evaluador>>> agruparEvaluadores()
	{
		Evaluador clengua = new Evaluador();
		
		clengua.setNombres("Carlos Lengua");
		clengua.setElEventoDeEvaluacionQueParticipa(2);
		clengua.setLaFase("Pendiente");
		
		Evaluador fguzman = new Evaluador();
		
		fguzman.setNombres("Fernanda Guzman");
		fguzman.setElEventoDeEvaluacionQueParticipa(2);
		fguzman.setLaFase("Pendiente");		
		
		ArrayList<Evaluador> unIntegrante = new ArrayList<Evaluador>();
		ArrayList<Evaluador> otroIntegrante = new ArrayList<Evaluador>();
		
		unIntegrante.add(clengua);
		otroIntegrante.add(fguzman);
		
		ArrayList<ArrayList<Evaluador>> losCompanerosDeUnSubordinado = new ArrayList<ArrayList<Evaluador>>();
		
		losCompanerosDeUnSubordinado.add(unIntegrante);
		losCompanerosDeUnSubordinado.add(otroIntegrante);
		
		
		ArrayList<ArrayList<ArrayList<Evaluador>>> conjuntoDeParticipantes = new ArrayList<ArrayList<ArrayList<Evaluador>>>();
		
		conjuntoDeParticipantes.add(losCompanerosDeUnSubordinado);
		
		ArrayList<ArrayList<Evaluador>> grupoVacio = new ArrayList<ArrayList<Evaluador>>();
		ArrayList<ArrayList<Evaluador>> otroIgualDeVacio = new ArrayList<ArrayList<Evaluador>>();
		ArrayList<ArrayList<Evaluador>> elCuarto = new ArrayList<ArrayList<Evaluador>>();
		ArrayList<ArrayList<Evaluador>> elDeLaPosicionCinco = new ArrayList<ArrayList<Evaluador>>();
		ArrayList<ArrayList<Evaluador>> laUltimaAgrupacionDesocupada = new ArrayList<ArrayList<Evaluador>>();
		
		
		conjuntoDeParticipantes.add(grupoVacio);
		conjuntoDeParticipantes.add(otroIgualDeVacio);
		conjuntoDeParticipantes.add(elCuarto);
		conjuntoDeParticipantes.add(elDeLaPosicionCinco);
		conjuntoDeParticipantes.add(laUltimaAgrupacionDesocupada);
		
		
		return conjuntoDeParticipantes;
		
	}	
	
	
	
	public Evaluador() {
		super();
	}

	public Evaluador(int elEventoDeEvaluacionQueParticipa, String laFase) {
		super();
		this.elEventoDeEvaluacionQueParticipa = elEventoDeEvaluacionQueParticipa;
		this.laFase = laFase;
	}

	public int getElEventoDeEvaluacionQueParticipa() {
		return elEventoDeEvaluacionQueParticipa;
	}

	public void setElEventoDeEvaluacionQueParticipa(
			int elEventoDeEvaluacionQueParticipa) {
		this.elEventoDeEvaluacionQueParticipa = elEventoDeEvaluacionQueParticipa;
	}

	public String getLaFase() {
		return laFase;
	}

	public void setLaFase(String laFase) {
		this.laFase = laFase;
	}

	@Override
	public String toString() {
		return "Evaluador [elEventoDeEvaluacionQueParticipa="
				+ elEventoDeEvaluacionQueParticipa + ", laFase=" + laFase + "]";
	}
	
//	public String presentaSuInformacion()
//	{
//		return this.getNombres() + ": " + this.getPuesto();
//		
//	}
	
	public String presentaSuInformacion()
	{
		return this.getNombres() + " lo evalua. Su examen esta " + laFase;
		
	}
	
	
}
