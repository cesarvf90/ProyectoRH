package pe.edu.pucp.proyectorh.reportes;


import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
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
		idPeriodo = getArguments().getInt("periodoSelec");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscPeriodoselec);
		textView.setText(titulo);
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridPerspectivas);
		
		
		
		
		return rootView;
	}
	
	public void cargarAvances(){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerAvanceXBCS + "?idperiodo=1";

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
	
	
	public class getObjetivos extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			BSCAvanceDTO bscAvance = gson.fromJson(result,BSCAvanceDTO.class);
			
			int [] arregloAvance = new int[4];
			
			arregloAvance[0]=(int)bscAvance.NotaFinalFinanciero;
			arregloAvance[1]=(int)bscAvance.NotaFinalAprendizaje;
			arregloAvance[2]=(int)bscAvance.NotaFinalCliente;
			arregloAvance[3]=(int)bscAvance.NotaFinalProcesosInternos;
			
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
         double NotaFinalFinanciero;
         double NotaFinalAprendizaje;
         double NotaFinalCliente;
         double NotaFinalProcesosInternos;
         
		public double getNotaFinalFinanciero() {
			return NotaFinalFinanciero;
		}
		public void setNotaFinalFinanciero(double notaFinalFinanciero) {
			NotaFinalFinanciero = notaFinalFinanciero;
		}
		public double getNotaFinalAprendizaje() {
			return NotaFinalAprendizaje;
		}
		public void setNotaFinalAprendizaje(double notaFinalAprendizaje) {
			NotaFinalAprendizaje = notaFinalAprendizaje;
		}
		public double getNotaFinalCliente() {
			return NotaFinalCliente;
		}
		public void setNotaFinalCliente(double notaFinalCliente) {
			NotaFinalCliente = notaFinalCliente;
		}
		public double getNotaFinalProcesosInternos() {
			return NotaFinalProcesosInternos;
		}
		public void setNotaFinalProcesosInternos(double notaFinalProcesosInternos) {
			NotaFinalProcesosInternos = notaFinalProcesosInternos;
		}
    }
	
	

}
