package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

public class ProcesoXCompetencias {

	private ProcesoEvaluacion360 unProceso;
	private ArrayList<Competencia> competencias;
	
	public ProcesoXCompetencias() {
		super();
	}
	public ProcesoEvaluacion360 getUnProceso() {
		return unProceso;
	}
	public void setUnProceso(ProcesoEvaluacion360 unProceso) {
		this.unProceso = unProceso;
	}
	public ArrayList<Competencia> getCompetencias() {
		return competencias;
	}
	public void setCompetencias(ArrayList<Competencia> competencias) {
		this.competencias = competencias;
	}
	
	
	
}
