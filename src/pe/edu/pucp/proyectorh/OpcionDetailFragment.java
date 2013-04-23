package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.model.Modulo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

/**
 * Fragmento de contenido de las funcionalidades
 * 
 * @author Cesar
 * 
 */
public class OpcionDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";

	Modulo.ModuloItem tituloFuncionalidad;

	public OpcionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			System.out.println("-------Seleccionado Id:"
					+ String.valueOf((Integer.parseInt((getArguments()
							.getString(ARG_ITEM_ID))) - 1)));
			tituloFuncionalidad = Modulo.MODULOS_MOSTRADOS_ACTUAL.get(Integer
					.parseInt(getArguments().getString(ARG_ITEM_ID)) - 1);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View rootView;
		if (tituloFuncionalidad != null) {
			if(tituloFuncionalidad.nombre.equals("Objetivos Empresa")){
				rootView = inflater.inflate(R.layout.objetivosbsc,
						container, false);
				rootView.findViewById(R.layout.objetivosbsc);
				String array_spinner[];
				array_spinner=new String[5];
				array_spinner[0]="01/01/2013 al 31/12/2013";
				array_spinner[1]="01/01/2012 al 31/12/2012";
				array_spinner[2]="01/01/2011 al 31/12/2011";
				array_spinner[3]="01/01/2010 al 31/12/2010";
				array_spinner[4]="01/01/2009 al 31/12/2009";
				Spinner s = (Spinner) rootView.findViewById(R.id.spinner1);
				@SuppressWarnings("unchecked")
				ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, array_spinner);
				s.setAdapter(adapter);
				
				Resources res = getResources();
				 
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
			}else{
				rootView = inflater.inflate(R.layout.fragment_opcion_detail,
						container, false);
				((TextView) rootView.findViewById(R.id.opcion_detail))
				.setText(tituloFuncionalidad.nombre);
			}
		}else{
			rootView = inflater.inflate(R.layout.fragment_opcion_detail,
					container, false);
		}
		return rootView;
	}
}
