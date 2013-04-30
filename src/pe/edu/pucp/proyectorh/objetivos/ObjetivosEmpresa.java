package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;


import pe.edu.pucp.proyectorh.OpcionDetailFragment;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.administracion.RendirEvaluacionesFragment;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPerspectivas;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class ObjetivosEmpresa extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnSubmit;
	
	int periodoSelec;
	String titulo;
	
	public ObjetivosEmpresa(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView  = inflater.inflate(R.layout.objetivosbsc,container, false);
			rootView.findViewById(R.layout.objetivosbsc);
			
			Resources res = getResources();
			
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinner1);
			List<String> lista = new ArrayList<String>();
			
			lista.add("01/01/2013 al 31/12/2013");
			lista.add("01/01/2012 al 31/12/2012");
			lista.add("01/01/2011 al 31/12/2011");
			lista.add("01/01/2010 al 31/12/2010");
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPeriodo.setAdapter(dataAdapter);
			
			spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					/*
					Toast.makeText(parent.getContext(), 
						"seleccionado : " + parent.getItemAtPosition(pos).toString(),
						Toast.LENGTH_SHORT).show();
					*/
					periodoSelec = 1; //aqui idobjetivo selec
					titulo = parent.getItemAtPosition(pos).toString();

				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			});
				
			TabHost tabs=(TabHost)rootView.findViewById(android.R.id.tabhost);
			tabs.setup();
			 
			TabHost.TabSpec spec=tabs.newTabSpec("Cliente");
			spec.setContent(R.id.tab1);
			spec.setIndicator("Cliente",
			    res.getDrawable(android.R.drawable.ic_btn_speak_now));
			tabs.addTab(spec);
			 
			spec=tabs.newTabSpec("Aprendizaje y Crecimiento");
			spec.setContent(R.id.tab2);
			spec.setIndicator("Aprendizaje y Crecimiento",
			    res.getDrawable(android.R.drawable.ic_dialog_map));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Financiero");
			spec.setContent(R.id.tab3);
			spec.setIndicator("Financiero",
			    res.getDrawable(android.R.drawable.ic_dialog_map));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Procesos Internos");
			spec.setContent(R.id.tab2);
			spec.setIndicator("Procesos Internos",
			    res.getDrawable(android.R.drawable.ic_dialog_map));
			tabs.addTab(spec);
			 
			tabs.setCurrentTab(0);
			
			tabs.setOnTabChangedListener(new OnTabChangeListener(){
			    @Override
			    public void onTabChanged(String tabId) {
			    	
			        Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
			    }
			});
	/*
			btnSubmit = (Button) rootView.findViewById(R.id.reportebscbtnConsultar);
			btnSubmit.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {
					  
			 
				    Toast.makeText(v.getContext(),
					"Seleccionado "+ String.valueOf(spinnerPeriodo.getSelectedItem()), 
						Toast.LENGTH_SHORT).show();
					  
				      ReporteObjetivosBSCPerspectivas fragment = new ReporteObjetivosBSCPerspectivas();
				      
				      Bundle argumentos = new Bundle();
				      argumentos.putString("PeriodoSelec", titulo);
				      fragment.setArguments(argumentos);
				      
					  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
					  ft.replace(R.id.opcion_detail_container, fragment);
					  ft.addToBackStack(null);
					  ft.commit();
					  
				  }
			 
				});
			*/
		return rootView;
	}

}
