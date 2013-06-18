package pe.edu.pucp.proyectorh.reportes;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPrincipal.getReportePeriodo;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ReporteObjetivosBSCPerspectivas extends Fragment {
	
	
	int idPeriodo;
	GridView gridView;
	int modo;
	String nomArch;
	TextView displayFecha;
	Button btnRefrescar;
	ProgressBar pbarra;
	 
	static final String[] perspectivas = new String[] { 
			"Financiera", "Formación", "Cliente", "Interno"};
	
	
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

		
		String titulo = getArguments().getString("titulo");
		idPeriodo = getArguments().getInt("PeriodoSelec");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscPeriodoselec);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		displayFecha = (TextView)rootView.findViewById(R.id.reportebscsDisplayfecha);
		pbarra = (ProgressBar) rootView.findViewById(R.id.reportebscprogressbarRef);
		
		
		modo = getArguments().getInt("modo");
		nomArch = getArguments().getString("archivo");
		if (modo==0) System.out.println("archivo: " + nomArch);
		
		btnRefrescar = (Button)rootView.findViewById(R.id.reportebscsActualizar);
		
		btnRefrescar.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
				  obtenerReporteOffline(idPeriodo);
				  
				  
			  }
		});
		
		
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridPerspectivas);
		
		cargarAvances(idPeriodo);
		
		
		return rootView;
	}
	
	protected void obtenerReporteOffline(int idPeriodo){
			
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				
				String request = "http://dp2kendo.apphb.com/Reportes/Reportes/ObjetivosOffline?idperiodo=" + idPeriodo;
	
				new getReportePeriodoRef().execute(request);
				
			} else {
				// Se muestra mensaje de error de conexion con el servicio
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Error de conexión");
				builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
			}
			
	}
	
	public class getReportePeriodoRef extends AsyncCall{
		
		@Override
		protected void onPreExecute(){
			pbarra.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			pbarra.setVisibility(View.INVISIBLE);
			
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

			PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(), nomArch);
			
			//actualizar gridview
			cargarAvances(idPeriodo);
			
			
		}
		
	}
	
	public void cargarAvances(int idperiodo){
		
		 if(modo==0){
			  //MODO OFFLINE
			  ArrayList<ObjetivoDTO> objetivos = PersistentHandler.getObjFromFile(getActivity(), nomArch);
			  ArrayList<ObjetivoDTO> objetivosEmpresa = new ArrayList<ObjetivoDTO>();
			  
			  String fechaarch = PersistentHandler.getFechaReporte(getActivity(), nomArch);
			  displayFecha.setText("Fecha de reporte: " + fechaarch);
			  
			  
			  
			  
			  
			  for (int i=0;i<objetivos.size();i++){
				 
				  if (objetivos.get(i).getIdpadre()==-1){
					  objetivosEmpresa.add(objetivos.get(i));
				  }
			  }
			  
			  int [] arregloAvance = new int[4];
			  
			  for (int i=0;i<objetivosEmpresa.size();i++){
				  
				  int bsc = objetivosEmpresa.get(i).getBSCId() -1;
				  if ((bsc>=0) && (bsc<=3)){
					  arregloAvance[bsc]= arregloAvance[bsc] +  ( objetivosEmpresa.get(i).getAvance()* objetivosEmpresa.get(i).getPeso() / 100);
				  }
				  
				  
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
						  b.putString("objetivopadre", "Perspectiva " + ((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText()  + " - Objetivos de la Empresa");
						  
						  b.putInt("idPeriodo", idPeriodo);
						  b.putInt("idPerspectiva", (position +1));
						  b.putInt("idPadre",0);
						  b.putInt("modo", modo);
						  b.putString("archivo", nomArch);
						  
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
					builder.setTitle("Error de conexión");
					builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
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
