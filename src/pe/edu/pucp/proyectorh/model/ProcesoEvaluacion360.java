package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

public class ProcesoEvaluacion360 {
	
	public String Nombre;
	public String fecha;
	public String estado;
	public int idProceso;

	  public static ArrayList<ProcesoEvaluacion360> getProcesosByResult(String result){
	    	ArrayList<ProcesoEvaluacion360> listaProcesos = new ArrayList<ProcesoEvaluacion360>();
			try {
		    	JSONArray arregloProcesos = new JSONArray(result);	
		    	for(int i=0;i<arregloProcesos.length();i++){
					final Gson gson = new Gson();
					final ProcesoEvaluacion360 proc = gson.fromJson(arregloProcesos.getString(i),ProcesoEvaluacion360.class);
					listaProcesos.add(proc);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return listaProcesos;
	    }
}
