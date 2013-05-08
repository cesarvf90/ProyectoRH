package pe.edu.pucp.proyectorh.reportes;



import pe.edu.pucp.proyectorh.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ReporteObjetivosBSCObjetivos extends Fragment {
	
	GridView gridView;
	
	String[] objetivos = new String[] { 
			"Objetivo ", "Objetivo ", "Objetivo ", "Objetivo ", "Objetivo ",
			"Objetivo ", "Objetivo ", "Objetivo ", "Objetivo ", "Objetivo "};
	
	int nivel;
	String objpadre;
	
	public ReporteObjetivosBSCObjetivos(){
		
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.reportebsc3objetivos,
				container, false);
		
		gridView = (GridView) rootView.findViewById(R.id.reportebscgridObjetivos);
		gridView.setAdapter(new ObjetivoAdapter(rootView.getContext(),objetivos));
		
		
		objpadre = getArguments().getString("objetivopadre");
		nivel = getArguments().getInt("nivel");
		
		incrementar_nivel(nivel, objpadre);
		
		String titulo = getArguments().getString("objetivopadre");
		TextView textView = (TextView)rootView.findViewById(R.id.reportebscObjetivopadre);
		textView.setText(titulo);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				
				if (nivel==2){
					
					Toast.makeText(v.getContext(),
							"Objetivo de último nivel", Toast.LENGTH_SHORT).show();
				}
				else{
					
					
					Bundle b = new Bundle();
					b.putInt("nivel",nivel + 1);
					String cadena = "" + ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
					b.putString("objetivopadre", cadena);
					
					ReporteObjetivosBSCObjetivos fragment = new ReporteObjetivosBSCObjetivos();
					fragment.setArguments(b);
					
					FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.opcion_detail_container, fragment);
					ft.addToBackStack(null);
					ft.commit();
					
				}
			   
			}
		});
		
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
					
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View v,
						int position, long id) {
		
						
						Bundle b = new Bundle();
						String cadena = "" +  ((TextView) v.findViewById(R.id.reportebscObjetivolabel)).getText();
						b.putString("titulo", cadena);
						
						ReporteObjetivosBSCGrafico fragment = new ReporteObjetivosBSCGrafico();
						fragment.setArguments(b);
						
						FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.opcion_detail_container, fragment);
						ft.addToBackStack(null);
						ft.commit();
							
						
						return true;
					   
					}
		
				});
 
		
		
		return rootView;
	}
	
	public void incrementar_nivel(int n, String padre){
			
			String cadena="";
				
			for (int j=0;j<10;j++){
				
				if (n == 0){
					cadena = (j+1) + "";
					objetivos[j] = objetivos[j] + cadena;
					
				}
				else{
					cadena = padre + "." + (j+1);
					objetivos[j] = cadena;
				}
	
				
			}

	}
	
}
