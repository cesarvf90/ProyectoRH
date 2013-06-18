package pe.edu.pucp.proyectorh.reportes;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import com.google.gson.Gson;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class Reporte360Grafico extends Fragment{

	private WebView browser;
	int objSelec;
	

	
	public Reporte360Grafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reporte360grafico,
				container, false);
		
		TextView nombre = (TextView)rootView.findViewById(R.id.nombreUsuarioRep);
		nombre.setText(LoginActivity.getUsuario().getUsername());
		
		browser = (WebView)rootView.findViewById(R.id.reporte360WebkitGLineal);
		
		
        //habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		
		browser.loadUrl("file:///android_asset/Reporte360chart.html");
		
		/*
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reporte360Titulografico);
		textView.setText(titulo);*/
		
		return rootView;
	}
	
	public class InterfaceChartLineal {
	    Context mContext;

	    /** Instantiate the interface and set the context */
	    InterfaceChartLineal(Context c) {
	        mContext = c;
	    }

	    /* Show a toast from the web page 
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }*/
	    
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
	
	public class DataObject {
		String[] procesoEval = new String[] { 
				"Proceso de Evaluación 1", "Proceso de Evaluación 2", "Proceso de Evaluación 3", "Proceso de Evaluación 4"};
		
		int[] jefe = new int[] { 10, 13, 14, 16 };
		int[] compañerosPares = new int[] { 13, 15, 18, 16 };
		int[] subordinados = new int[] { 15, 12, 14, 17 };
		int[] notafinal = new int[] { 16, 14, 17, 16 };
		

	}
	
}
