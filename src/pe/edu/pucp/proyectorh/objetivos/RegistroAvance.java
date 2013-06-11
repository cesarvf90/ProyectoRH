package pe.edu.pucp.proyectorh.objetivos;


import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosExpandableAdapter;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoObjetivos;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoObjetivosChild;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoPeriodos;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.TableFila;
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


public class RegistroAvance extends Fragment {
	View rootView;
	Context contexto;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
	private Spinner spinnerPeriodo;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	int periodoBSCActual;	
	TableLayout lay;

	int modoPrueba=1;
	
	public RegistroAvance(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			loadData(listObjetivosBSC);
		}
	}
	
	public LinearLayout cargaDataAvance(final ArrayList<String> listaObjetivos){
		LinearLayout layAux = new LinearLayout(contexto);
		layAux.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout lay1 = new LinearLayout(contexto);
		lay1.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView nombreObj = new TextView(contexto);
		nombreObj.setText("Objetivo:");
		lay1.addView(nombreObj);
		
		Spinner spinnerObjetivos = new Spinner(contexto);
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaObjetivos);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerObjetivos.setAdapter(dataAdapter);
		spinnerObjetivos.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
		lay1.addView(spinnerObjetivos);
		
		EditText porcentaje = new EditText(contexto);
		porcentaje.setText("0%");
		porcentaje.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
		lay1.addView(porcentaje);
		
		Button botonMas = new Button(contexto);
		botonMas.setText("+");
		botonMas.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  lay.addView(cargaDataAvance(listaObjetivos));
			  }
		});
		layAux.addView(botonMas);
		
		layAux.addView(lay1);
		
		LinearLayout lay2 = new LinearLayout(contexto);
		lay2.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView nombreDesc = new TextView(contexto);
		nombreDesc.setText("Descripcion:");
		lay2.addView(nombreDesc);
		
		EditText descrip = new EditText(contexto);
		descrip.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,80));
		lay2.addView(descrip);
		
		layAux.addView(lay2);
		return layAux;
	}

	public void loadData(ArrayList<ObjetivosBSC> listObjetivosBSC){
		ArrayList<String> listaObjetivos = new ArrayList<String>();
		for(int i=0;i<listObjetivosBSC.size();i++){	
			listaObjetivos.add(listObjetivosBSC.get(i).Nombre);
		}

		lay.addView(cargaDataAvance(listaObjetivos));
	}
	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			listaPeriodos = Periodo.getPeriodosByResult(result);
			System.out.println("result="+result);
			for(int i=0; i<listaPeriodos.size(); i++){
				System.out.println("aumenta periodo="+listaPeriodos.get(i).Nombre);
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
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
				}
			});
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.registro_avance,container, false);
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.registro_avance);
		
		lay =  (TableLayout)rootView.findViewById(R.id.layAvance);
				
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerRegistroPeriodo);
		listaNombrePer = new ArrayList<String>();
		ListadoPeriodos lp = new ListadoPeriodos();
		Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

		return rootView;
	}

}
