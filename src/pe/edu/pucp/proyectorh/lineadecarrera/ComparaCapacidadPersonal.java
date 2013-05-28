package pe.edu.pucp.proyectorh.lineadecarrera;

import com.google.gson.Gson;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.reportes.PerspectivaAdapter;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCObjetivos;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.InterfaceChartLineal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.content.Context;
import android.webkit.WebView;


public class ComparaCapacidadPersonal extends Fragment {

	int idConvocatoria;
	//GridView gridView;
	private WebView browser;
	private Spinner spinnerRequisitos;
	
	public ComparaCapacidadPersonal(){
		
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
	    
	    public String getTituloGraf(){

	    	return "Nivel de Cumplimiento para el puesto";
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
		
		View rootView = inflater.inflate(R.layout.comparacap2per,
				container, false);
		
		browser = (WebView)rootView.findViewById(R.id.comparaCap1WebkitGLineal);
		
		//habilitamos javascript y flash
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		browser.loadUrl("file:///android_asset/ComparaCapPerbarchart.html");
		
		spinnerRequisitos = (Spinner) rootView.findViewById(R.id.comp_puesto_requi);
		
		String tituloconv = getArguments().getString("titulo");
		String descrip = getArguments().getString("desc");
		idConvocatoria = getArguments().getInt("convocSelec");
		String usuario = getArguments().getString("IdUsuario");
		
		TextView textView = (TextView)rootView.findViewById(R.id.CompCapConvSelec);
		textView.setText(tituloconv);
		
		TextView textView2 = (TextView)rootView.findViewById(R.id.comp_puesto_descrip);
		textView2.setText(descrip);
		
		//gridView = (GridView) rootView.findViewById(R.id.reportebscgridPerspectivas);
		//gridView.setAdapter(new PerspectivaAdapter(rootView.getContext(), perspectivas));
		
		/*gridView.setOnItemClickListener(new OnItemClickListener() {
			
			
			@Override
			public void onItemClick (AdapterView<?> parent, View v,
					int position, long id) {
				/*
			   Toast.makeText(v.getContext(),
				((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText(), Toast.LENGTH_SHORT).show();
				*/
				
				  /*ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
			      
				  Bundle b = new Bundle();
				  b.putInt("nivel",0);
				  b.putString("objetivopadre", "Perspectiva " + ((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText());
				  
				  b.putInt("idPeriodo", idPeriodo);
				  b.putInt("idPerspectiva", 1);
				  b.putInt("idPadre",0);
				  
				  fragment.setArguments(b);
				  
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
			}
		});*/
		
		
		return rootView;
	}
}
