package pe.edu.pucp.proyectorh.reportes;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.ROfertasLaborales;
import pe.edu.pucp.proyectorh.reportes.ReporteCubrimientoGrafico.getAvances;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ReportePersonalBSCGrafico extends Fragment {
	
	int idcolaborador;
	WebView browser;
	
	public ReportePersonalBSCGrafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportepersonalbsc2grafico,
				container, false);
		
		idcolaborador = getArguments().getInt("ColaboradorSelec");
		
		
		browser = (WebView)rootView.findViewById(R.id.reporteperbscWebkit);
		
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reporteperbscTitulografico);
		textView.setText(titulo);
		
		cargarGraficoHistorico(idcolaborador);
		
		return rootView;
	}
	
	public void cargarGraficoHistorico(int idcolab){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerHistoricoObjetivos + "?idColaborador=" + idcolab;

			new getAvances().execute(request);
			
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
	
	
	public class getAvances extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			List<HistoricoBSC> historico = gson.fromJson(result,new TypeToken<List<ROfertasLaborales>>(){}.getType());
			
			//habilitamos javascript y flash
			browser.getSettings().setJavaScriptEnabled(true);
			browser.getSettings().setPluginsEnabled(true);
			/*
			DataObject data = new DataObject();
			
			InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
			
			browser.addJavascriptInterface(intface, "Android");
			*/
			
			browser.loadUrl("file:///android_asset/ReporteBSCpersonal.html");
			
			
		}
	}
	
	
	public class HistoricoBSC
	{
	        private int idperiodo;

	        private String nombreColaborador;

	        private List<ObjetivoDTO> objetivos;

			public int getIdperiodo() {
				return idperiodo;
			}

			public void setIdperiodo(int idperiodo) {
				this.idperiodo = idperiodo;
			}

			public String getNombreColaborador() {
				return nombreColaborador;
			}

			public void setNombreColaborador(String nombreColaborador) {
				this.nombreColaborador = nombreColaborador;
			}

			public List<ObjetivoDTO> getObjetivos() {
				return objetivos;
			}

			public void setObjetivos(List<ObjetivoDTO> objetivos) {
				this.objetivos = objetivos;
			}
	         
	         
	}
		
	

}
