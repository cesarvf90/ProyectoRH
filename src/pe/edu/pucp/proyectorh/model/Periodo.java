package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Periodo {
    public String Nombre;
    public String FechaInicio;
    public String FechaFin;
    public String FechaFinDisplay;
    public int BSCID;
    public int id;

    public static ArrayList<Periodo> getPeriodosByResult(String result){
    	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
		try {
	    	JSONArray arregloPeriodos = new JSONArray(result);	
	    	for(int i=0;i<arregloPeriodos.length();i++){
				final Gson gson = new Gson();
				final Periodo per = gson.fromJson(arregloPeriodos.getString(i),Periodo.class);
				listaPeriodos.add(per);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listaPeriodos;
    }
}
