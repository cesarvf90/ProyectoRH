package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import pe.edu.pucp.proyectorh.DetalleFragment;
import pe.edu.pucp.proyectorh.MainActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.LoginActivity.LoginUsuario;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulanteFragment;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPerspectivas;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

public class ObjetivosEmpresa extends Fragment {
	
	private Spinner spinnerPeriodo;
	private Button btnDescCambios;
	private Button btnGuardarCambios;
	
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	
	int periodoSelec;
	String titulo;
	
	public ObjetivosEmpresa(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public class ListarPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			listaPeriodos = new ArrayList<Periodo>();
			// deserializando el json parte por parte
			try {
				JSONArray arregloPeriodos = new JSONArray(result);
				for(int i=0;i<arregloPeriodos.length();i++){
					JSONObject periodoJSON = arregloPeriodos.getJSONObject(i);
					System.out.println("Arreglo Nº"+i+"="+periodoJSON);
					Periodo per = new Periodo(periodoJSON.getString("Nombre"),periodoJSON.getInt("BSCID"));
					listaPeriodos.add(per);
				}
			} catch (Exception e){
				System.out.println("Error="+e.toString());
			}
		}
	}
	
	public int obtenerBSCID(int indice){
		System.out.println("obtiene bscid");
		return listaPeriodos.get(indice).BSCID;
	}
	
	public ArrayList<String> listadoPeriodos(){
		System.out.println("entra a listarPeriodos");
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.ListarPeriodos;
			new ListarPeriodos().execute(request);
			System.out.println("listarPedidos pasa execute");
			ArrayList<String> lista = new ArrayList<String>();
			for(int i=0; i<listaPeriodos.size(); i++){
				System.out.println("Entra con i="+i+" y con nombre="+listaPeriodos.get(i).Nombre);
				lista.add(listaPeriodos.get(i).Nombre);	
			}
			System.out.println("pasa adds");
			return lista;
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Error de conexión");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return null;
		}
	}
	
	public TableRow agregaCabezera(Context contexto){
		TableRow cabecera = new TableRow(contexto);
		cabecera.setLayoutParams(new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		TextView columna1 = new TextView(contexto);
	    columna1.setLayoutParams(new TableRow.LayoutParams(450, LayoutParams.WRAP_CONTENT));
	    columna1.setText("Descripción del Objetivo:");
	    cabecera.addView(columna1);
	    
	    TextView columna2 = new TextView(contexto);
	    columna2.setLayoutParams(new TableRow.LayoutParams(50, LayoutParams.WRAP_CONTENT));
	    columna2.setText("Peso:");
	    cabecera.addView(columna2);
	    
	    TextView columna3 = new TextView(contexto);
	    columna3.setLayoutParams(new TableRow.LayoutParams(150, LayoutParams.WRAP_CONTENT));
	    columna3.setText("Creador:");
	    cabecera.addView(columna3);
	    
	    return cabecera;
	}
	
	public TableRow agregaSeparadorCabezera(Context contexto){
	    TableRow separador_cabecera = new TableRow(contexto);
	    separador_cabecera.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
	    
	    FrameLayout linea_cabecera = new FrameLayout(contexto);
	    TableRow.LayoutParams linea_cabecera_params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, 2);
	    linea_cabecera_params.span = 6;
	    linea_cabecera.setBackgroundColor(Color.parseColor("#CC2266"));
	    separador_cabecera.addView(linea_cabecera, linea_cabecera_params);
	    
	    return separador_cabecera;
	}
	
	public TableRow agregaFila(Context contexto,int flagUltimo){
			TableRow fila = new TableRow(contexto);
		    fila.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		    EditText descripObj = new EditText(contexto);
		    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);
		    descripObj.setLayoutParams(new TableRow.LayoutParams(450, LayoutParams.WRAP_CONTENT));
		    fila.addView(descripObj);
			
		    EditText peso = new EditText(contexto);
		    peso.setInputType(InputType.TYPE_CLASS_NUMBER);
		    peso.setLayoutParams(new TableRow.LayoutParams(50, LayoutParams.WRAP_CONTENT));
		    fila.addView(peso);
		    
		    EditText creador = new EditText(contexto);
		    creador.setInputType(InputType.TYPE_CLASS_TEXT);
		    creador.setLayoutParams(new TableRow.LayoutParams(150, LayoutParams.WRAP_CONTENT));
		    fila.addView(creador);
		    
		    Button eliminarObj = new Button(contexto);
		    eliminarObj.setText("X");
		    fila.addView(eliminarObj);	
		    
		    if(flagUltimo==1){
			    Button aumentarObj = new Button(contexto);
			    aumentarObj.setText("+");
			    fila.addView(aumentarObj);	
		    }
		    
		return fila;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView  = inflater.inflate(R.layout.objetivosbsc,container, false);
			Context contexto = rootView.getContext();
			rootView.findViewById(R.layout.objetivosbsc);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerObjEmpPeriodo);
			List<String> lista = listadoPeriodos();
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,lista);
			System.out.println("pasa adapter");
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerPeriodo.setAdapter(dataAdapter);
			
			spinnerPeriodo.setOnItemSelectedListener(new OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					periodoSelec = obtenerBSCID(pos);
					System.out.println("seleccionado="+periodoSelec);
				}
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
			});
			
			
			/*
			 * CODIGO PARA MANEJO DE PERSPECTIVA (TABS)
			 */				
			TabHost tabs=(TabHost)rootView.findViewById(android.R.id.tabhost);
			tabs.setup();
			 
			TabHost.TabSpec spec=tabs.newTabSpec("Cliente");
			spec.setContent(R.id.objEmpTab1);
			spec.setIndicator("Cliente",
			    res.getDrawable(android.R.drawable.ic_btn_speak_now));
			tabs.addTab(spec);
			 
			spec=tabs.newTabSpec("Aprendizaje y Crecimiento");
			spec.setContent(R.id.objEmpTab2);
			spec.setIndicator("Aprendizaje y Crecimiento",
			    res.getDrawable(android.R.drawable.ic_dialog_map));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Financiero");
			spec.setContent(R.id.objEmpTab3);
			spec.setIndicator("Financiero",
			    res.getDrawable(android.R.drawable.ic_dialog_map));
			tabs.addTab(spec);
			
			spec=tabs.newTabSpec("Procesos Internos");
			spec.setContent(R.id.objEmpTab4);
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
			
			/**AGREGA UN TEXTBOX**/
			 		
			TableLayout layoutTab1 = (TableLayout) rootView.findViewById(R.id.objEmpTab1);
			//CABECERA
			TableRow cabecera = agregaCabezera(contexto);
			layoutTab1.addView(cabecera);
			
			TableRow separador_cabecera = agregaSeparadorCabezera(contexto);
			layoutTab1.addView(separador_cabecera);
			
			TableRow fila = agregaFila(contexto,1);
			layoutTab1.addView(fila);
			
		    /*
			    // Línea que separa los datos de la fila de totales
			    TableRow separador_totales = new TableRow(this);
			    separador_totales.setLayoutParams(new TableLayout.LayoutParams(
			       LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			    FrameLayout linea_totales = new FrameLayout(this);
			    TableRow.LayoutParams linea_totales_params =
			       new TableRow.LayoutParams(LayoutParams.FILL_PARENT, 2);
			    linea_totales_params.span = 6;
			    linea_totales.setBackgroundColor(Color.parseColor("#FFFFFF"));
			    separador_totales.addView(linea_totales, linea_totales_params);
			    tabla.addView(separador_totales);
			 
			    // Fila de totales
			    TableRow totales = new TableRow(this);
			    totales.setLayoutParams(new TableLayout.LayoutParams(
			       LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			 
			    TextView texto_total = new TextView(this);
			    TableRow.LayoutParams texto_total_params =
			       new TableRow.LayoutParams(
			          LayoutParams.WRAP_CONTENT,
			          LayoutParams.WRAP_CONTENT);
			    texto_total_params.span = 2;
			    texto_total.setText("Total");
			    texto_total.setTextColor(Color.parseColor("#0000CC"));
			    texto_total.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			    texto_total.setGravity(Gravity.RIGHT);
			    totales.addView(texto_total, texto_total_params);
			 
			    for (int i = 0; i < 3; i++)
			    {
			       TextView columna = new TextView(this);
			       columna.setLayoutParams(new TableRow.LayoutParams(
			          LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			       columna.setText(String.valueOf(valores_totales[i]));
			       columna.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
			       columna.setGravity(Gravity.CENTER);
			       totales.addView(columna);
			    }
			 
			    tabla.addView(totales);
			 
			    // Añadimos la tabla a la actividad
			    setContentView(tabla);
		     */
		   
		    
			/*
			 * CODIGO PARA BOTON DESCARTAR CAMBIOS
			 */				
	/*
			btnDescCambios = (Button) rootView.findViewById(R.id.ObjEmpDescCambios);
			btnDescCambios.setOnClickListener(new OnClickListener() {
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
			
			/*
			 * CODIGO PARA BOTON DESCARTAR CAMBIOS
			 */		
			/*
			btnGuardarCambios = (Button) rootView.findViewById(R.id.ObjEmpGuardarCambios);			
			btnGuardarCambios.setOnClickListener(new OnClickListener() {
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
