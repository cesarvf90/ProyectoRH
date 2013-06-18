package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class MisObjetivos extends Fragment {
	
	public int indicador=0;
	
	public static int IND_MISOBJS=1;
	public static int IND_SUBORD=2;	
	
	ArrayList<ObjetivosBSC> objsPadre;
	ArrayList<ObjetivosBSC> objsHijos;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;
	
	ExpandableListView listaObjs;
	
	Context contexto;
	ObjetivosExpandableAdapter adapter;
	
	private ArrayList<ObjetivosBSC> groups;
	private ArrayList<ArrayList<ObjetivosBSC>> childs;
	
	
	public MisObjetivos(){
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView  = inflater.inflate(R.layout.listar_objetivos,container, false);
			contexto = rootView.getContext();
			rootView.findViewById(R.layout.listar_objetivos);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerMisObjsPeriodo);
			listaNombrePer = new ArrayList<String>();
			ListadoPeriodos lp = new ListadoPeriodos();
			Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

			
			listaObjs = (ExpandableListView) rootView.findViewById(R.id.listaObjetivos);
			System.out.println("setea a cero");
	    	groups= new ArrayList<ObjetivosBSC>();
	    	childs= new ArrayList<ArrayList<ObjetivosBSC>>();
			//listaObjs.setLongClickable(true);
	    	listaObjs.setFocusable(false);
	    	listaObjs.setFocusableInTouchMode(false);
	        
			// Se muestra la informacion de la oferta
			listaObjs.setOnGroupClickListener(new OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					System.out.println("------------>Clickeo papa gp="+groupPosition);

					return false;
				}
			});
			
			
			listaObjs.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					System.out.println("------------>Clickeo item");
					
					// TODO Auto-generated method stub
					
				}
			});

			// Se muestra la informacion de el postulante
			listaObjs.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
						System.out.println("------------>Clickeo hijo gp="+groupPosition+ " y chp="+childPosition);
						return false;
				}
			});

		return rootView;
	}
	
	
    private void loadData(ArrayList<ObjetivosBSC> listObjetivosPadre){
    	for(int i=0;i<listObjetivosPadre.size();i++){
    		System.out.println("agrega obj="+listObjetivosPadre.get(i).Nombre);
    		groups.add(listObjetivosPadre.get(i));
      	}
    	
    	ListadoObjetivosChild lo = new ListadoObjetivosChild();
    	String rutaLlamada ="";

    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS 2");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
	    }else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS 2");
    		rutaLlamada = Servicio.ListarObjetivosParaSubordinados+"?idColaborador="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
    	System.out.println("Ruta-Hijos="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
	
    	System.out.println("new adapter");
    	adapter = new ObjetivosExpandableAdapter(contexto, groups, childs);
    	listaObjs.setAdapter(adapter);
    }
    
    private ArrayList<ObjetivosBSC> obtenerHijos(int idPadre, ArrayList<ObjetivosBSC> listObjetivosBSC){
    	ArrayList<ObjetivosBSC> hijos = new ArrayList<ObjetivosBSC>();
    	for(int i=0;i<listObjetivosBSC.size();i++){
    		if(listObjetivosBSC.get(i).ObjetivoPadreID==idPadre){
    			hijos.add(listObjetivosBSC.get(i));
    		}
    	}
    	return hijos;
    }
    
    private void loadDataChild(ArrayList<ObjetivosBSC> listObjetivosHijos){
    	for(int i=0;i<groups.size();i++){
    		childs.add(obtenerHijos(groups.get(i).ID,listObjetivosHijos));
    	}
    	adapter.actualizaHijos(childs);
    }
    
    public boolean isAdmin(){
    	return true;
    }
	
	public  void listarObjetivos(){
    	groups= new ArrayList<ObjetivosBSC>();
    	childs= new ArrayList<ArrayList<ObjetivosBSC>>();

    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";
    	
    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS");
    		rutaLlamada = Servicio.ListarMisObjetivosSuperiores+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);		
			loadData(listObjetivosBSC);
		}
	}

	public class ListadoObjetivosChild extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ObjetivosBSC> listObjetivosHijos = ObjetivosBSC.getObjetivosByResult(result);		
			loadDataChild(listObjetivosHijos);
		}
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
	

	
}
