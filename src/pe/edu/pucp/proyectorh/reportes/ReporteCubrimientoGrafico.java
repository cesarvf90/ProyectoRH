package pe.edu.pucp.proyectorh.reportes;

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

public class ReporteCubrimientoGrafico extends Fragment{
	
	private WebView browser;
	
	public ReporteCubrimientoGrafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportecubrimientografico,
				container, false);
		
		
		browser = (WebView)rootView.findViewById(R.id.reportecubWebkit);
		 
        //habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		
		browser.loadUrl("file:///android_asset/ReporteCubrimientochart.html");
		
		
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reportecubTitulografico);
		textView.setText(titulo);
		
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
		String[] univ = new String[] { 
				"PUCP", "UNI", "U.LIMA", "UPC"};
		
		int[] bachiller = new int[] { 20, 15, 18, 15 };
		int[] master = new int[] { 8, 5, 8, 2 };
		int[] doctorado = new int[] { 3, 2, 0, 0 };

	}

}
