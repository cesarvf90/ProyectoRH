package pe.edu.pucp.proyectorh.lineadecarrera;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidadPersonal.DataObject;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidadPersonal.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.webkit.WebView;

public class GraficoMatchCandidatosxPuesto extends Fragment {
	
	private WebView browser;
	private Button btnDetalle;
	private int idConv;
	private int idColab;
	
	public GraficoMatchCandidatosxPuesto(){
			
	}

	public class DataObject {
		
		int match = getArguments().getInt("MatchLevel");
		int[] arrmatch = new int[] {match};
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
		
		View rootView = inflater.inflate(R.layout.candidatosxpuesto3grafico,
				container, false);
		
		String NombreColab = getArguments().getString("ColaboradorNom");
		TextView textView = (TextView)rootView.findViewById(R.id.DetalleCompCanxPuestoNombreColab);
		textView.setText(NombreColab);
		
		idConv = getArguments().getInt("ConvocatoriaID");
		idColab = getArguments().getInt("ColaboradorID");
		
		browser = (WebView)rootView.findViewById(R.id.CanxPuestodetalleComp1WebkitGLineal);
		
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		browser.loadUrl("file:///android_asset/ComparaCapCandxPuestobarchart.html");
		
		btnDetalle = (Button) rootView.findViewById(R.id.CanxPuestobtnDetalle);
		
		btnDetalle.setOnClickListener(new OnClickListener() {
			
			@Override
			  public void onClick(View v) {
				
				DetalleCompCandxPuesto fragment = new DetalleCompCandxPuesto();
				
				Bundle argumentos = new Bundle();
				argumentos.putInt("ColaboradorID", idColab);
				argumentos.putInt("ConvocatoriaID",idConv);
				
				fragment.setArguments(argumentos);
				
				FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.opcion_detail_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
			
		});
		
		return rootView;
	}
}
