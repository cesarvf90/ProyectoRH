package pe.edu.pucp.proyectorh.lineadecarrera;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.CompetenciaConPonderadoDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.FuncionDTO;
import pe.edu.pucp.proyectorh.lineadecarrera.ComparaCapacidad.OfertaLaboralMobilePostulanteDTO;
import pe.edu.pucp.proyectorh.reportes.ReporteServices;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.ObjetivosXPersonaRDTO;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.getAvances;
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

public class DetalleCompetencias extends Fragment  {

	private WebView browser;
	int objSelec;
	private String usuario;
	List<OfertaLaboralMobilePostulanteDTO> listaConv;
	List<CompetenciaConPonderadoDTO> listaPuesto;
	List<CompetenciaConPonderadoDTO> listaColab;
	int idConvocatoria;
	String[] comp;
	int[] porpu;
	int[] poremp;
	
	public DetalleCompetencias(){
		
	}
	
	
	
	public class DataObject {
		//String[] lineax = new String[] { 
				//"Área-Puesto"};
		
		//int[] avance = new int[] {100};
		//int[] avance2 = new int[] {84}; 
		
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
		
		View rootView = inflater.inflate(R.layout.detallecomp1,
				container, false);
		
		browser = (WebView)rootView.findViewById(R.id.detalleComp1WebkitGLineal);
		
		TextView textView = (TextView)rootView.findViewById(R.id.DetalleCompTitulografico);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		usuario = LoginActivity.usuario.getUsername();
		idConvocatoria = getArguments().getInt("ConvSelec");
		
		//ListView listaNombreCompetencia = (ListView) rootView.findViewById(R.id.lista_nombre_competencias);
		cargarArreglos(usuario);
		
		//habilitamos javascript y flash
		//browser.getSettings().setJavaScriptEnabled(true);
		//browser.getSettings().setPluginsEnabled(true);
		//browser.addJavascriptInterface(new InterfaceChartLineal(rootView.getContext()), "Android");
		
		//browser.loadUrl("file:///android_asset/DetalleCompPerbarchart.html");
		return rootView;
	}
	
	public void cargarArreglos(String usuario){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = LineaCarServices.obtenerConvocatorias + "?userName=" + usuario;
			new getArreglos().execute(request);
			
		}else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Error de conexción");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	}
	
	public class getArreglos extends AsyncCall {
		
		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<OfertaLaboralMobilePostulanteDTO> Convocatorias = gson.fromJson(result,new TypeToken<List<OfertaLaboralMobilePostulanteDTO>>(){}.getType());
			
			//String [] compe = new String[Convocatorias.];
			
			listaConv = Convocatorias;
			
			for(int i =0; i<listaConv.size();i++){
				
				if (listaConv.get(i).getID() == idConvocatoria){
					
					listaPuesto = listaConv.get(i).getCompetenciasPonderadasPuesto();
					listaColab = listaConv.get(i).getCompetenciasPonderadasColaborador();
					
					comp = new String[listaPuesto.size()];
					porpu = new int[listaPuesto.size()];
					poremp = new int[listaColab.size()];
					
					for (int j=0; j<listaPuesto.size();j++){
						
						comp[j] = listaPuesto.get(j).getCompetenciaNombre();
						porpu[j]= listaPuesto.get(j).getPorcentaje();
						poremp[j]= listaColab.get(j).getPorcentaje();
					}
				}
			}
			
			browser.getSettings().setJavaScriptEnabled(true);
			browser.getSettings().setPluginsEnabled(true);
			DataObject data = new DataObject(comp,porpu,poremp);
			
			InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
			browser.addJavascriptInterface(intface, "Android");
			
			browser.loadUrl("file:///android_asset/DetalleCompPerbarchart.html");
			
		}
	}
}
