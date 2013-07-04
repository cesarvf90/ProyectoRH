package pe.edu.pucp.proyectorh.objetivos;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;


public class RegistroAvance extends Fragment {
	View rootView;
	Context contexto;
	FragmentActivity actv;
	
	ExpandableListView listaProcesos;
	
	private ArrayList<ProcesoEvaluacion360> groups;
	private ArrayList<ArrayList<Evaluados360>> childs;
	
		ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	int periodoBSCActual;	
	TableLayout lay;

	int modoPrueba=1;
	
	ArrayList<ObjetivosBSC> listObjetivosBSC;
	
	public RegistroAvance(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public  void listarObjetivos(){
    	ListadoObjetivos lo = new ListadoObjetivos();
    	String rutaLlamada ="";
    	
    	rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	
    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("Recibido: " + result.toString());
				listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);		
				loadData();
			}catch(Exception e){
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public  void registrarAvance(int id,int avance,String descrip){
		RegistroDeAvance lo = new RegistroDeAvance(actv);
    	String rutaLlamada ="";
    	
    	rutaLlamada = Servicio.CrearAvance+"?idObjetivo="+id+"&alcance="+avance+"&descripcion="+descrip; 
    	
    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class RegistroDeAvance extends AsyncCall {
		
		public RegistroDeAvance(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				ocultarMensajeProgreso();
				if (!ConstanteServicio.SERVICIO_OK.equals(respuesta)) {					
					Servicio.mostrarErrorComunicacion("Error al Recibir Respuesta.\nNo se guardaron los Avances.\nIntente Nuevamente",actv);
				}
			} catch (Exception e) {
				ocultarMensajeProgreso();
				System.out.println("SE CAYO ACT="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class ObjetivoLay extends LinearLayout{

		int objetivoID;
		TextView porcentaje;
		public ObjetivoLay(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public ObjetivoLay cargaDataAvance(final ArrayList<String> listaObjetivos){
		final ObjetivoLay layAux = new ObjetivoLay(contexto);
		layAux.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout lay1 = new LinearLayout(contexto);
		lay1.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView nombreObj = new TextView(contexto);
		nombreObj.setText("Objetivo:");
		lay1.addView(nombreObj);
		
		Spinner spinnerObjetivos = new Spinner(contexto);
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaObjetivos);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerObjetivos.setAdapter(dataAdapter);
		spinnerObjetivos.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
		spinnerObjetivos.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				if (listObjetivosBSC!=null && listObjetivosBSC.size()>0){
					layAux.objetivoID=listObjetivosBSC.get(pos).ID;
					System.out.println("obj seleccionado="+layAux.objetivoID);
					ObjetivosBSC auxi = listObjetivosBSC.get(pos);
					System.out.println("val="+auxi.AvanceFinalDeAlgunProgeso);
					layAux.porcentaje.setText(String.valueOf(auxi.AvanceFinalDeAlgunProgeso));
				}
			}
			
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
		    	// TODO Auto-generated method stub
			  }
		});
		
		lay1.addView(spinnerObjetivos);
		
		layAux.porcentaje = new EditText(contexto);
		layAux.porcentaje.setInputType(InputType.TYPE_CLASS_NUMBER);
		layAux.porcentaje.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
		lay1.addView(layAux.porcentaje);
		
		TextView simboloPorc = new TextView(contexto);
		simboloPorc.setText("%");
		lay1.addView(simboloPorc);
		
		Button botonMas = new Button(contexto);
		botonMas.setText("+");
		botonMas.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  lay.addView(cargaDataAvance(listaObjetivos));
			  }
		});
		lay1.addView(botonMas);
		
		Button botonMenos = new Button(contexto);
		botonMenos.setText("-");
		botonMenos.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  lay.removeView(layAux);
			  }
		});
		lay1.addView(botonMenos);
		
		layAux.addView(lay1);
		
		LinearLayout lay2 = new LinearLayout(contexto);
		lay2.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView nombreDesc = new TextView(contexto);
		nombreDesc.setText("Descripcion:");
		lay2.addView(nombreDesc);
		
		EditText descrip = new EditText(contexto);
		descrip.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,80));
		lay2.addView(descrip);
		
		layAux.addView(lay2);
		return layAux;
	}

	public void loadData(){
		lay.removeAllViews();
		ArrayList<String> listaObjetivos = new ArrayList<String>();
		for(int i=0;i<listObjetivosBSC.size();i++){	
			listaObjetivos.add(listObjetivosBSC.get(i).Nombre);
		}

		lay.addView(cargaDataAvance(listaObjetivos));
	}
	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				listaPeriodos = Periodo.getPeriodosByResult(result);
				System.out.println("result="+result);
	
				if (listaPeriodos.size()>0){
					for(int i = 0;i<listaPeriodos.size();i++){
						System.out.println("-->evalua:"+listaPeriodos.get(i).Nombre+" con fe="+listaPeriodos.get(i).FechaFinDisplay);
						if(listaPeriodos.get(i).FechaFinDisplay.equalsIgnoreCase("Activo")){
							periodoBSCActual = listaPeriodos.get(i).BSCID;
							System.out.println("entra con Per="+periodoBSCActual);
						}
					}
				}
				listarObjetivos();
			}catch(Exception e){
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public void guardarAllAvances(){
		for( int i = 0; i < lay.getChildCount(); i++ ){
			  if( lay.getChildAt(i) instanceof ObjetivoLay ){
				  ObjetivoLay objLay = (ObjetivoLay)lay.getChildAt(i);
				  System.out.println("encontro lay de obj="+objLay.objetivoID);
				  LinearLayout lay1= (LinearLayout)objLay.getChildAt(0);
				  LinearLayout lay2= (LinearLayout)objLay.getChildAt(1);
				  
				  int avance = 0;
				  String szAvance = ((EditText)lay1.getChildAt(2)).getText().toString();
				  if(!(szAvance==null||szAvance.isEmpty()||szAvance.compareTo("")==0)){
					  avance=Integer.parseInt(szAvance);
				  }
				  System.out.println("--avance="+avance);
				  String descrip = ((EditText)lay2.getChildAt(1)).getText().toString();
				  registrarAvance(objLay.objetivoID,avance,descrip);
				}
		  }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.registro_avance,container, false);
		actv = getActivity();
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.registro_avance);
		
		lay =  (TableLayout)rootView.findViewById(R.id.layAvance);
				
		ListadoPeriodos lp = new ListadoPeriodos();
		Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

		
		Button btnDescartar = (Button) rootView.findViewById(R.id.AvanceDescCambios);
		btnDescartar.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				 loadData();
			  }
		});
		
		Button btnGuardar = (Button) rootView.findViewById(R.id.AvanceGuardarCambios);
		btnGuardar.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  guardarAllAvances();
			  }
		});
		
		return rootView;
	}

}
