package pe.edu.pucp.proyectorh.reportes;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import android.content.Context;
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
		
		
		spinnerColaborador = (Spinner) rootView.findViewById(R.id.reporteperbscspinner);
		lista = new ArrayList<String>();
		//obtenerlistaColaboradores();

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
		
		
			
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}
	
	public class ColaboradorRDTO
	{
	        private int idColaborador;
	        private String nombreColaborador;
	        private String puesto;
	         
			public int getIdColaborador() {
				return idColaborador;
			}
			public void setIdColaborador(int idColaborador) {
				this.idColaborador = idColaborador;
			}
			public String getNombreColaborador() {
				return nombreColaborador;
			}
			public void setNombreColaborador(String nombreColaborador) {
				this.nombreColaborador = nombreColaborador;
			}
			public String getPuesto() {
				return puesto;
			}
			public void setPuesto(String puesto) {
				this.puesto = puesto;
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
			((TextView) view).setTypeface(Typeface.createFromAsset(
			context.getAssets(), "OpenSans-Light.ttf"));
		}
		} catch (Exception e) {
		}
	}
	
	

}
