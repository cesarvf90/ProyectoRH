package pe.edu.pucp.proyectorh.reportes;


import pe.edu.pucp.proyectorh.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ReporteObjetivosBSCPerspectivas extends Fragment {
	
	GridView gridView;
	 
	static final String[] perspectivas = new String[] { 
			"Financiera", "Clientes", "Interno", "Formacion"};
	
	public ReporteObjetivosBSCPerspectivas(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc2perspectivas,
				container, false);
		
		String titulo = getArguments().getString("PeriodoSelec");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscPeriodoselec);
		textView.setText("Periodo " + titulo);
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridPerspectivas);
		gridView.setAdapter(new PerspectivaAdapter(rootView.getContext(), perspectivas));
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			
			public void onItemClick (AdapterView<?> parent, View v,
					int position, long id) {
				/*
			   Toast.makeText(v.getContext(),
				((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText(), Toast.LENGTH_SHORT).show();
				*/
				
				  ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
			      
				  Bundle b = new Bundle();
				  b.putInt("nivel",0);
				  b.putString("objetivopadre", "Perspectiva " + ((TextView) v.findViewById(R.id.reportebscPerspectivalabel)).getText());
				  fragment.setArguments(b);
			      
				  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
				  ft.replace(R.id.opcion_detail_container, fragment);
				  ft.addToBackStack(null);
				  ft.commit();
			}
		});
		
		
		
		
		return rootView;
	}

}
