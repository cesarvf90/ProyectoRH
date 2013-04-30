package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;
import java.util.List;


import pe.edu.pucp.proyectorh.OpcionDetailFragment;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.administracion.RendirEvaluacionesFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ReporteObjetivosBSCPrincipal extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnSubmit;
	
	int periodoSelec;
	String titulo;
	
	public ReporteObjetivosBSCPrincipal(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.reportebsc1principal,
				container, false);
		
		 
		spinnerPeriodo = (Spinner) rootView.findViewById(R.id.reportebscspinner);
		List<String> lista = new ArrayList<String>();
		
		lista.add("2013-1");
		lista.add("2012-3");
		lista.add("2012-2");
		lista.add("2012-1");
		
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
		

		return rootView;
	}

}
