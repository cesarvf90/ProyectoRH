package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;

import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost.OnTabChangeListener;


public class ObjetivosEmpresa extends Fragment {
	View rootView;
	Context contexto;
	FragmentActivity actv;
	
	private Spinner spinnerPeriodo;
	private Button btnDescCambios;
	private Button btnGuardarCambios;
	
	ArrayList<Periodo> listaPeriodos = new ArrayList<Periodo>();
	ArrayList<String> listaNombrePer = new ArrayList<String>();
	
	TableLayout layoutTab1;
	TableLayout layoutTab2;
	TableLayout layoutTab3;
	TableLayout layoutTab4;
	
	int periodoBSCActual;
	String titulo;
	
	int perspectivaActual;
	
	ArrayList<ObjetivosBSC> objetivosLeidos;
	
	public ObjetivosEmpresa(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void actualizaTabs(){
		objetivosLeidos= new ArrayList<ObjetivosBSC>();
		layoutTab1.removeAllViews();
		layoutTab2.removeAllViews();
		layoutTab3.removeAllViews();
		layoutTab4.removeAllViews();
		
		AgregaDatosTab(1);
		AgregaDatosTab(2);
		AgregaDatosTab(3);
		AgregaDatosTab(4);
	}
	
	public ObjetivosBSC getObjLeido(int id){
		ObjetivosBSC obj = new ObjetivosBSC();
		for(int i=0;i<objetivosLeidos.size();i++){
			if(objetivosLeidos.get(i).ID==id){
				obj=objetivosLeidos.get(i);
				break;
			}
		}
		return obj;
	}
	
	public ObjetivosBSC cambiaEstadoNoEliminar(int id){
		ObjetivosBSC obj = new ObjetivosBSC();
		for(int i=0;i<objetivosLeidos.size();i++){
			if(objetivosLeidos.get(i).ID==id){
				objetivosLeidos.get(i).seElimina=false;
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
	
	public void validaCambios(ObjetivosBSC obj, int contFila, int numLay){
		System.out.println("valida de objID="+obj.ID+ " n="+obj.Nombre+" y p="+obj.Peso+ " -tP="+obj.TipoObjetivoBSCID);
		obj.seElimina=false;
		cambiaEstadoNoEliminar(obj.ID);
		if(obj.ID==-1){
			creaObjetivo(obj,contFila, numLay);
		}else{			
			if(comparaObjs(obj,getObjLeido(obj.ID))==false){
				actualizaObjetivo(obj,contFila);	
			}
		}
	}
	
	public void creaObjetivo(ObjetivosBSC obj, int contFila, int numLayout){
		System.out.println("--->Creara");
		AddObjetivo co = new AddObjetivo();
		co.obj= obj;
		co.contFila = contFila;
		co.numLay = numLayout;
		String rutaLlamada = Servicio.CrearObjetivoBSC+"?Nombre="+obj.Nombre+"&Peso="+obj.Peso+"&TipoObjetivoBSCID="+obj.TipoObjetivoBSCID+"&BSCID="+periodoBSCActual;
		System.out.println("------->EMF-rutaCrear="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
	}
	
	public void actualizaObjetivo(ObjetivosBSC obj,int contFila){
		System.out.println("-->Actualizar");
		UpdateObjetivo co = new UpdateObjetivo();
		co.obj= obj;
		String rutaLlamada = Servicio.ActualizaObjetivoBSC+"?Nombre="+obj.Nombre+"&Peso="+obj.Peso+"&TipoObjetivoBSCID="+obj.TipoObjetivoBSCID+"&BSCID="+periodoBSCActual+"&ID="+obj.ID;
		System.out.println("------->EMF-rutaActualizar="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
	}
	
	public void eliminaObjetivos(){
		System.out.println("eliminar objs");
		for(int i=0;i<objetivosLeidos.size();i++){
			System.out.println("-Analiza obj="+objetivosLeidos.get(i).Nombre + " con estado="+objetivosLeidos.get(i).seElimina);
			if(objetivosLeidos.get(i).seElimina){
				ObjetivosBSC obj = objetivosLeidos.get(i);
				System.out.println("--->Eliminara Obj="+obj.Nombre);
				DeleteObjetivo co = new DeleteObjetivo();
				co.numObj=i;
				String rutaLlamada = Servicio.EliminarObjetivoBSC+"?objetivoID="+obj.ID;
				System.out.println("------->EMF-rutaCrear="+rutaLlamada);
				Servicio.llamadaServicio(this.getActivity(), co,rutaLlamada);
			}
		}
	}
	
	public class AddObjetivo extends AsyncCall {
		ObjetivosBSC obj;
		int contFila;
		int numLay;
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
					objetivosLeidos.add(obj);
					System.out.println("agregado obj con id="+obj.ID);
					
					if(numLay==1){
						((TableFila)layoutTab1.getChildAt(contFila)).idObjetivo=idRecibido;
					}else if (numLay==2){
						((TableFila)layoutTab2.getChildAt(contFila)).idObjetivo=idRecibido;
					}else if (numLay==3){
						((TableFila)layoutTab3.getChildAt(contFila)).idObjetivo=idRecibido;
					}else if (numLay==4){
						((TableFila)layoutTab4.getChildAt(contFila)).idObjetivo=idRecibido;
					}
				}
			} catch (Exception e) {
				System.out.println("SE CAYO ADD="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class UpdateObjetivo extends AsyncCall {
		ObjetivosBSC obj;
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
			} catch (Exception e) {
				System.out.println("SE CAYO ACT="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class DeleteObjetivo extends AsyncCall {
		int numObj;
		@Override
		protected void onPostExecute(String result) {
			System.out.println("RecibidoDeleteObj: " + result.toString());
			try {
				System.out.println("recibio ok");
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (ConstanteServicio.SERVICIO_OK.equals(respuesta)) {
					System.out.println("eliminara");
					objetivosLeidos.remove(numObj);
				}
			} catch (Exception e) {
				System.out.println("SE CAYO DEL="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	public class ListadoObjetivos extends AsyncCall {
		int auxPerspectiva = perspectivaActual;
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			ArrayList<ObjetivosBSC> listObjetivosBSC = ObjetivosBSC.getObjetivosByResult(result);
				
			if (listObjetivosBSC.size()==0){
				TableFila fila=agregaFila(auxPerspectiva,null, 1);
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
			//FILAS
			for(int i=0;i<listObjetivosBSC.size();i++){
				int flagUltimo = 0;
				ObjetivosBSC objBSC = listObjetivosBSC.get(i);
				objetivosLeidos.add(objBSC);
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
		}
	}
		
	public class ListadoPeriodos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			listaPeriodos=Periodo.getPeriodosByResult(result);
			for(int i=0; i<listaPeriodos.size(); i++){
				listaNombrePer.add(listaPeriodos.get(i).Nombre);	
			}
			
			if(listaPeriodos!=null){				
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

	public TableRow agregaCabezera(){
		TableRow cabecera = new TableRow(contexto);
		cabecera.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));

		TextView columna1 = new TextView(contexto);
	    columna1.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,80));
	    columna1.setText("Descripción del Objetivo:");
	    cabecera.addView(columna1);
	    
	    
	    TextView columna2 = new TextView(contexto);
	    columna2.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
	    columna2.setText("Peso:");
	    cabecera.addView(columna2);
	    
	    /*
	    TextView columna3 = new TextView(contexto);
	    columna3.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
	    columna3.setText("Creador:");
	    cabecera.addView(columna3);
	    */
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
	
	class EditTextObjetivo extends EditText{
		int idObj;

		public EditTextObjetivo(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
	}
	
	public TableFila agregaFila(final int numLayout,ObjetivosBSC objBSC, final int flagUltimo){
			final TableFila fila = new TableFila(contexto);
			fila.flagUlt=flagUltimo;
			String szNombre ="";
			String szPeso ="0";
			//String szCreador=LoginActivity.getUsuario().getUsername();
			
			if(objBSC != null){
				szNombre=objBSC.Nombre;
				szPeso = Integer.toString(objBSC.Peso);
				fila.idObjetivo = objBSC.ID;
				//szCreador = LoginActivity.getUsuario().getUsername(); //objBSC.CreadorID;
			}
			
			fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		    EditText descripObj = new EditText(contexto);
		    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);

		    descripObj.setText(szNombre);
		    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
		    fila.addView(descripObj);
			
		    EditText peso = new EditText(contexto);
		    peso.setInputType(InputType.TYPE_CLASS_NUMBER);

		    peso.setText(szPeso);
		    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
		    fila.addView(peso);
		    
		    /*
		    TextView creador = new TextView(contexto);

		    creador.setText(szCreador);
		    creador.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
		    fila.addView(creador);
		    */
		    
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
		    
		    /**BOTON AUMENTAR - INICIO**/
		    final Button aumentarObj = new Button(contexto);
		    aumentarObj.setText("+");
		    aumentarObj.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {	
					  aumentarObj.setVisibility(View.INVISIBLE); //elimina el boton
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
		    
		    if(fila.flagUlt!=1){
		    	aumentarObj.setVisibility(View.INVISIBLE);	
		    }
		    /**BOTON AUMENTAR - FIN**/
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
			actv = getActivity();
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
			 
			 Button guardarCambios = (Button) rootView.findViewById(R.id.ObjEmpGuardarCambios);
			 	guardarCambios.setOnClickListener(new OnClickListener() {
					  @Override
					  public void onClick(View v) {
						  System.out.println("guarda cambios");
						  //VALIDAR TODOS LOS OBJETIVOS
						  for(int k=0;k<objetivosLeidos.size();k++){
								objetivosLeidos.get(k).seElimina=true;
						  }
						  if(validaSumas()){
							  guardaLayouts(layoutTab1,1);
							  guardaLayouts(layoutTab2,2);
							  guardaLayouts(layoutTab3,3);
							  guardaLayouts(layoutTab4,4);
							  eliminaObjetivos();
						  }
					  }
				});
		return rootView;
	}

	public boolean validaSumas(){
		return validaSumaLay(layoutTab1,1) && validaSumaLay(layoutTab2,2) && validaSumaLay(layoutTab3,3)&&validaSumaLay(layoutTab4,4);
	}
	
	public boolean validaSumaLay(TableLayout lay, int tipoObjetivo){
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
		
		System.out.println("peso final="+pesoFinal+" en perspectiva="+tipoObjetivo);
		if(pesoFinal==100||pesoFinal==0){
			return true;
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Error en Suma de Pesos");
			builder.setMessage("La suma de pesos es "+pesoFinal+" y debería ser 100.\nCorregir la pestaña "+getNombrePerspectiva(tipoObjetivo));
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
	  }
		return false;
	}
	
	public String getNombrePerspectiva(int id){
		if(id==1) return "Financiero";
		if(id==2) return "Aprendizaje y Crecimiento";
		if(id==3) return "Cliente";
		if(id==4) return "Procesos Internos";
		return "";
	}
	
	public void guardaLayouts(TableLayout lay,int tipoObjetivo){
		System.out.println("analiza lay="+tipoObjetivo);
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
				  myObj.TipoObjetivoBSCID = tipoObjetivo;
				  System.out.println("----->Nombre="+myObj.Nombre);
				  if(!(myObj.Nombre==null || myObj.Nombre=="" || myObj.Nombre.isEmpty())){
					  validaCambios(myObj,i, tipoObjetivo);
				  }
				}
		  }
	}

}
