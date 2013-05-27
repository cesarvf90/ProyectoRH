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
	public boolean IsAsignadoAPersona;
	public int CreadorID;
	public int TipoObjetivoBSCID;
	public int ObjetivoPadreID;
	public int BSCID;	
	
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