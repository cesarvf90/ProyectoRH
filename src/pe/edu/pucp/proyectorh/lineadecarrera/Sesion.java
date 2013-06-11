package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.List;

import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.OfertaLaboralMobileJefeDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;

public class Sesion {

	private static List<OfertaLaboralMobileJefeDTO> listaConvocatorias;
	private static List<OfertaLaboralMobilePostulanteDTO> listaOfertas;
	
	public List<OfertaLaboralMobileJefeDTO> getConvocatorias(){
		return Sesion.listaConvocatorias;
	}
	
	public void setConvocatorias(List<OfertaLaboralMobileJefeDTO> listaConvocatorias){
		Sesion.listaConvocatorias = listaConvocatorias;
	}
	
	public List<OfertaLaboralMobilePostulanteDTO> getOfertas(){
		return Sesion.listaOfertas;
	}
	
	public void setOfertas(List<OfertaLaboralMobilePostulanteDTO> listaOfertas){
		Sesion.listaOfertas = listaOfertas;
	}
}
