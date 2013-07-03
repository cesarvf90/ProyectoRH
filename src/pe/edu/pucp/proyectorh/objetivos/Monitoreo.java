package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDTO;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Objetivo;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.model.Subordinado;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoPeriodos;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.AddObjetivo;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.DeleteObjetivo;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.TableFila;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.UpdateObjetivo;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Monitoreo extends Fragment {
	
	public int indicador=0;
	
	public static int IND_MISOBJS=1;
	public static int IND_SUBORD=2;	
	
	ArrayList<ObjetivosBSC> objsPadre;
	ArrayList<ObjetivosBSC> objsHijos;
	
	private Spinner spinnerSubordinados;
	private Spinner spinnerObjetivos;
	ArrayList<Subordinado> listaSubordinados = new ArrayList<Subordinado>();
	List<String> listaNombreSubordinados;
	
	int ultimoPeriodo;
	
	ArrayList<ObjetivosBSC> listaObjetivosPadre;
	ArrayList<ArrayList<ObjetivosBSC>> listaObjetivosHijo;
	ArrayList<ObjetivosBSC> listadoActual;
	List<String> listaNombreObj;
	int subordinadoActual;
	int objetivoActual;
	ArrayList<ObjetivosBSC> listObjetivosHijos;
	TableLayout lay;
	ExpandableListView listaObjs;
	
	Context contexto;
	ObjetivosExpandableAdapter adapter;	
	
	FragmentActivity actv;
	View rootView;
	
	public Monitoreo(){
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView  = inflater.inflate(R.layout.monitoreo_objetivos,container, false);
			actv = getActivity();
			contexto = rootView.getContext();
			rootView.findViewById(R.layout.monitoreo_objetivos);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerSubordinados = (Spinner) rootView.findViewById(R.id.spinnerMisSubordinados);
			spinnerObjetivos = (Spinner) rootView.findViewById(R.id.spinnerObjsPadre);
			
			
			lay = (TableLayout) rootView.findViewById(R.id.objsHijosMonitoreo);

			listaNombreSubordinados = new ArrayList<String>();
			ListadoSubordinados lp = new ListadoSubordinados();
			String rutaLlamada  = Servicio.ConsultarSubordinados + "?deEsteColaborador=" + LoginActivity.usuario.getID();
			Servicio.llamadaServicio(this.getActivity(), lp,rutaLlamada);

			 Button descartarCambios = (Button) rootView.findViewById(R.id.MisObjsDescCambiosMon);
			 descartarCambios.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {
						  System.out.println("descarta cambios");
						  mostrarHijos();
					  }
				});
			 
			 Button guardarCambios = (Button) rootView.findViewById(R.id.MisObjsGuardarCambiosMon);
			 	guardarCambios.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {
						  System.out.println("guarda cambios");
						  //VALIDAR TODOS LOS OBJETIVOS
						  for(int k=0;k<listadoActual.size();k++){
								listadoActual.get(k).seElimina=true;
						  }
						  if(validaSumas()){
							  guardaLayout();
							  eliminaObjetivos();
						  }
					  }
				});
		return rootView;
	}
	
	
	public class ListadoSubordinados extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try {
				
				listaSubordinados = Subordinado.getSubordinadosByResult(result);
				for(int i=0; i<listaSubordinados.size(); i++){
					listaNombreSubordinados.add(listaSubordinados.get(i).NombreCompleto);	
				}
					
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaNombreSubordinados);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerSubordinados.setAdapter(dataAdapter);
					
				spinnerSubordinados.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						subordinadoActual = listaSubordinados.get(pos).ID;
						System.out.println("subordinado seleccionado="+subordinadoActual);		
						obtenerUltimoPeriodo();
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
					}
				});
			}catch(Exception e){
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public void obtenerUltimoPeriodo(){
		System.out.println("entra a ultper");
		ListadoPeriodos lp = new ListadoPeriodos();
		Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);
	}
	
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("Recibido pER: " + result.toString());
				ArrayList<Periodo> listaPeriodos = Periodo.getPeriodosByResult(result);
				if (listaPeriodos.size()>0){
					ultimoPeriodo = listaPeriodos.get(listaPeriodos.size()-1).BSCID;
				}
				listarObjetivos();				
			}catch(Exception e){
					Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public  void listarObjetivos(){
    	ListadoObjetivos lo = new ListadoObjetivos(actv);
    	//String rutaLlamada = Servicio.ListarObjetivosParaSubordinados+"?idColaborador="+LoginActivity.getUsuario().getID()+"&idPeriodo="+ultimoPeriodo; 
    	String rutaLlamada = Servicio.ListarMisObjetivosSuperiores+"?idUsuario="+subordinadoActual+"&idPeriodo="+ultimoPeriodo;

    	System.out.println("Ruta="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada);
	}
	
	public class ListadoObjetivos extends AsyncCall {
		
		public ListadoObjetivos(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("Recibido: " + result.toString());
				ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);		
				loadData(listObjetivosBSC);
				ocultarMensajeProgreso();
			}catch(Exception e){
				ocultarMensajeProgreso();
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}

	 private void loadData(ArrayList<ObjetivosBSC> listObjetivosPadre){
	    	listaObjetivosPadre = new ArrayList<ObjetivosBSC>();
	    	listaNombreObj = new ArrayList<String>();
	    	if (listObjetivosPadre.size() > 0){
				objetivoActual = listObjetivosPadre.get(0).ID;
			}
	    	for(int i=0;i<listObjetivosPadre.size();i++){
	    		System.out.println("agrega obj="+listObjetivosPadre.get(i).Nombre);
	    		listaObjetivosPadre.add(listObjetivosPadre.get(i));
	    		listaNombreObj.add(listObjetivosPadre.get(i).Nombre + " - ("+listObjetivosPadre.get(i).AvanceFinal+"%)");	
	      	}
	    	
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaNombreObj);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerObjetivos.setAdapter(dataAdapter);
			
	    	ListadoObjetivosChild lo = new ListadoObjetivosChild(actv);
	    	String rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+subordinadoActual+"&idPeriodo="+ultimoPeriodo; 
	    	
	    	System.out.println("Ruta-Hijos="+rutaLlamada);
			Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
	    }
	    
		public class ListadoObjetivosChild extends AsyncCall {
			
			public ListadoObjetivosChild(Activity activity) {
				super(activity);
			}
			@Override
			protected void onPostExecute(String result) {
				try{
					System.out.println("Recibido H: " + result.toString());
					listObjetivosHijos = ObjetivosBSC.getObjetivosByResult(result);		
					loadDataChild(listObjetivosHijos);
					ocultarMensajeProgreso();
				}catch(Exception e){
					ocultarMensajeProgreso();
					Servicio.mostrarErrorComunicacion(e.toString(),actv);
				}
			}
		}
	
		private void loadDataChild(ArrayList<ObjetivosBSC> listObjetivosHijos){
	    	listaObjetivosHijo = new ArrayList<ArrayList<ObjetivosBSC>>();
	    	for(int i=0;i<listaObjetivosPadre.size();i++){
	        	System.out.println("gordis i="+i);
	    		listaObjetivosHijo.add(obtenerHijos(listaObjetivosPadre.get(i).ID,listObjetivosHijos));
	    	}
	    	System.out.println("iki i");
	    	mostrarHijos();
	    	//se actualiza el spinner
	    	spinnerObjetivos.setOnItemSelectedListener(new OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					System.out.println("objetivo seleccionado="+objetivoActual);
					objetivoActual = listaObjetivosPadre.get(pos).ID;
					System.out.println("objetivo seleccionado="+objetivoActual);
					mostrarHijos();
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
				}
			});
	    	
	    }
	
	
	    public void mostrarHijos(){
	    	System.out.println("mostrar hijos de obj="+objetivoActual);
	    	listadoActual=obtenerHijos(objetivoActual, listObjetivosHijos);
	    	
	    	lay.removeAllViews();	    	
			    	
	    	for(int i=0;i<listadoActual.size();i++){
				ObjetivosBSC objBSC = listadoActual.get(i);
				System.out.println("EMF-Ingresa fila i="+i);
				TableFila fila = agregaFila(objBSC);
				lay.addView(fila);
				TableRow separador_cabecera = agregaSeparadorCabezera();
				lay.addView(separador_cabecera);
				for(int j=0; j< objBSC.LosProgresos.size();j++){
					TableFila filita = agregaFilaAvance(objBSC.LosProgresos.get(j));
					lay.addView(filita);
				}
	    	}
	    }

		
		  public TableFila agregaFila(ObjetivosBSC objBSC){
				final TableFila fila = new TableFila(contexto);
				String szNombre ="";
			
				if(objBSC != null){
					szNombre=objBSC.Nombre;
					fila.idObjetivo = objBSC.ID;
				}
				
				fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

			    TextView descripObj = new TextView(contexto);
			    descripObj.setText(szNombre);
			    descripObj.setTextSize(18);
			    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,100));
			    fila.addView(descripObj);
			return fila;
		}
		    

		public TableFila agregaFilaAvance(AvanceDTO avance){
				final TableFila fila = new TableFila(contexto);
				String szNombre;
				String szValor;
				String szValorJefe;
			
				szNombre = avance.Comentario;
				szValor = String.valueOf(avance.Valor);
				szValorJefe = String.valueOf(avance.ValorDelJefe);
				
				fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		
			    TextView descripObj = new TextView(contexto);
			    descripObj.setText(szNombre);
			    descripObj.setTextSize(16);
			    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,60));
			    fila.addView(descripObj);
			    
			    TextView valor = new TextView(contexto);
			    valor.setText(szValor);
			    valor.setTextSize(16);
			    valor.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
			    fila.addView(valor);
			    
			    TextView valorJefe = new TextView(contexto);
			    valorJefe.setText(szValorJefe);
			    valorJefe.setTextSize(16);
			    valorJefe.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
			    fila.addView(valorJefe);
			    
			return fila;
		}
		    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean validaSumas(){
		int pesoFinal=0;
		for( int i = 0; i < lay.getChildCount(); i++ ){
			  if( lay.getChildAt(i) instanceof TableFila ){
				  TableFila fila = (TableFila)lay.getChildAt(i);
				  ObjetivosBSC myObj = new ObjetivosBSC();
				  try{
					 myObj.Peso=Integer.parseInt(((EditText)fila.getChildAt(1)).getText().toString());
				  }catch(Exception e){
					  myObj.Peso=0;
				  }
				  pesoFinal=pesoFinal+myObj.Peso;
			  }
		}
		
		System.out.println("peso final="+pesoFinal);
		if(pesoFinal==100||pesoFinal==0){
			return true;
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Error en Suma de Pesos");
			builder.setMessage("La suma de pesos es "+pesoFinal+" y debería ser 100.\nCorregir por favor.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
	  }
		return false;
	}
	
	
	public void guardaLayout(){
		System.out.println("analiza lay pa guardar");
		for( int i = 0; i < lay.getChildCount(); i++ ){
			  if( lay.getChildAt(i) instanceof TableFila ){
				  TableFila fila = (TableFila)lay.getChildAt(i);
				  System.out.println("encontro lay de obj="+fila.idObjetivo);
				  ObjetivosBSC myObj = new ObjetivosBSC();
				  myObj.ID= fila.idObjetivo;
				  myObj.Nombre=((EditText)fila.getChildAt(0)).getText().toString();
				  try{
					 myObj.Peso=Integer.parseInt(((EditText)fila.getChildAt(1)).getText().toString());
				  }catch(Exception e){
					  myObj.Peso=0;
				  }
				  System.out.println("----->Nombre="+myObj.Nombre);
				  if(!(myObj.Nombre==null || myObj.Nombre=="" || myObj.Nombre.isEmpty())){
					  validaCambios(myObj,i);
				  }
				}
		  }
	}
	
	public ObjetivosBSC cambiaEstadoNoEliminar(int id){
		ObjetivosBSC obj = new ObjetivosBSC();
		for(int i=0;i<listadoActual.size();i++){
			if(listadoActual.get(i).ID==id){
				listadoActual.get(i).seElimina=false;
				break;
			}
		}
		return obj;
	}
	
	public boolean comparaObjs(ObjetivosBSC obj1, ObjetivosBSC obj2){
		boolean rpta=true;
		if(obj1.Nombre==null){
			obj1.Nombre= "";
		}
		if(obj2.Nombre==null){
			obj2.Nombre= "";
		}
		
		if(obj1.Nombre.compareTo(obj2.Nombre)!=0){
			rpta=false;
		}
		
		if(obj1.Peso!=obj2.Peso){
			rpta=false;
		}
		
		return rpta;
	}
	
	
	public void validaCambios(ObjetivosBSC obj, int contFila){
		System.out.println("valida de objID="+obj.ID+ " n="+obj.Nombre+" y p="+obj.Peso+ " -tP="+obj.TipoObjetivoBSCID);
		obj.seElimina=false;
		cambiaEstadoNoEliminar(obj.ID);
		if(obj.ID==-1){
			creaObjetivo(obj,contFila);
		}else{			
			if(comparaObjs(obj,getObjLeido(obj.ID))==false){
				actualizaObjetivo(obj,contFila);	
			}
		}
	}
	
	public ObjetivosBSC getObjLeido(int id){
		ObjetivosBSC obj = new ObjetivosBSC();
		for(int i=0;i<listadoActual.size();i++){
			if(listadoActual.get(i).ID==id){
				obj=listadoActual.get(i);
				break;
			}
		}
		return obj;
	}
	
	public void creaObjetivo(ObjetivosBSC obj, int contFila){
		System.out.println("--->Creara");
		AddObjetivo co = new AddObjetivo(actv);
		co.obj= obj;
		co.contFila = contFila;
		
		String rutaLlamada="";
		if(indicador==IND_MISOBJS){
    		System.out.println("CREAR MIS OBJETIVOS");
    		rutaLlamada = Servicio.CrearObjetivoPropio+"?ObjetivoPadreID="+objetivoActual+"&Nombre="+obj.Nombre+"&Peso="+obj.Peso;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS");
			rutaLlamada = Servicio.CrearObjetivoSub+"?Nombre="+obj.Nombre+"&Peso="+obj.Peso+"&ObjetivoPadreID="+objetivoActual;
    	}
		System.out.println("------->EMF-rutaCrear="+rutaLlamada);
		
		Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
	}
	
	public void actualizaObjetivo(ObjetivosBSC obj,int contFila){
		System.out.println("-->Actualizar");
		UpdateObjetivo co = new UpdateObjetivo(actv);
		co.obj= obj;
		
		String rutaLlamada="";
		if(indicador==IND_MISOBJS){
    		System.out.println("ACT MIS OBJETIVOS");
    		rutaLlamada = Servicio.ActualizaObjetivoPropio+"?ID="+obj.ID+"&ObjetivoPadreID="+objetivoActual+"&Nombre="+obj.Nombre+"&Peso="+obj.Peso;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("ACT MIS SUBORDINADOS");
			rutaLlamada = Servicio.ActualizaObjetivoSub+"?ID="+obj.ID+"&Nombre="+obj.Nombre+"&Peso="+obj.Peso+"&ObjetivoPadreID="+objetivoActual;
    	}
		System.out.println("------->EMF-rutaCrear="+rutaLlamada);
		
		Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
	}
	
	public void eliminaObjetivos(){
		System.out.println("eliminar objs");
		for(int i=0;i<listadoActual.size();i++){
			System.out.println("-Analiza obj="+listadoActual.get(i).Nombre + " con estado="+listadoActual.get(i).seElimina);
			if(listadoActual.get(i).seElimina){
				ObjetivosBSC obj = listadoActual.get(i);
				System.out.println("--->Eliminara Obj="+obj.Nombre);
				DeleteObjetivo co = new DeleteObjetivo(actv);
				co.numObj=i;
				
				String rutaLlamada="";
				if(indicador==IND_MISOBJS){
		    		System.out.println("ELIM MIS OBJETIVOS");
		    		rutaLlamada = Servicio.EliminarObjetivoPropio+"?objetivoID="+obj.ID;
		    	}else if(indicador==IND_SUBORD){
		    		System.out.println("ELIM MIS SUBORDINADOS");
					rutaLlamada = Servicio.EliminarObjetivoSub+"?objetivoID="+obj.ID;
				}
				System.out.println("------->EMF-rutaCrear="+rutaLlamada);

				Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
			}
		}
	}
	
	public class AddObjetivo extends AsyncCall {
		ObjetivosBSC obj;
		int contFila;
		public AddObjetivo(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			System.out.println("RecibidoAddObj: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (ConstanteServicio.SERVICIO_OK.equals(respuesta)) {
					System.out.println("agregara");
					int idRecibido = jsonObject.getInt("ID");
					obj.ID = idRecibido;
					listadoActual.add(obj);
					System.out.println("agregado obj con id="+obj.ID);
					
					((TableFila)lay.getChildAt(contFila)).idObjetivo=idRecibido;
				}
				ocultarMensajeProgreso();
			} catch (Exception e) {
				ocultarMensajeProgreso();
				System.out.println("SE CAYO ADD="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class UpdateObjetivo extends AsyncCall {
		ObjetivosBSC obj;
		public UpdateObjetivo(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			System.out.println("RecibidoUpdateObj: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (ConstanteServicio.SERVICIO_OK.equals(respuesta)) {
					System.out.println("modificara");
					getObjLeido(obj.ID).Nombre = obj.Nombre;
					getObjLeido(obj.ID).Peso = obj.Peso;
					System.out.println("mod->obj="+getObjLeido(obj.ID).Nombre+" con p="+getObjLeido(obj.ID).Peso);
				}
				ocultarMensajeProgreso();
			} catch (Exception e) {
				ocultarMensajeProgreso();
				System.out.println("SE CAYO ACT="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class DeleteObjetivo extends AsyncCall {
		int numObj;
		public DeleteObjetivo(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			System.out.println("RecibidoDeleteObj: " + result.toString());
			try {
				System.out.println("recibio ok");
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (ConstanteServicio.SERVICIO_OK.equals(respuesta)) {
					System.out.println("eliminara");
					listadoActual.remove(numObj);
				}
				ocultarMensajeProgreso();
			} catch (Exception e) {
				ocultarMensajeProgreso();
				System.out.println("SE CAYO DEL="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
   
	
	class TableFila extends TableRow {
		int flagUlt = 0;
		int idObjetivo = -1;
		
		public TableFila(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}		
	}

	
  
    
    private ArrayList<ObjetivosBSC> obtenerHijos(int idPadre, ArrayList<ObjetivosBSC> listObjetivosBSC){
    	ArrayList<ObjetivosBSC> hijos = new ArrayList<ObjetivosBSC>();
    	for(int i=0;i<listObjetivosBSC.size();i++){
    		System.out.println("compara a="+listObjetivosBSC.get(i).ObjetivoPadreID +" con b="+ idPadre);
    		if(listObjetivosBSC.get(i).ObjetivoPadreID==idPadre){
    			hijos.add(listObjetivosBSC.get(i));
    		}
    	}
    	return hijos;
    }
    
  
    
    public boolean isAdmin(){
    	return true;
    }
	

	public TableRow agregaSeparadorCabezera(){
	    TableRow separador_cabecera = new TableRow(contexto);
	    separador_cabecera.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	    
	    FrameLayout linea_cabecera = new FrameLayout(contexto);
	    TableRow.LayoutParams linea_cabecera_params = new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 3);
	    linea_cabecera_params.span = 6;
	    linea_cabecera.setBackgroundColor(Color.parseColor("#CC2266"));
	    separador_cabecera.addView(linea_cabecera, linea_cabecera_params);
	    
	    return separador_cabecera;
	}


	

	
}
