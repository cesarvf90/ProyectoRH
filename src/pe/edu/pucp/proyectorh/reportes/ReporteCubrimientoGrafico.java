package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.DataObject;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.InterfaceChartLineal;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.ObjetivosXPersonaRDTO;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCGrafico.getAvances;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class ReporteCubrimientoGrafico extends Fragment{
	
	private WebView browser;
	int idpuesto;
	String fechaini;
	String fechafin;
	
	public ReporteCubrimientoGrafico(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportecubrimientografico,
				container, false);
		
		idpuesto = getArguments().getInt("PuestoSelec");
		
		 fechaini = getArguments().getString("fechaini");
		 fechafin = getArguments().getString("fechafin");
		System.out.println("fechaini " + fechaini);
		System.out.println("fechafin " + fechafin);
		
		
		browser = (WebView)rootView.findViewById(R.id.reportecubWebkit);
		
		String titulo = getArguments().getString("titulo");
		// set value into textview
		TextView textView = (TextView)rootView.findViewById(R.id.reportecubTitulografico);
		textView.setText(titulo);
		
		cargarGraficoPuesto(idpuesto);
		
		return rootView;
	}
	
	public void cargarGraficoPuesto(int idpuesto){
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = ReporteServices.obtenerPostulaciones + "?idpuesto=" + idpuesto + "&finicio=" + fechaini + "&ffin=" + fechafin;

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
			List<ROfertasLaborales> ofertas;
			try{
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			ofertas = gson.fromJson(result,new TypeToken<List<ROfertasLaborales>>(){}.getType());
			
			}
			catch(Exception e){
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Error de datos");
				builder.setMessage("No se pudo recuperar la información.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
				return;
			}
			
			if (ofertas.size()==0){
				 String summary = "<html><body>No se encontraron resultados</body></html>";
				 browser.loadData(summary, "text/html", null);
			}
			else{
				
				ArrayList<String> arrayUniv = new ArrayList<String>();
				ArrayList<Integer> bachiller = new ArrayList<Integer>();
				ArrayList<Integer> master = new ArrayList<Integer>();
				ArrayList<Integer> doctorado = new ArrayList<Integer>();
				ArrayList<Integer> tecnico = new ArrayList<Integer>();
				ArrayList<Integer> estudiante = new ArrayList<Integer>();
				ArrayList<Integer> licenciado = new ArrayList<Integer>();
				
				for (int i=0;i<ofertas.size();i++){
					boolean encontrado=false;
					int pos=0;
					for (int j=0;j<arrayUniv.size();j++){
						if (ofertas.get(i).getNombreProveniencia().equals(arrayUniv.get(j))) {
							encontrado = true;
							pos = j;
						}
						
					}
					
					if (!encontrado){
						pos = arrayUniv.size();
						arrayUniv.add(ofertas.get(i).getNombreProveniencia());
						bachiller.add(0);
						master.add(0);
						doctorado.add(0);
						tecnico.add(0);
						estudiante.add(0);
						licenciado.add(0);
						
					}
					
					if (ofertas.get(i).getGradoAcademico().equals("Bachiller")) bachiller.set(pos, ofertas.get(i).getCantPostulantes());
					if (ofertas.get(i).getGradoAcademico().equals("Master")) master.set(pos, ofertas.get(i).getCantPostulantes());
					if (ofertas.get(i).getGradoAcademico().equals("Doctor")) doctorado.set(pos, ofertas.get(i).getCantPostulantes());
					if (ofertas.get(i).getGradoAcademico().equals("Técnico")) tecnico.set(pos, ofertas.get(i).getCantPostulantes());
					if (ofertas.get(i).getGradoAcademico().equals("Estudiante")) estudiante.set(pos, ofertas.get(i).getCantPostulantes());
					if (ofertas.get(i).getGradoAcademico().equals("Licenciado")) licenciado.set(pos, ofertas.get(i).getCantPostulantes());
					
				}
				
				//habilitamos javascript y flash
				browser.getSettings().setJavaScriptEnabled(true);
				browser.getSettings().setPluginsEnabled(true);
				
				DataObject data = new DataObject(arrayUniv, bachiller, master, doctorado, estudiante,tecnico ,licenciado );
				
				InterfaceChartLineal intface = new InterfaceChartLineal(getActivity(),data);
				
				browser.addJavascriptInterface(intface, "Android");
				
				
				browser.loadUrl("file:///android_asset/ReporteCubrimientochart.html");
			}
			
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
	    	
	    	
	    	Gson gson = new Gson();
	    	 
	    	// convert java object to JSON format,
	    	// and returned as JSON formatted string
	    	String json = gson.toJson(obj);
	    	System.out.println(json);
	    	return json;
	    }


	}
	
	public class DataObject {
		
		String[] univ ;
		
		int[] bachiller;
		int[] master;
		int[] doctorado;
		int[] tecnico;
		int[] licenciado;
		int[] estudiante; 
		
		public DataObject(ArrayList<String> arrayUniv, ArrayList<Integer> arrayBach, ArrayList<Integer> arrayMaster,ArrayList<Integer> arrayDoctor, ArrayList<Integer> arrayEst, ArrayList<Integer> arrayTec,ArrayList<Integer> arrayLic){
			
			univ= new String[arrayUniv.size()];
			bachiller = new int[arrayUniv.size()];
			master = new int[arrayUniv.size()];
			doctorado = new int[arrayUniv.size()];
			tecnico = new int[arrayUniv.size()];
			licenciado = new int[arrayUniv.size()];
			estudiante = new int[arrayUniv.size()];
			
			for (int i=0;i<arrayUniv.size();i++){
				univ[i] = arrayUniv.get(i);
				bachiller[i] = arrayBach.get(i);
				master[i] = arrayMaster.get(i);
				doctorado[i] = arrayMaster.get(i);
				tecnico[i] = arrayTec.get(i);
				licenciado[i] = arrayLic.get(i);
				estudiante[i] = arrayEst.get(i);
			}
			
		}
		

	}
	
	public class ROfertasLaborales
	{
	        private String nombreProveniencia;
	        private String gradoAcademico;
	        
	        public String getNombreProveniencia() {
				return nombreProveniencia;
			}
			public void setNombreProveniencia(String nombreProveniencia) {
				this.nombreProveniencia = nombreProveniencia;
			}
			public String getGradoAcademico() {
				return gradoAcademico;
			}
			public void setGradoAcademico(String gradoAcademico) {
				this.gradoAcademico = gradoAcademico;
			}
			public int getCantPostulantes() {
				return cantPostulantes;
			}
			public void setCantPostulantes(int cantPostulantes) {
				this.cantPostulantes = cantPostulantes;
			}
			public int getCantElegidos() {
				return cantElegidos;
			}
			public void setCantElegidos(int cantElegidos) {
				this.cantElegidos = cantElegidos;
			}
			
			private int cantPostulantes;
			private int cantElegidos;
	}
	
	

}
