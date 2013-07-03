package pe.edu.pucp.proyectorh.objetivos;


import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


public class Monitoreo extends Fragment {
	View rootView;
	Context contexto;
	FragmentActivity actv;
	
	ExpandableListView listaObjs;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;	
	TableLayout lay;
	private ArrayList<ObjetivosBSC> groups;
	private ArrayList<ArrayList<AvanceDTO>> childs;
	AvanceExpandableAdapter adapter;
	
	public Monitoreo(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				listaPeriodos = Periodo.getPeriodosByResult(result);
				for(int i=0; i<listaPeriodos.size(); i++){
					listaNombrePer.add(listaPeriodos.get(i).Nombre);	
				}
					
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaNombrePer);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerPeriodo.setAdapter(dataAdapter);
					
				spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						periodoBSCActual = listaPeriodos.get(pos).BSCID;
						System.out.println("periodo seleccionado="+periodoBSCActual);
						listarObjetivos();
							 
						//EMF-//actualizaTabs();
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
					}
				});
			}catch(Exception e){
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public  void listarObjetivos(){
    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";
    	
    	rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	
    	System.out.println("RutaListarObj="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("RecibidoListadoObj: " + result.toString());
				ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);	
				groups = new ArrayList<ObjetivosBSC>();
				childs = new ArrayList<ArrayList<AvanceDTO>>();
				loadData(listObjetivosBSC);
			}catch(Exception e){
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	 private void loadData(ArrayList<ObjetivosBSC> listObjetivosPadre){
	    	for(int i=0;i<listObjetivosPadre.size();i++){
	    		System.out.println("agrega obj="+listObjetivosPadre.get(i).Nombre);
	    		groups.add(listObjetivosPadre.get(i));
	    		childs.add(listObjetivosPadre.get(i).LosProgresos);
	    	}
	    	
	    	System.out.println("new adapter");
	    	adapter = new AvanceExpandableAdapter(contexto, groups, childs);
	    	listaObjs.setAdapter(adapter);
	    }
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.visualizacion_avance,container, false);
		contexto = rootView.getContext();
		actv = getActivity();
		rootView.findViewById(R.layout.visualizacion_avance);
		
		Resources res = getResources();
	
		lay =  (TableLayout)rootView.findViewById(R.layout.visualizacion_avance);
		
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerObjEmpPeriodo);
		listaNombrePer = new ArrayList<String>();
		ListadoPeriodos lp = new ListadoPeriodos();
		Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

		listaObjs = (ExpandableListView) rootView.findViewById(R.id.listaObjetivos);
		listaObjs.setLongClickable(true);

		// Se muestra la informacion de la oferta
		listaObjs.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return false;
			}
		});

		// Se muestra la informacion de el postulante
		listaObjs.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
					return false;
			}
		});

		// Se dirige a la evaluacion del postulante
		listaObjs.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,int position, long id) {
				return false;
			}
		});
		
		return rootView;
	}

}