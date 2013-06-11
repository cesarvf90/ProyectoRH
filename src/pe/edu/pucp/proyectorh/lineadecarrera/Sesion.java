package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.List;

import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.OfertaLaboralMobileJefeDTO;

public class Sesion {

	private static List<OfertaLaboralMobileJefeDTO> listaConvocatorias;
	
	public List<OfertaLaboralMobileJefeDTO> getConvocatorias(){
		return Sesion.listaConvocatorias;
	}
	
	public void setConvocatorias(List<OfertaLaboralMobileJefeDTO> listaConvocatorias){
		Sesion.listaConvocatorias = listaConvocatorias;
	}
	
}
