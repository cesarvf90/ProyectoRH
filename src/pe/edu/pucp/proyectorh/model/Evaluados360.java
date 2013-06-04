package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class Evaluados360 {
	public String Nombre;

	  public static ArrayList<Evaluados360> getEvaluadosByResult(String result){
	    	ArrayList<Evaluados360> listaEvaluados = new ArrayList<Evaluados360>();
			try {
		    	JSONArray arregloEvaluados = new JSONArray(result);	
		    	for(int i=0;i<arregloEvaluados.length();i++){
					final Gson gson = new Gson();
					final Evaluados360 eval = gson.fromJson(arregloEvaluados.getString(i),Evaluados360.class);
					listaEvaluados.add(eval);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return listaEvaluados;
	    }

}
