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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ReporteObjetivosBSCPerspectivas extends Fragment {
	
	
	int idPeriodo;
	GridView gridView;
	int modo;
	 
	static final String[] perspectivas = new String[] { 
			"Financiera", "Formaci�n", "Cliente", "Interno"};
	
	
	public ReporteObjetivosBSCPerspectivas(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc2perspectivas,
				container, false);

		
		String titulo = getArguments().getString("titulo") + " - Objetivos de la Empresa";
		idPeriodo = getArguments().getInt("PeriodoSelec");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscPeriodoselec);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		modo = getArguments().getInt("modo");
		
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridPerspectivas);
		
		cargarAvances(idPeriodo);
		
		
		return rootView;
	}
	
	public void cargarAvances(int idperiodo){
		
		 if(modo==0){
			  //MODO OFFLINE
			  ArrayList<ObjetivoDTO> objetivos = PersistentHandler.getObjFromFile(getActivity(), "reporteRH.txt");
			  ArrayList<ObjetivoDTO> objetivosEmpresa = new ArrayList<ObjetivoDTO>();
			  
			  for (int i=0;i<objetivos.size();i++){
				 
				  // if (objetivos.get(i).g)
			  }
			  
			  int [] arregloAvance = new int[4];
			  
			  for (int i=0;i<4;i++){
				  int promedio = 0;
				  int suma = 0;
				  for (int j=0;j<objetivosEmpresa.size();j++){
					    
				  }
				  arregloAvance[0]=promedio;
			  }
				
				gridView.setAdapter(new PerspectivaAdapter(getActivity(), perspectivas, arregloAvance));
				
				gridView.setOnItemClickListener(new OnItemClickListener() {
					
					
					@Override
					public void onItemClick (AdapterView<?> parent, View v,
							int position, long id) {
						/*
					   Toast.makeText(v.getContext(),
						((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText(), Toast.LENGTH_SHORT).show();
						*/
						
						  ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
					      
						  Bundle b = new Bundle();
						  b.putInt("nivel",0);
						  b.putString("objetivopadre", "Perspectiva " + ((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText());
						  
						  b.putInt("idPeriodo", idPeriodo);
						  b.putInt("idPerspectiva", (position +1));
						  b.putInt("idPadre",0);
						  b.putInt("modo", modo);
						  
						  fragment.setArguments(b);
						  
					      
						  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						  ft.replace(R.id.opcion_detail_container, fragment);
						  ft.addToBackStack(null);
						  ft.commit();
					}
				});
				
		
			
		 }
		 else{
			 //MODO ONLINE
			 
			 if (ConnectionManager.connect(getActivity())) {
					// construir llamada al servicio
					String request = ReporteServices.obtenerAvanceXBCS + "?idperiodo=" + idperiodo;
		
					new getObjetivos().execute(request);
					
				} else {
					// Se muestra mensaje de error de conexion con el servicio
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setTitle("Error de conexi�n");
					builder.setMessage("No se pudo conectar con el servidor. Revise su conexi�n a Internet.");
					builder.setCancelable(false);
					builder.setPositiveButton("Ok", null);
					builder.create();
					builder.show();
				}
			 
			 
		 }
		
		
		
	}
	
	
	public class getObjetivos extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<BSCAvanceDTO> bscAvance = gson.fromJson(result,new TypeToken<List<BSCAvanceDTO>>(){}.getType());
			
			int [] arregloAvance = new int[4];
			
			arregloAvance[0]=bscAvance.get(0).NotaFinalFinanciero;
			arregloAvance[1]=bscAvance.get(0).NotaFinalAprendizaje;
			arregloAvance[2]=bscAvance.get(0).NotaFinalCliente;
			arregloAvance[3]=bscAvance.get(0).NotaFinalProcesosInternos;
			
			gridView.setAdapter(new PerspectivaAdapter(getActivity(), perspectivas, arregloAvance));
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				
				
				@Override
				public void onItemClick (AdapterView<?> parent, View v,
						int position, long id) {
					/*
				   Toast.makeText(v.getContext(),
					((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText(), Toast.LENGTH_SHORT).show();
					*/
					
					  ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
				      
					  Bundle b = new Bundle();
					  b.putInt("nivel",0);
					  b.putString("objetivopadre", "Perspectiva " + ((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText());
					  
					  b.putInt("idPeriodo", idPeriodo);
					  b.putInt("idPerspectiva", (position +1));
					  b.putInt("idPadre",0);
					  b.putInt("modo", modo);
					  
					  fragment.setArguments(b);
					  
				      
					  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
					  ft.replace(R.id.opcion_detail_container, fragment);
					  ft.addToBackStack(null);
					  ft.commit();
				}
			});
			
			
		}
	
	}
	
	public class BSCAvanceDTO
    {
        private int NotaFinalFinanciero;
        private int NotaFinalAprendizaje;
        private int NotaFinalCliente;
        private int NotaFinalProcesosInternos;
         
		public int getNotaFinalFinanciero() {
			return NotaFinalFinanciero;
		}
		public void setNotaFinalFinanciero(int notaFinalFinanciero) {
			NotaFinalFinanciero = notaFinalFinanciero;
		}
		public int getNotaFinalAprendizaje() {
			return NotaFinalAprendizaje;
		}
		public void setNotaFinalAprendizaje(int notaFinalAprendizaje) {
			NotaFinalAprendizaje = notaFinalAprendizaje;
		}
		public int getNotaFinalCliente() {
			return NotaFinalCliente;
		}
		public void setNotaFinalCliente(int notaFinalCliente) {
			NotaFinalCliente = notaFinalCliente;
		}
		public int getNotaFinalProcesosInternos() {
			return NotaFinalProcesosInternos;
		}
		public void setNotaFinalProcesosInternos(int notaFinalProcesosInternos) {
			NotaFinalProcesosInternos = notaFinalProcesosInternos;
		}
    }
	
	

}
