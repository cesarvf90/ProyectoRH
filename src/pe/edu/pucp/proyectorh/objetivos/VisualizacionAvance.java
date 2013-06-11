package pe.edu.pucp.proyectorh.objetivos;


import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosExpandableAdapter;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoObjetivosChild;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoPeriodos;
import pe.edu.pucp.proyectorh.objetivos.RegistroAvance.ListadoObjetivos;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TabHost.OnTabChangeListener;


public class VisualizacionAvance extends Fragment {
	View rootView;
	Context contexto;
	
	ExpandableListView listaProcesos;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;	
	TableLayout lay;
	private ArrayList<ObjetivosBSC> groups;
	private ArrayList<ArrayList<AvanceDeObjetivo>> childs;
	AvanceExpandableAdapter adapter;
	
	public VisualizacionAvance(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {		
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
		}
	}
	
	public  void listarObjetivos(){
    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";
    	
    	rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	
    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);	
			groups = new ArrayList<ObjetivosBSC>();
			childs = new ArrayList<ArrayList<AvanceDeObjetivo>>();
			loadData(listObjetivosBSC);
		}
	}
	
	 private void loadData(ArrayList<ObjetivosBSC> listObjetivosPadre){
	    	for(int i=0;i<listObjetivosPadre.size();i++){
	    		System.out.println("agrega obj="+listObjetivosPadre.get(i).Nombre);
	    		groups.add(listObjetivosPadre.get(i));
	      	}
	    	
	    	//ListadoObjetivosChild lo = new ListadoObjetivosChild();
	    	String rutaLlamada ="";
	
	    	System.out.println("Ruta-Hijos="+rutaLlamada);
			//Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
		
	    	System.out.println("new adapter");
	    	adapter = new AvanceExpandableAdapter(contexto, groups, childs);
	    	//listaObjs.setAdapter(adapter);
	    }
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.visualizacion_avance,container, false);
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.visualizacion_avance);
		
		Resources res = getResources();
	
		lay =  (TableLayout)rootView.findViewById(R.layout.visualizacion_avance);
		
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerObjEmpPeriodo);
		listaNombrePer = new ArrayList<String>();
		ListadoPeriodos lp = new ListadoPeriodos();
		Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);


		return rootView;
	}

}