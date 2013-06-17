package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ReportePersonalBSCPrincipal extends Fragment {
	Spinner spinnerColaborador;
	ArrayList<String> lista;
	Button btnSubmit;
	
	public ReportePersonalBSCPrincipal(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportepersonalbsc1principal,
				container, false);
		
		TextView txt = (TextView) rootView.findViewById(R.id.reporteperbsc1titulo);  
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");  
		txt.setTypeface(font);
		
		spinnerColaborador = (Spinner) rootView.findViewById(R.id.reporteperbscspinner);
		lista = new ArrayList<String>();
		//obtenerlistaPeriodos();

		btnSubmit = (Button) rootView.findViewById(R.id.reporteperbscbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
				  
				  ReportePersonalBSCGrafico fragment = new ReportePersonalBSCGrafico();
				  Bundle argumentos = new Bundle();
			      argumentos.putInt("ColaboradorSelec", 1);
			      argumentos.putString("titulo", "Colaborador XXX");
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
