package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Objetivo;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeObjetivos;

public class MisSubordinados extends Fragment {

	private View rootView;
	private ArrayList<Objetivo> objetivos;
	private ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avances;
	
	private Spinner spinnerPeriodo;
	private Button btnDescCambios;
	private Button btnGuardarCambios;
	
	ArrayList<Periodo> listarPeriodos = new ArrayList<Periodo>();
	
	TableLayout layoutTab1;
	TableLayout layoutTab2;
	TableLayout layoutTab3;
	TableLayout layoutTab4;
	
	int periodoSelec;
	String titulo;
	
	public MisSubordinados() {
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	public class ListarPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			listarPeriodos = new ArrayList<Periodo>();
			
			try {
//				JSONArray arregloPeriodos = new JSONArray(result);
//				for (int i = 0; i < arregloPeriodos.length(); i++) {
//					JSONObject periodoJSON = arregloPeriodos.getJSONObject(i);
//					System.out.println("Arreglo N° " + i + " = " + periodoJSON);
////					Periodo per = new Periodo(periodoJSON.getString("Nombre"), periodoJSON.getInt("BSCID"));
////					Periodo per = Periodo.getPeriodosByResult(result)
//					listarPeriodos.add(per);
//				}
				
				listarPeriodos = Periodo.getPeriodosByResult(result);
			} catch (Exception e) {
				System.out.println("Error = " + e.toString());
			}
		}
	}
	
	public int obtenerBSCID(int indice) {
		System.out.println("obtiene bscid");
		return listarPeriodos.get(indice).BSCID;
	}
	
	public ArrayList<String> listadoPeriodos() {
		System.out.println("entra a listarPeriodos");
		if (ConnectionManager.connect(this.getActivity())) {
			String request = Servicio.ListarPeriodos;
			new ListarPeriodos().execute(request);
			System.out.println("listarPedidos pasa execute");
			ArrayList<String> lista = new ArrayList<String>();
			for (int i = 0; i < listarPeriodos.size(); i++) {
				System.out.println("Entra con i = " + i + " y con nombre = " + listarPeriodos.get(i).Nombre);
				lista.add(listarPeriodos.get(i).Nombre);
			}
			System.out.println("pasa adds");
			return lista;			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Error de conexión");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return null;
		}
	}
	
	public TableRow agregaCabecera(Context contexto) {
		TableRow cabecera = new TableRow(contexto);
		cabecera.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		
		TextView columna1 = new TextView(contexto);
		columna1.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 70));
		columna1.setText("Descripción del Objetivo:");
		cabecera.addView(columna1);
		
		TextView columna2 = new TextView(contexto);
		columna1.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 10));
		columna1.setText("Peso:");
		cabecera.addView(columna2);		
		
		TextView columna3 = new TextView(contexto);
		columna3.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 20));
		columna3.setText("Creador:");
		cabecera.addView(columna3);		
		
		return cabecera;
	}
	
	public TableRow agregaSeparadorCabezera(Context contexto){
	    TableRow separador_cabecera = new TableRow(contexto);
	    separador_cabecera.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	    
	    FrameLayout linea_cabecera = new FrameLayout(contexto);
	    TableRow.LayoutParams linea_cabecera_params = new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 3);
	    linea_cabecera_params.span = 6;
	    linea_cabecera.setBackgroundColor(Color.parseColor("#CC2266"));
	    separador_cabecera.addView(linea_cabecera, linea_cabecera_params);
	    
	    return separador_cabecera;
	}
	
	public TableRow agregaFila(final Context contexto, final int numLayout,ObjetivosBSC objBSC, int flagUltimo){
			final TableRow fila = new TableRow(contexto);
		    fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	
		    EditText descripObj = new EditText(contexto);
		    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);
		    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,70));
		    fila.addView(descripObj);
			
		    EditText peso = new EditText(contexto);
		    peso.setInputType(InputType.TYPE_CLASS_NUMBER);
		    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,10));
		    fila.addView(peso);
		    
		    TextView creador = new TextView(contexto);
		    creador.setText("Ever Mitta");
		    creador.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
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
				  }
			});
		    fila.addView(eliminarObj);	
		    
		    if(flagUltimo==1){
			    final Button aumentarObj = new Button(contexto);
			    aumentarObj.setText("+");
			    aumentarObj.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {	
						  fila.removeView(aumentarObj); //elimina el boton
						  TableRow filaUlt=agregaFila(contexto,numLayout,null, 1);
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
		return fila;
	}	
	
	public  ArrayList<ObjetivosBSC> addObjetivos(int tipoBSC){
		ArrayList<ObjetivosBSC> listObjs = new ArrayList<ObjetivosBSC>();
		
		return listObjs;
	}
	
	public TableLayout AgregaDatosTab(Context contexto, TableLayout lay, int tipoBSC){
		//CABECERA
		TableRow cabecera = agregaCabecera(contexto);
		lay.addView(cabecera);
		
		//SEPARADOR DE CABECERA
		TableRow separador_cabecera = agregaSeparadorCabezera(contexto);
		lay.addView(separador_cabecera);
		
		ArrayList<ObjetivosBSC> listObjetivosBSC = addObjetivos(tipoBSC); 
		
		//FILAS
		int cantidadObjetivosBSC = listObjetivosBSC.size();
		cantidadObjetivosBSC = 2; //para pruebas
		for(int i=0;i<cantidadObjetivosBSC;i++){
			int flagUltimo = 0;
			ObjetivosBSC objBSC = new ObjetivosBSC(); //= listObjetivosBSC.get(i);
			
			if ((i+1) == cantidadObjetivosBSC){
				flagUltimo=1;
			}
			
			TableRow fila = agregaFila(contexto,tipoBSC,objBSC,flagUltimo);
			lay.addView(fila);
		}
		//SEPARADOR DE TOTAL
		//TableRow separador_total = agregaSeparadorCabezera(contexto);
		//lay.addView(separador_total);
		
		return lay;		
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView  = inflater.inflate(R.layout.mis_subordinados,container, false);
			Context contexto = rootView.getContext();
			rootView.findViewById(R.layout.mis_subordinados);
			
			Resources res = getResources();
			
			final ArrayList<Objetivo> objetivosSubordinado = Objetivo.tomarPrestadoDataDePrueba();
			final ArrayList<AvanceDeObjetivo> avancesDeObjetivos = AvanceDeObjetivo.tomarPrestadoDataDePrueba(); 
			final ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avancesHastaHoy = new ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>>();
			
//			avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El primer objetivo no presenta progreso
//			avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El segundo tampoco
			
			ArrayList<AvanceDeObjetivo> grupoDeUno = new ArrayList<AvanceDeObjetivo>();
			
			grupoDeUno.add(avancesDeObjetivos.get(2));
			
			ArrayList<AvanceDeObjetivo> otroGrupoDeUno = new ArrayList<AvanceDeObjetivo>();
			
			otroGrupoDeUno.add(avancesDeObjetivos.get(1));	
			
			ArrayList<ArrayList<AvanceDeObjetivo>> parDeAvancesTercerObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
			
			parDeAvancesTercerObjetivo.add(grupoDeUno);
			parDeAvancesTercerObjetivo.add(otroGrupoDeUno);
			
			avancesHastaHoy.add(parDeAvancesTercerObjetivo);
			
			//El primer y segundo objetivo están sin comenzar
			
			ArrayList<AvanceDeObjetivo> elAvanceDelCeroPorCiento = new ArrayList<AvanceDeObjetivo>();
			
			elAvanceDelCeroPorCiento.add(avancesDeObjetivos.get(0));
			
			ArrayList<ArrayList<AvanceDeObjetivo>> elUnicoAvanceDelObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
			
			elUnicoAvanceDelObjetivo.add(elAvanceDelCeroPorCiento);
			
			avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
			avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
			
			//Coloca la data en los cajones de la vista
			
			ExpandableListView listaDeObjetivos = (ExpandableListView) rootView.findViewById(R.id.AquiSupervisoSusObjetivos);
			AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), objetivosSubordinado, avancesHastaHoy);
			listaDeObjetivos.setAdapter(adaptador);
			listaDeObjetivos.setLongClickable(true);
			
			//Esta parte responde: ¿De donde saca los subordinados?

			ArrayList<String> listadoSubordinadosNombreYApellidos = Colaborador.tomarPrestadoDataDePrueba();
			
			Spinner elMenuDeSubordinados = (Spinner) rootView.findViewById(R.id.UnoDeEstosSubordinados);
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listadoSubordinadosNombreYApellidos);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			elMenuDeSubordinados.setAdapter(dataAdapter);
			
//			Colaborador.consultarColaboradoresDelServidorDeProduccion();
			
			return rootView;
		}			
}
