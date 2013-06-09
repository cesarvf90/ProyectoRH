package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Objetivo;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCObjetivos.getObjetivos;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeObjetivos;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReporteObjetivosBSCPersonales extends Fragment {
	
	
	int nivel;
	String objpadre;
	ArrayList<ObjetivoDTO> listaObjetivos;
	
	int idPadre;
	int idPersp;
	int idPeriodo;
	
	ExpandableListView expandObjetivos;
	
	public ReporteObjetivosBSCPersonales(){
		
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc3objpersonal,
				container, false);
		
		//obtener argumentos
		idPeriodo = getArguments().getInt("idPeriodo");
		idPersp = getArguments().getInt("idPerspectiva");
		idPadre = getArguments().getInt("idPadre");
		
		System.out.println("periodo: " + idPeriodo);
		System.out.println("persp: " + idPersp);
		System.out.println("padre: " + idPadre);

		String titulo = getArguments().getString("objetivopadre");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscObjetivopadre2);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		expandObjetivos = (ExpandableListView) rootView.findViewById(R.id.reportebscexpandable);
		
		cargarObjetivos(idPadre,idPeriodo,idPersp);
		
		
		
		
		
		return rootView;
	}
	
	
	public void cargarObjetivos (int idobjPadre, int idPeriodo, int idPerspectiva){
		
		if (idobjPadre == 0){
			//NIVEL 0
			
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				String request = ReporteServices.obtenerObjetivosXBCS + "?BSCId=" + idPerspectiva + "&idperiodo=" + idPeriodo;

				new getObjetivosPersonales().execute(request);
				
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
		else {
			//conseguir por idPadre
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				String request = ReporteServices.obtenerObjetivosXPadre + "?PadreId=" + idobjPadre;


				new getObjetivosPersonales().execute(request);
				
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
	}
	
	public class getObjetivosPersonales extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			listaObjetivos = gson.fromJson(result,
					new TypeToken<List<ObjetivoDTO>>(){}.getType());
			
			System.out.println("pase gson :D");
			
			/*harcode!*/
			
			ArrayList<String> personas = new ArrayList<String>();
			personas.add("andre");
			personas.add("juan perez");
			
			ArrayList<ArrayList<ObjetivoDTO>> objetivos = new ArrayList<ArrayList<ObjetivoDTO>>();
			
			for (int i=0;i<personas.size();i++){
				
				objetivos.add(listaObjetivos);
			}

			
			/**/
			ObjetivoPersonalAdapter adaptador = new ObjetivoPersonalAdapter(getActivity().getApplicationContext(), personas, objetivos);
			expandObjetivos.setAdapter(adaptador);
	
		}
		
	}
	
	

public class ObjetivoPersonalAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<String> personas;
	private ArrayList<ArrayList<ObjetivoDTO>> objetivos;
	private Context context;
	ArrayList<ObjetivoDTO> objetivosPersonal;
	
	GridView gridView;
	
	public ObjetivoPersonalAdapter(Context contexto, ArrayList<String> personas, ArrayList<ArrayList<ObjetivoDTO>> objetivos) {
		this.context = contexto;
		this.personas = personas;
		this.objetivos = objetivos;
		System.out.println("entro a constructor");
	}
	
	/*
	@Override
	public ArrayList<ObjetivoDTO> getChild(int groupPosition, int childPosition) {
		return objetivos.get(groupPosition).get(childPosition);
	}
	*/
	
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		System.out.println("entro a childview");
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		View view;
		 
		if (convertView == null) {
			
			view = new View(context);

			view = inflater.inflate(R.layout.reportebscgridadapter, null);
			
			gridView = (GridView) view.findViewById(R.id.reportebscgridObjetivosPersonales);
			
			gridView.setAdapter(new ObjetivoAdapter(context,objetivos.get(groupPosition)));
			
			objetivosPersonal = objetivos.get(groupPosition);
			
			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
					
					if (objetivosPersonal.get(position).getHijos()==0) {
						
						Toast.makeText(v.getContext(),
								"Objetivo de último nivel", Toast.LENGTH_SHORT).show();
					}
					else{
						
						
						Bundle b = new Bundle();
						b.putInt("nivel",nivel + 1);
						//String cadena = "" + ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
						String cadena = objetivosPersonal.get(position).getDescripcion();
						b.putString("objetivopadre", cadena);
						
						b.putInt("idPadre",objetivosPersonal.get(position).getIdObjetivo());
						
						ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
						fragment.setArguments(b);
						
						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();
						
					}
				   
				}
			});
			
			
			
		}
		else{
			view = convertView;
		}
 
		return view;
			

	}
	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		System.out.println("entro a groupview");

		String nombre = getGroup(groupPosition);

		if (convertView == null) {
			System.out.println("entro aqui groupview2");
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
			// R.layout.expandablelistview_group, null);
					R.layout.reportebscheaderpersona, null);
		}

		TextView grouptxt = (TextView) convertView
				.findViewById(R.id.reportebscTextViewGroup);
		grouptxt.setText(nombre);

		return convertView;
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public String getGroup(int groupPosition) {
		return personas.get(groupPosition);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return personas.size();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

	}

	
	

}
