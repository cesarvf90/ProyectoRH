package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.R.color;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
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
	ArrayList<PersonaXObjetivoDTO> listaObjetivosxPersona;
	
	
	int idPadre;
	int idPersp;
	int idPeriodo;
	int modo;
	String nomArch;
	
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
		modo = getArguments().getInt("modo");
		nomArch = getArguments().getString("archivo");
		
		System.out.println("periodo: " + idPeriodo);
		System.out.println("persp: " + idPersp);
		System.out.println("padre: " + idPadre);
		System.out.println("modo: " + modo);
		if (modo==0) System.out.println("archivo: " + nomArch);

		String titulo = getArguments().getString("objetivopadre");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscObjetivopadre2);
		textView.setText(titulo);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		textView.setTypeface(font);
		
		expandObjetivos = (ExpandableListView) rootView.findViewById(R.id.reportebscexpandable);
		
		cargarObjetivos(idPadre,idPeriodo,idPersp);

		customizarEstilos(getActivity(), rootView);
		
		return rootView;
	}
	
	
	public void cargarObjetivos (int idobjPadre, int idPeriodo, int idPerspectiva){
		
		if (modo==1){
			//MODO ONLINE
			//conseguir por idPadre
			if (ConnectionManager.connect(getActivity())) {
				// construir llamada al servicio
				String request = ReporteServices.obtenerPersonasXObjetivo + "?idObjetivo=" + idobjPadre;


				new getObjetivosPersonales().execute(request);
				
			} else {
				// Se muestra mensaje de error de conexion con el servicio
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Error de conexci�n");
				builder.setMessage("No se pudo conectar con el servidor. Revise su conexi�n a Internet.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
			}
		}
		else{
			//MODO OFFLINE
			
			ArrayList<ObjetivoDTO> objetivosArch = PersistentHandler.getObjFromFile(getActivity(), nomArch);
			if (objetivosArch!=null){
			
				ArrayList<List<ObjetivoDTO>> objetivos = new ArrayList<List<ObjetivoDTO>>();
				ArrayList<String> personas = new ArrayList<String>();
				
				//obtener por padre
				for (int i=0;i<objetivosArch.size();i++){
					if (objetivosArch.get(i).getIdpadre()==idobjPadre){
						
						//este objetivo es el de personaxobjetivo, se muestran sus hijos
						
						//validacion si colaborador es null no mostrar
						if (objetivosArch.get(i).getColaboradorNombre()!=null){
							System.out.println(objetivosArch.get(i).getColaboradorNombre());
							personas.add(objetivosArch.get(i).getColaboradorNombre());
							
							ArrayList<ObjetivoDTO> listaHijos = new ArrayList<ObjetivoDTO> (); 
							
							//obtener hijos
							for (int j=0;j<objetivosArch.size();j++){
								if ((objetivosArch.get(j).getIdpadre()==objetivosArch.get(i).getIdObjetivo()) && 
								     (objetivosArch.get(j).getColaboradorID() == objetivosArch.get(i).getColaboradorID())){
									listaHijos.add(objetivosArch.get(j));
								}
									
							}
							
							objetivos.add(listaHijos);
						}
						
					}
				}
				
				ObjetivoPersonalAdapter adaptador = new ObjetivoPersonalAdapter(getActivity().getApplicationContext(), personas, objetivos);
				expandObjetivos.setAdapter(adaptador);
				for(int i=0; i < adaptador.getGroupCount(); i++){
					expandObjetivos.expandGroup(i);
				}
			
			}
		}
			
			
			
		
	}
	
	public class getObjetivosPersonales extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			
			Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new NetDateTimeAdapter()).create();
			listaObjetivosxPersona = gson.fromJson(result,
					new TypeToken<List<PersonaXObjetivoDTO>>(){}.getType());
			
			ArrayList<String> personas = new ArrayList<String>();
			ArrayList<List<ObjetivoDTO>> objetivos = new ArrayList<List<ObjetivoDTO>>();
			for (int i=0;i<listaObjetivosxPersona.size();i++){
				if (listaObjetivosxPersona.get(i).getNombreColaborador()!= null){
					personas.add(listaObjetivosxPersona.get(i).getNombreColaborador());
					objetivos.add(listaObjetivosxPersona.get(i).getObjetivos());
				}
				
			}
			

			ObjetivoPersonalAdapter adaptador = new ObjetivoPersonalAdapter(getActivity().getApplicationContext(), personas, objetivos);
			expandObjetivos.setAdapter(adaptador);
	
		}
		
	}
	
	
public class ObjetivoPersonalAdapter extends BaseExpandableListAdapter {

	private ArrayList<String> personas;
	private ArrayList<List<ObjetivoDTO>> objetivos;
	private Context context;
    int gpglobal;
	ArrayList<GridView> listaGridView ;

	public ObjetivoPersonalAdapter(Context contexto, ArrayList<String> personas, ArrayList<List<ObjetivoDTO>> objetivos) {
		this.context = contexto;
		this.personas = personas;
		this.objetivos = objetivos;
		listaGridView = new ArrayList<GridView>(); 
		for (int i=0;i<personas.size();i++){
			listaGridView.add(null);
		}
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		View view;

		
			
			System.out.println("entra a setgridview...");

			view = new View(context);

			view = inflater.inflate(R.layout.reportebscgridadapter, null);

			listaGridView.set(groupPosition,  (GridView) view.findViewById(R.id.reportebscgridObjetivosPersonales));

			listaGridView.get(groupPosition).setAdapter(new ObjetivoAdapter(context,objetivos.get(groupPosition)));

			gpglobal = groupPosition;

			listaGridView.get(groupPosition).setOnItemClickListener(new OnItemClickListener() {
				int gp = gpglobal;
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
					parent.getParent();
					/*
					System.out.print("(grouppos: " + gp +", ppos: " + position + ")");
					System.out.print("(size: " + objetivos.get(gp).size() + ")");
					System.out.println(" hijos:" + objetivos.get(gp).get(position).getHijos()
							+ " descp: " + objetivos.get(gp).get(position).getDescripcion() );

						Toast.makeText(parent.getContext(), 
					"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
					Toast.LENGTH_SHORT).show(); */
					
					if (objetivos.get(gp).get(position).getHijos()==0) {

						Toast.makeText(v.getContext(),
								"Objetivo de �ltimo nivel", Toast.LENGTH_SHORT).show();
					}
					else{


						Bundle b = new Bundle();
						b.putInt("nivel",nivel + 1);
						//String cadena = "" + ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
						String cadena = objetivos.get(gp).get(position).getDescripcion();
						b.putString("objetivopadre", cadena);

						b.putInt("idPadre",objetivos.get(gp).get(position).getIdObjetivo());
						b.putInt("modo",modo);
						b.putString("archivo", nomArch);

						ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
						fragment.setArguments(b);

						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();

					}

				}
			});

			ViewGroup.LayoutParams layoutParams = listaGridView.get(groupPosition).getLayoutParams();
			layoutParams.height = 100*((objetivos.get(groupPosition).size() + 1)/2); //this is in pixels 100 -> tablet, 200->cel
			listaGridView.get(groupPosition).setLayoutParams(layoutParams);
			
		
 
		return view;


	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		String nombre = getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
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

	private void customizarEstilos(Context context, View view) {
		try {
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				for (int i = 0; i < vg.getChildCount(); i++) {
					View child = vg.getChildAt(i);
					customizarEstilos(context, child);
				}
			} else if (view instanceof TextView) {
			((TextView) view).setTextColor(Color.BLACK);
		}
		} catch (Exception e) {
		}
	}


	private class PersonaXObjetivoDTO
	{
	    private int avance;
	   
	    private String nombreColaborador;
	
	    private int idObjetivo;
	
	    private List<ObjetivoDTO> objetivos;

		public int getAvance() {
			return avance;
		}

		public void setAvance(int avance) {
			this.avance = avance;
		}

		public String getNombreColaborador() {
			return nombreColaborador;
		}

		public void setNombreColaborador(String nombreColaborador) {
			this.nombreColaborador = nombreColaborador;
		}

		public int getIdObjetivo() {
			return idObjetivo;
		}

		public void setIdObjetivo(int idObjetivo) {
			this.idObjetivo = idObjetivo;
		}

		public List<ObjetivoDTO> getObjetivos() {
			return objetivos;
		}

		public void setObjetivos(List<ObjetivoDTO> objetivos) {
			this.objetivos = objetivos;
		}
	    
	    
	}

	

}
