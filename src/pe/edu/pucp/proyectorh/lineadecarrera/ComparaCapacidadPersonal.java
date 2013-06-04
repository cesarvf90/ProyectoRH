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


public class ComparaCapacidadPersonal extends Fragment {

	int idConvocatoria;
	//GridView gridView;
	private WebView browser;
	private Spinner spinnerRequisitos;
	private Button btnDetalle;
	
	
	List<FuncionDTO> listaReq;
	List<OfertaLaboralMobilePostulanteDTO> listaConv;
	List<String> listaN ;
	
	
	public ComparaCapacidadPersonal(){
		
	}
	
	public class DataObject {
		
		//int[] avance = new int[] {100};
		//int[] avance2 = new int[] {84}; 
		
		double match = getArguments().getDouble("match");
		double[] arrmatch = new double[] {match};
		
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
		
		idConvocatoria = getArguments().getInt("ConvSelec");
		spinnerRequisitos = (Spinner) rootView.findViewById(R.id.comp_puesto_requi);
		listaN = new ArrayList<String>();
		
		
		obtenerlistaRequisitos();
		
		
		String tituloconv = getArguments().getString("titulo");
		String descrip = getArguments().getString("desc");
		
		String usuario = getArguments().getString("IdUsuario");
		
		TextView textView = (TextView)rootView.findViewById(R.id.CompCapConvSelec);
		textView.setText(tituloconv);
		
		TextView textView2 = (TextView)rootView.findViewById(R.id.comp_puesto_descrip);
		textView2.setText(descrip);
		
		btnDetalle = (Button) rootView.findViewById(R.id.detalleCompbtnConsultar);
		
		btnDetalle.setOnClickListener(new OnClickListener() {
			
			@Override
			  public void onClick(View v) {
				
				DetalleCompetencias fragment = new DetalleCompetencias();
				
				FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.opcion_detail_container, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
			
		});
		
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
	
	protected void obtenerlistaRequisitos(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			  String request = LineaCarServices.obtenerConvocatorias;
			  new getRequisitos().execute(request);
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
	
	public class getRequisitos extends AsyncCall {
		
		//webservice
		
		@Override
		protected void onPostExecute(String result) {
					
			System.out.println("Recibido: " + result.toString());
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<OfertaLaboralMobilePostulanteDTO> Convocatorias = gson.fromJson(result,
					new TypeToken<List<OfertaLaboralMobilePostulanteDTO>>(){}.getType());
			
			listaConv = Convocatorias;
			
			for(int i =0; i<listaConv.size();i++){
				
				if (listaConv.get(i).getID() == idConvocatoria){
					listaReq = listaConv.get(i).getFunciones(); 
					for (int j=0; j<listaReq.size();j++){
						listaN.add(listaReq.get(j).getNombre());
					}	
				}
			}
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaN);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerRequisitos.setAdapter(dataAdapter);
		}
	}
	
}
