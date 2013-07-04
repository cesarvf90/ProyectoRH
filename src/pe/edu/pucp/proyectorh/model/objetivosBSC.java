package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class ObjetivosBSC{
	public int ID;
	public String Nombre;
	public int Peso;
	public String AvanceFinal;
	//public boolean IsAsignadoAPersona;
	public int CreadorID;
	public int TipoObjetivoBSCID;
	public int ObjetivoPadreID;
	public int BSCID;	
	public ArrayList<AvanceDTO> LosProgresos;
	public String FechaCreacion;
	public String FechaFinalizacion;
	public String comentarioUltimoAvance;
	public int PesoMiObjetivo;
	public int AvanceFinalDeAlgunProgeso=0;
	
	public boolean seElimina=true;
	
	public ObjetivosBSC() {
	
	}
	
	public ObjetivosBSC(String nombre){
		Nombre= nombre;
	}
	
    public static ArrayList<ObjetivosBSC> getObjetivosByResult(String result){
    	ArrayList<ObjetivosBSC> listaObjetivos = new ArrayList<ObjetivosBSC>();
		try {
	    	JSONArray arregloObjetivos = new JSONArray(result);	
	    	for(int i=0;i<arregloObjetivos.length();i++){
				final Gson gson = new Gson();
				final ObjetivosBSC obj = gson.fromJson(arregloObjetivos.getString(i),ObjetivosBSC.class);
				listaObjetivos.add(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listaObjetivos;
    }
}
