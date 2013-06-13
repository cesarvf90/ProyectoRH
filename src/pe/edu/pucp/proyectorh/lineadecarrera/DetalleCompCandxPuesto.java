package pe.edu.pucp.proyectorh.lineadecarrera;

import android.support.v4.app.Fragment;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.CompetenciaConPonderadoDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.OfertaLaboralMobileJefeDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.CandidatosxPuesto.PostulanteConCompetenciasDTO;
import pe.edu.pucp.proyectorh.reportes.ReporteServices;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

public class DetalleCompCandxPuesto extends Fragment{

	private WebView browser;
	int idConvocatoria;
	int idUsuario;
	private List<OfertaLaboralMobileJefeDTO> listaConvC;
	List<CompetenciaConPonderadoDTO> listaPuesto;
	List<PostulanteConCompetenciasDTO>listaColab;
	List<CompetenciaConPonderadoDTO> listaCompColab;
	String[] comp;
	int[] porpu;
	int[] poremp;
	
	public DetalleCompCandxPuesto(){
		
	}
	
	public class DataObject {
		
		String [] competencias;
		int []porccomp;
		int[]porcemp;
		
		public DataObject(String[] competencias, int[] porcomp, int[] porcemp){
			
			this.competencias = competencias;
			this.porccomp = porcomp;
			this.porcemp = porcemp;
		}
	}
	
	public class InterfaceChartLineal {
		Context mContext;
		DataObject obj;

	    /** Instantiate the interface and set the context */
	    InterfaceChartLineal(Context c, DataObject o) {
	        mContext = c;
	        obj = o;
	    }
	    
	    public String getData(){
	    	
	    	//DataObject obj = new DataObject();
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
		
		View rootView = inflater.inflate(R.layout.candidatosxpuesto4detalle,
				container, false);
		
		browser = (WebView)rootView.findViewById(R.id.detalleCompCandxPuesto1WebkitGLineal);
		
		TextView textView = (TextView)rootView.findViewById(R.id.DetalleCompCandxPuestoTitulografico);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		idConvocatoria = getArguments().getInt("ConvocatoriaID");
		idUsuario = getArguments().getInt("ColaboradorID");
		
		Sesion objetoSesion = new Sesion();
		List<OfertaLaboralMobileJefeDTO> Convocatorias = objetoSesion.getConvocatorias();
		
		listaConvC = Convocatorias;
		
		for(int i =0; i<listaConvC.size();i++){
			
			if (listaConvC.get(i).getID() == idConvocatoria){
				
				listaPuesto = listaConvC.get(i).getCompetenciasPonderadasPuesto();
				listaColab = listaConvC.get(i).getPostulantesConCompetencias();
				
				comp = new String[listaPuesto.size()];
				porpu = new int[listaPuesto.size()];
				//poremp = new int[listaColab.size()];
				
				for (int j=0; j<listaPuesto.size();j++){
					
				comp[j] = listaPuesto.get(j).getCompetenciaNombre();
				porpu[j]= listaPuesto.get(j).getPorcentaje();
				}
				
				for (int k=0; k<listaColab.size();k++){
					
					if (listaColab.get(k).getIdPostulante()== idUsuario ){
						
						listaCompColab = listaColab.get(k).getCompetenciasPostulante();
						poremp = new int[listaCompColab.size()];
						
						for(int l=0; l<listaCompColab.size();l++){
							
							poremp[l] = listaCompColab.get(l).getPorcentaje();
						}
						
					}
				}
			}
		}
		
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setPluginsEnabled(true);
		DataObject data = new DataObject(comp,porpu,poremp);
		
		InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
		browser.addJavascriptInterface(intface, "Android");
		
		browser.loadUrl("file:///android_asset/DetalleCompCandxPuestoPerbarchart.html");
		
		return rootView;
	}
	
}
