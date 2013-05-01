package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ReporteObjetivosBSCGrafico extends Fragment{
	
	private WebView browser;
	
	

	
	public ReporteObjetivosBSCGrafico(){
		
	}
	
	public class DataObject {
		String[] meses = new String[] { 
				"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio"};
		
		int[] avance = new int[] { 10, 15, 18, 25, 30, 33 };
		

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
	    
	    	return json;
	    }

	    
	    public String getTitulo(){

	    	return "Cumplimiento";
	    }
	    
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.reportebsc4grafico,
				container, false);

		
		browser = (WebView)rootView.findViewById(R.id.reportebscWebkitGLineal);
		 
        //habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		
		browser.loadUrl("file:///android_asset/ReporteBSCgooglelinechart.html");
		
		
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscTitulografico);
		textView.setText(titulo);
		
		
		return rootView;
	}

}
