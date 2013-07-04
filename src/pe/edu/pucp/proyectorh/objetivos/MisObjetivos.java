package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
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

public class MisObjetivos extends Fragment {
	
	public int indicador=0;
	
	public static int IND_MISOBJS=1;
	public static int IND_SUBORD=2;	
	
	ArrayList<ObjetivosBSC> objsPadre;
	ArrayList<ObjetivosBSC> objsHijos;
	
	private Spinner spinnerPeriodo;
	private Spinner spinnerObjetivos;
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	List<String> listaNombrePer;
	ArrayList<ObjetivosBSC> listaObjetivosPadre;
	ArrayList<ArrayList<ObjetivosBSC>> listaObjetivosHijo;
	ArrayList<ObjetivosBSC> listadoActual;
	List<String> listaNombreObj;
	int periodoBSCActual;
	int objetivoActual;
	ArrayList<ObjetivosBSC> listObjetivosHijos;
	TableLayout lay;
	ExpandableListView listaObjs;
	
	Context contexto;
	ObjetivosExpandableAdapter adapter;	
	
	FragmentActivity actv;
	View rootView;
	
	public MisObjetivos(){
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView  = inflater.inflate(R.layout.listar_objetivos,container, false);
			actv = getActivity();
			contexto = rootView.getContext();
			rootView.findViewById(R.layout.listar_objetivos);
			
			Resources res = getResources();
			
			/*
			 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
			 */
			spinnerPeriodo = (Spinner) rootView.findViewById(R.id.spinnerMisObjsPeriodo);
			spinnerObjetivos = (Spinner) rootView.findViewById(R.id.spinnerMisObjsObjetivo);
			
			
			lay = (TableLayout) rootView.findViewById(R.id.objsHijos);

			listaNombrePer = new ArrayList<String>();
			ListadoPeriodos lp = new ListadoPeriodos();
			Servicio.llamadaServicio(this.getActivity(), lp,Servicio.ListarPeriodos);

			 Button descartarCambios = (Button) rootView.findViewById(R.id.MisObjsDescCambios);
			 descartarCambios.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {
						  System.out.println("descarta cambios");
						  mostrarHijos();
					  }
				});
			 
			 Button guardarCambios = (Button) rootView.findViewById(R.id.MisObjsGuardarCambios);
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
	
    private void loadData(ArrayList<ObjetivosBSC> listObjetivosPadre){
    	listaObjetivosPadre = new ArrayList<ObjetivosBSC>();
    	listaNombreObj = new ArrayList<String>();
    	if (listObjetivosPadre.size() > 0){
			objetivoActual = listObjetivosPadre.get(0).ID;
		}
    	for(int i=0;i<listObjetivosPadre.size();i++){
    		System.out.println("agrega obj="+listObjetivosPadre.get(i).Nombre);
    		listaObjetivosPadre.add(listObjetivosPadre.get(i));
    		listaNombreObj.add(listObjetivosPadre.get(i).Nombre);	
      	}
    	
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,listaNombreObj);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerObjetivos.setAdapter(dataAdapter);
		
    	ListadoObjetivosChild lo = new ListadoObjetivosChild(actv);
    	String rutaLlamada ="";

    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS 2");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
	    }else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS 2");
    		rutaLlamada = Servicio.ListarObjetivosParaSubordinados+"?idColaborador="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
    	System.out.println("Ruta-Hijos="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
    }
    
	
	class TableFila extends TableRow {
		int flagUlt = 0;
		int idObjetivo = -1;
		
		public TableFila(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}		
	}

	
    public TableFila agregaFila(ObjetivosBSC objBSC, final int flagUltimo){
		final TableFila fila = new TableFila(contexto);
		fila.flagUlt=flagUltimo;
		String szNombre ="";
		String szPeso ="0";
		String szAporte ="0";
		//String szCreador=LoginActivity.getUsuario().getUsername();
		
		if(objBSC != null){
			szNombre=objBSC.Nombre;
			szPeso = Integer.toString(objBSC.Peso);
			fila.idObjetivo = objBSC.ID;
			szAporte = Integer.toString(objBSC.PesoMiObjetivo);
			//szCreador = LoginActivity.getUsuario().getUsername(); //objBSC.CreadorID;
		}
		
		fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		int a=85,b=15, c=0;
		if(indicador==IND_MISOBJS){
			a=70; b=15; c=15;			
		}
		
	    EditText descripObj = new EditText(contexto);
	    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);

	    descripObj.setText(szNombre);
	    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,a));
	    fila.addView(descripObj);
		
	    EditText peso = new EditText(contexto);
	    peso.setInputType(InputType.TYPE_CLASS_NUMBER);

	    peso.setText(szPeso);
	    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,b));
	    fila.addView(peso);
	    
	    if(indicador==IND_MISOBJS){
		    EditText aporte = new EditText(contexto);
		    aporte.setInputType(InputType.TYPE_CLASS_NUMBER);

		    aporte.setText(szAporte);
		    aporte.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,c));
		    fila.addView(aporte);		    	
	    }
	    
	    Button eliminarObj = new Button(contexto);
	    eliminarObj.setText("X");
	    eliminarObj.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  lay.removeView(fila);
				  
				  if(fila.flagUlt==1){
					  TableFila filaUlt=agregaFila(null, 1);
					  lay.addView(filaUlt);
				  }
			  }
		});
	    fila.addView(eliminarObj);	
	    
	    /**BOTON AUMENTAR - INICIO**/
	    final Button aumentarObj = new Button(contexto);
	    aumentarObj.setText("+");
	    aumentarObj.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {	
				  aumentarObj.setVisibility(View.INVISIBLE); //elimina el boton
				  fila.flagUlt=0;
				  TableFila filaUlt=agregaFila(null, 1);
				  lay.addView(filaUlt);		 
			  }
		});
	    fila.addView(aumentarObj); 
	    
	    if(fila.flagUlt!=1){
	    	aumentarObj.setVisibility(View.INVISIBLE);	
	    }
	    /**BOTON AUMENTAR - FIN**/
	    System.out.println("retorna fila");
	return fila;
}
    
    public void mostrarHijos(){
    	System.out.println("mostrar hijos de obj="+objetivoActual);
    	listadoActual=obtenerHijos(objetivoActual, listObjetivosHijos);
    	
    	lay.removeAllViews();
		TableRow cabecera = agregaCabezera();
		TableRow separador_cabecera = agregaSeparadorCabezera();
		lay.addView(cabecera);
		lay.addView(separador_cabecera);
		
    	
    	if (listadoActual.size()==0){
			TableFila fila=agregaFila(null, 1);
			lay.addView(fila);				
		}
    	
    	for(int i=0;i<listadoActual.size();i++){
			int flagUltimo = 0;
			ObjetivosBSC objBSC = listadoActual.get(i);
			if ((i+1) == listadoActual.size()){
				flagUltimo=1;
			}
			System.out.println("EMF-Ingresa fila i="+i);
			TableFila fila = agregaFila(objBSC,flagUltimo);
			lay.addView(fila);
    	}
			//.addView(fila);
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
    
    private void loadDataChild(ArrayList<ObjetivosBSC> listObjetivosHijos){
    	listaObjetivosHijo = new ArrayList<ArrayList<ObjetivosBSC>>();
    	for(int i=0;i<listaObjetivosPadre.size();i++){
        	System.out.println("gordis i="+i);
    		listaObjetivosHijo.add(obtenerHijos(listaObjetivosPadre.get(i).ID,listObjetivosHijos));
    	}
    	System.out.println("evelyn");
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
    
    public boolean isAdmin(){
    	return true;
    }
	
	public  void listarObjetivos(){

    	ListadoObjetivos lo = new ListadoObjetivos(actv);
    	String rutaLlamada =""; 
    	
    	if(indicador==IND_MISOBJS){
    		System.out.println("MIS OBJETIVOS");
    		rutaLlamada = Servicio.ListarMisObjetivosSuperiores+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual;
    	}else if(indicador==IND_SUBORD){
    		System.out.println("MIS SUBORDINADOS");
			rutaLlamada = Servicio.ListarMisObjetivos+"?idUsuario="+LoginActivity.getUsuario().getID()+"&idPeriodo="+periodoBSCActual; 
    	}
    	
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
	
	public TableRow agregaCabezera(){
		TableRow cabecera = new TableRow(contexto);
		cabecera.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));

		int a=80,b=20, c=0;
		if(indicador==IND_MISOBJS){
			a=60; b=20; c=20;			
		}
		
		TextView columna1 = new TextView(contexto);
	    columna1.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,a));
	    columna1.setText("Descripción del Objetivo:");
	    cabecera.addView(columna1);
	    
	    
	    TextView columna2 = new TextView(contexto);
	    columna2.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,b));
	    columna2.setText("Peso:");
	    cabecera.addView(columna2);
	    
		if(indicador==IND_MISOBJS){
		    TextView columna3 = new TextView(contexto);
		    columna3.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,c));
		    columna3.setText("Aporte:");
		    cabecera.addView(columna3);
		}
		
	    return cabecera;
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

	public class ListadoObjetivosChild extends AsyncCall {
		
		public ListadoObjetivosChild(Activity activity) {
			super(activity);
		}
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("Recibido: " + result.toString());
				listObjetivosHijos = ObjetivosBSC.getObjetivosByResult(result);		
				loadDataChild(listObjetivosHijos);
				ocultarMensajeProgreso();
			}catch(Exception e){
				ocultarMensajeProgreso();
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	
	public class ListadoPeriodos extends AsyncCall {

		@Override
		protected void onPostExecute(String result) {
			try{
				listaPeriodos = Periodo.getPeriodosByResult(result);
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
						listarObjetivos();
							 
						//EMF-//actualizaTabs();
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
	

	
}
