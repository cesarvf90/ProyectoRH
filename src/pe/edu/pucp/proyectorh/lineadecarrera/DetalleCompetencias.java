package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.getConvocatorias;
import pe.edu.pucp.proyectorh.reportes.PerspectivaAdapter;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCObjetivos;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.webkit.WebView;

public class DetalleCompetencias extends Fragment  {

	private WebView browser;
	
	public DetalleCompetencias(){
		
	}
	
	
	
	public class DataObject {
		String[] lineax = new String[] { 
				"Área-Puesto"};
		
		int[] avance = new int[] {100};
		int[] avance2 = new int[] {84}; 
		
	}
	
	public class InterfaceChartLineal {
		Context mContext;

	    /** Instantiate the interface and set the context */
	    InterfaceChartLineal(Context c) {
	        mContext = c;
	    }
	    
	    public String getData(){
	    	
	    	DataObject obj = new DataObject();
	    	Gson gson = new Gson();
	    	 
	    	// convert java object to JSON format,
	    	// and returned as JSON formatted string
	    	String json = gson.toJson(obj);
	    	System.out.println(json);
	    	return json;
	    }
	}
	
	public String getTituloGraf(){

    	return "Detalle por Competencias";
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.detallecomp1,
				container, false);
		
		browser = (WebView)rootView.findViewById(R.id.detalleComp1WebkitGLineal);
		
		//habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		browser.loadUrl("file:///android_asset/DetalleCompPerbarchart.html");
		return rootView;
	}
}
