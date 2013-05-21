package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;


import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;


public class ObjetivosEmpresa extends Fragment {
	View rootView;
	Context contexto;
	
	private Spinner spinnerPeriodo;
	private Button btnDescCambios;
	private Button btnGuardarCambios;
	
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	
	TableLayout layoutTab1;
	TableLayout layoutTab2;
	TableLayout layoutTab3;
	TableLayout layoutTab4;
	
	int periodoBSCActual;
	String titulo;
	
	int perspectivaActual;
	
	public ObjetivosEmpresa(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void actualizaTabs(){
		layoutTab1.removeAllViews();
		layoutTab2.removeAllViews();
		layoutTab3.removeAllViews();
		layoutTab4.removeAllViews();
		
		AgregaDatosTab(1);
		AgregaDatosTab(2);
		AgregaDatosTab(3);
		AgregaDatosTab(4);
	}
	
	
	public class ListadoObjetivos extends AsyncCall {
		int auxPerspectiva = perspectivaActual;
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONArray array = new JSONArray(result);
				ArrayList<ObjetivosBSC> listObjetivosBSC = new ArrayList<ObjetivosBSC>();
				for(int i = 0; i < array.length(); i++) {
					final Gson gson = new Gson();
					final ObjetivosBSC oBSC = gson.fromJson(array.getString(i),ObjetivosBSC.class);
					listObjetivosBSC.add(oBSC);
				}
				
				//FILAS
				for(int i=0;i<listObjetivosBSC.size();i++){
					int flagUltimo = 0;
					ObjetivosBSC objBSC = listObjetivosBSC.get(i);
					if ((i+1) == listObjetivosBSC.size()){
						flagUltimo=1;
					}
					System.out.println("EMF-Ingresa fila i="+i+" para perspectiva="+auxPerspectiva);
					TableFila fila = agregaFila(auxPerspectiva,objBSC,flagUltimo);
					if (auxPerspectiva==1){
						layoutTab1.addView(fila);
					}else if(auxPerspectiva==2){
						layoutTab2.addView(fila);
					}else if(auxPerspectiva==3){
						layoutTab3.addView(fila);
					}else if(auxPerspectiva==4){
						layoutTab4.addView(fila);
					}
				}
			} catch (Exception e){
				System.out.println("Error="+e.toString());
			}
		}
	}
	
	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			listaPeriodos = new ArrayList<Periodo>();
			try {
				JSONArray arregloPeriodos = new JSONArray(result);
				for(int i=0;i<arregloPeriodos.length();i++){
					JSONObject periodoJSON = arregloPeriodos.getJSONObject(i);
					Periodo per = new Periodo(periodoJSON.getString("Nombre"),periodoJSON.getInt("BSCID"));
					listaPeriodos.add(per);
				}
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
						actualizaTabs();
					}
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
				});
			} catch (Exception e){
				System.out.println("Error="+e.toString());
			}
		}
	}
	
	class TableFila extends TableRow {
		int flagUlt = 0;
		
		public TableFila(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}		
	}

	public TableRow agregaCabezera(){
		TableRow cabecera = new TableRow(contexto);
		cabecera.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));

		TextView columna1 = new TextView(contexto);
	    columna1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,70));
	    columna1.setText("Descripción del Objetivo:");
	    cabecera.addView(columna1);
	    
	    
	    TextView columna2 = new TextView(contexto);
	    columna2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,10));
	    columna2.setText("Peso:");
	    cabecera.addView(columna2);
	    
	    TextView columna3 = new TextView(contexto);
	    columna3.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,20));
	    columna3.setText("Creador:");
	    cabecera.addView(columna3);
	    
	    return cabecera;
	}
	
	public TableRow agregaSeparadorCabezera(){
	    TableRow separador_cabecera = new TableRow(contexto);
	    separador_cabecera.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    
	    FrameLayout linea_cabecera = new FrameLayout(contexto);
	    TableRow.LayoutParams linea_cabecera_params = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 3);
	    linea_cabecera_params.span = 6;
	    linea_cabecera.setBackgroundColor(Color.parseColor("#CC2266"));
	    separador_cabecera.addView(linea_cabecera, linea_cabecera_params);
	    
	    return separador_cabecera;
	}
	
	public TableFila agregaFila(final int numLayout,ObjetivosBSC objBSC, final int flagUltimo){
			final TableFila fila = new TableFila(contexto);
			fila.flagUlt=flagUltimo;
			String szNombre ="";
			String szPeso ="";
			String szCreador=LoginActivity.getUsuario().getUsername();
			
			if(objBSC != null){
				szNombre=objBSC.Nombre;
				szPeso = Integer.toString(objBSC.Peso);
				szCreador = LoginActivity.getUsuario().getUsername(); //objBSC.CreadorID;
			}
			
			fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		    EditText descripObj = new EditText(contexto);
		    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);
		  
		    descripObj.setText(szNombre);
		    descripObj.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,70));
		    fila.addView(descripObj);
			
		    EditText peso = new EditText(contexto);
		    peso.setInputType(InputType.TYPE_CLASS_NUMBER);
		    peso.setText(szPeso);
		    peso.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,10));
		    fila.addView(peso);
		    
		    TextView creador = new TextView(contexto);
		    creador.setText(szCreador);
		    creador.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,20));
		    fila.addView(creador);
		    		    
		    Button eliminarObj = new Button(contexto);
		    eliminarObj.setText("X");
		    eliminarObj.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {
					  if (numLayout==1){
						  layoutTab1.removeView(fila);
					  }else if(numLayout==2){
						  layoutTab2.removeView(fila);
					  }else if(numLayout==3){
						  layoutTab3.removeView(fila);
					  }else if(numLayout==4){
						  layoutTab4.removeView(fila);
					  }
					  
					  if(fila.flagUlt==1){
						  TableFila filaUlt=agregaFila(numLayout,null, 1);
						  if (numLayout==1){
							  layoutTab1.addView(filaUlt);
						  }else if(numLayout==2){
							  layoutTab2.addView(filaUlt);
						  }else if(numLayout==3){
							  layoutTab3.addView(filaUlt);
						  }else if(numLayout==4){
							  layoutTab4.addView(filaUlt);
						  }			
					  }
					  
					  
				  }
			});
		    fila.addView(eliminarObj);	
		    
		    if(fila.flagUlt==1){
			    final Button aumentarObj = new Button(contexto);
			    aumentarObj.setText("+");
			    aumentarObj.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {	
						  fila.removeView(aumentarObj); //elimina el boton
						  fila.flagUlt=0;
						  TableFila filaUlt=agregaFila(numLayout,null, 1);
						  if (numLayout==1){
							  layoutTab1.addView(filaUlt);
						  }else if(numLayout==2){
							  layoutTab2.addView(filaUlt);
						  }else if(numLayout==3){
							  layoutTab3.addView(filaUlt);
						  }else if(numLayout==4){
							  layoutTab4.addView(filaUlt);
						  }						 
					  }
				});
			    fila.addView(aumentarObj);	
		    }
		    System.out.println("retorna fila");
		return fila;
	}
	
	public  void listarObjetivos(){
		ListadoObjetivos lo = new ListadoObjetivos();
		String rutaLlamada = Servicio.ListarObjetivosBSC+"?tipoObjetivoBSCID="+perspectivaActual+"&BSCID="+periodoBSCActual;
		System.out.println("EMF-ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public void AgregaDatosTab(int tipoBSC){
		perspectivaActual = tipoBSC;
		
		//CABECERA
		TableRow cabecera = agregaCabezera();
			
		//SEPARADOR DE CABECERA
		TableRow separador_cabecera = agregaSeparadorCabezera();
		
		  if (perspectivaActual==1){
			  layoutTab1.addView(cabecera);
			  layoutTab1.addView(separador_cabecera);
		  }else if(perspectivaActual==2){
			  layoutTab2.addView(cabecera);
			  layoutTab2.addView(separador_cabecera);
		  }else if(perspectivaActual==3){
			  layoutTab3.addView(cabecera);
			  layoutTab3.addView(separador_cabecera);
		  }else if(perspectivaActual==4){
			  layoutTab4.addView(cabecera);
			  layoutTab4.addView(separador_cabecera);
		  }
		
		listarObjetivos(); 
		
		System.out.println("gg fin ");
		//SEPARADOR DE TOTAL
		//TableRow separador_total = agregaSeparadorCabezera();
		//lay.addView(separador_total);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView  = inflater.inflate(R.layout.objetivosbsc,container, false);
			contexto = rootView.getContext();
			rootView.findViewById(R.layout.objetivosbsc);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerObjEmpPeriodo);
			listaNombrePer = new ArrayList<String>();
			ListadoPeriodos lp = new ListadoPeriodos();
			Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

			/*
			 * CODIGO PARA MANEJO DE PERSPECTIVA (TABS)
			 */				
			TabHost tabs=(TabHost)rootView.findViewById(android.R.id.tabhost);
			tabs.setup();
			 
			TabHost.TabSpec spec=tabs.newTabSpec("Financiero");
			spec.setContent(R.id.objEmpTab1);
			spec.setIndicator("Financiero",
			    res.getDrawable(android.R.drawable.ic_menu_myplaces));
			tabs.addTab(spec);
			 
			spec=tabs.newTabSpec("Aprendizaje y Crecimiento");
			spec.setContent(R.id.objEmpTab2);
			spec.setIndicator("Aprendizaje y Crecimiento",
			    res.getDrawable(android.R.drawable.ic_menu_myplaces));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Cliente");
			spec.setContent(R.id.objEmpTab3);
			spec.setIndicator("Cliente",
			    res.getDrawable(android.R.drawable.ic_menu_myplaces));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Procesos Internos");
			spec.setContent(R.id.objEmpTab4);
			spec.setIndicator("Procesos Internos",
			    res.getDrawable(android.R.drawable.ic_menu_myplaces));
			tabs.addTab(spec);
			 
			tabs.setCurrentTab(0);
			
			tabs.setOnTabChangedListener(new OnTabChangeListener(){
			    @Override
			    public void onTabChanged(String tabId) {
			    	
			        Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
			    }
			});
			 		
			layoutTab1 = (TableLayout) rootView.findViewById(R.id.objEmpTab1);
			layoutTab2 = (TableLayout) rootView.findViewById(R.id.objEmpTab2);
			layoutTab3 = (TableLayout) rootView.findViewById(R.id.objEmpTab3);
			layoutTab4 = (TableLayout) rootView.findViewById(R.id.objEmpTab4);		
			
			
			 Button descartarCambios = (Button) rootView.findViewById(R.id.ObjEmpDescCambios);
			 descartarCambios.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {
						  System.out.println("descarta cambios");
						  actualizaTabs();
					  }
				});
		return rootView;
	}

}
