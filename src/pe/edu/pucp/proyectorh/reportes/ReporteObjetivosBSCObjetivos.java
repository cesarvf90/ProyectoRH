package pe.edu.pucp.proyectorh.reportes;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ReporteObjetivosBSCObjetivos extends Fragment {
	
	GridView gridView;

	
	int nivel;
	String objpadre;
	ArrayList<ObjetivoDTO> listaObjetivos;
	
	int idPadre;
	int idPersp;
	int idPeriodo;
	
	int modo;
	
	public ReporteObjetivosBSCObjetivos(){
		
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc3objetivos,
				container, false);
		
		//obtener argumentos
		idPeriodo = getArguments().getInt("idPeriodo");
		idPersp = getArguments().getInt("idPerspectiva");
		idPadre = getArguments().getInt("idPadre");
		modo = getArguments().getInt("modo");
		
		System.out.println("periodo: " + idPeriodo);
		System.out.println("persp: " + idPersp);
		System.out.println("padre: " + idPadre);
		System.out.println("modo: " + modo);
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridObjetivos);
		//llamar a WS
		cargarObjetivos(idPadre,idPeriodo,idPersp);
		
		String titulo = getArguments().getString("objetivopadre");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscObjetivopadre);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		
		return rootView;
	}
	
	public void cargarObjetivos (int idobjPadre, int idPeriodo, int idPerspectiva){
		
		if (idobjPadre == 0){
			//NIVEL 0
			if (modo==1){
				//ONLINE
			
				if (ConnectionManager.connect(getActivity())) {
					// construir llamada al servicio
					String request = ReporteServices.obtenerObjetivosXBCS + "?BSCId=" + idPerspectiva + "&idperiodo=" + idPeriodo;
	
					new getObjetivos().execute(request);
					
				} else {
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
			else{
				//MODO OFFLINE
				ArrayList<ObjetivoDTO> objetivosArch = PersistentHandler.getObjFromFile(getActivity(), "reporteRH.txt");
				
				//obtener por bsc y idperiodo
				listaObjetivos = objetivosArch;
				setgridview();
				
			}
			
			
		}
		else {
			
			if (modo==1){
				//MODO ONLINE
			
			//conseguir por idPadre
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				String request = ReporteServices.obtenerObjetivosXPadre + "?PadreId=" + idobjPadre;


				new getObjetivos().execute(request);
				
			} else {
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
			else{
				//MODO OFFLINE
				ArrayList<ObjetivoDTO> objetivosArch = PersistentHandler.getObjFromFile(getActivity(), "reporteRH.txt");
				//obtener por padre
				listaObjetivos = objetivosArch;
				setgridview();
				
				
			}
			
			
			
		}
		
		
		
		
	}
	
	public class getObjetivos extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			listaObjetivos = gson.fromJson(result,
					new TypeToken<List<ObjetivoDTO>>(){}.getType());
			
			setgridview();
			
			
		}
	
	}
	
	public void setgridview(){
		gridView.setAdapter(new ObjetivoAdapter(getActivity(),listaObjetivos));
		
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				
				if (listaObjetivos.get(position).getHijos()==0) {
					
					Toast.makeText(v.getContext(),
							"Objetivo de último nivel", Toast.LENGTH_SHORT).show();
				}
				else{
					
					
					Bundle b = new Bundle();
					b.putInt("nivel",nivel + 1);
					//String cadena = "" + ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
					String cadena = listaObjetivos.get(position).getDescripcion();
					b.putString("objetivopadre", cadena);
					
					b.putInt("idPadre",listaObjetivos.get(position).getIdObjetivo());
					b.putInt("modo", modo);
					
					
					
					if(listaObjetivos.get(position).isEsIntermedio()){
						ReporteObjetivosBSCPersonales fragment = new ReporteObjetivosBSCPersonales();
						fragment.setArguments(b);
						
						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();
					}
					else{
						
						ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
						fragment.setArguments(b);
						
						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();
						
					}
					
					
					
				}
			   
			}
		});
		
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
					
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View v,
						int position, long id) {
		
						
						Bundle b = new Bundle();
						String cadena = "" +  ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
						b.putString("titulo", cadena);
						b.putInt("idObjetivo",listaObjetivos.get(position).getIdObjetivo());
						b.putInt("modo", modo);
						
						ReporteObjetivosBSCGrafico fragment = new ReporteObjetivosBSCGrafico();
						fragment.setArguments(b);
						
						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();
							
						
						return true;
					   
					}
		
				});
	}
	
	
}
