package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;
import java.util.List;


import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulanteFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

public class MisEvaluaciones extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnSubmit;
	
	int periodoSelec;
	String titulo;
	
	public MisEvaluaciones(){
		
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
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				Toast.makeText(parent.getContext(), 
					"seleccionado : " + parent.getItemAtPosition(pos).toString(),
					Toast.LENGTH_SHORT).show();
				
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
				  /*
		 
			    Toast.makeText(v.getContext(),
				"OnClickListener : " + 
		                "\nSpinner 1 : "+ String.valueOf(spinnerPeriodo.getSelectedItem()), 
					Toast.LENGTH_SHORT).show();*/
				  
				  EvaluacionPostulanteFragment fragment = new EvaluacionPostulanteFragment();
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
				  
			  }
		 
			});
		

		return rootView;
	}

}
