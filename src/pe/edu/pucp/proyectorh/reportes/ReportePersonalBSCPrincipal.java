package pe.edu.pucp.proyectorh.reportes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.miinformacion.ConsultarEquipoTrabajoFragment.deserializarJSON;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.reportes.ReporteObjetivosBSCPrincipal.getReportePeriodo;
import pe.edu.pucp.proyectorh.reportes.ReportePersonalBSCGrafico.HistoricoBSC;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.NetDateTimeAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;

public class ReportePersonalBSCPrincipal extends Fragment {
	Spinner spinnerColaborador;
	ArrayList<String> lista;
	Button btnSubmit;
	String titulo;
	int colaboradorSelec;
	
	ArrayList<ColaboradorEquipoTrabajo> colaboradores = new ArrayList<ColaboradorEquipoTrabajo>();
	
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
		llamarServicioConsultarEquipoTrabajo(LoginActivity.getUsuario().getID());

		btnSubmit = (Button) rootView.findViewById(R.id.reporteperbscbtnConsultar);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  
				  if( PersistentHandler.buscarArchivo(getActivity(), "ObjPersonal" + colaboradorSelec +".txt")){
					  
					  ReportePersonalBSCGrafico fragment = new ReportePersonalBSCGrafico();
					  Bundle argumentos = new Bundle();
				      argumentos.putInt("ColaboradorSelec", colaboradorSelec);
				      argumentos.putString("titulo", titulo);
				      fragment.setArguments(argumentos);
				      
					  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
					  ft.replace(R.id.opcion_detail_container, fragment);
					  ft.addToBackStack(null);
					  ft.commit();
						
					}
				  else{
					  obtenerReporteOffline(colaboradorSelec);
				  }

			  }
		});
		
		
			
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}
	
	protected void obtenerReporteOffline(int idColab){
		
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			
			String request = ReporteServices.obtenerHistoricoObjetivos + "?idColaborador=" + idColab;

			new getReporteColaborador(getActivity()).execute(request);
			
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Error de conexión");
			builder.setMessage("No se pudo conectar con el servidor. Revise su conexión a Internet.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
		
	}
	
public class getReporteColaborador extends AsyncCall{
		
		public getReporteColaborador(Activity activity) {
			super(activity);
		}
	
	
		@Override
		protected void onPostExecute(String result) {
			
			ocultarMensajeProgreso();

			System.out.println("Recibido: " + result.toString());
			
			
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

			PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + result, getActivity(), "ObjPersonal" + colaboradorSelec +".txt");
		      
			ReportePersonalBSCGrafico fragment = new ReportePersonalBSCGrafico();
			  Bundle argumentos = new Bundle();
		      argumentos.putInt("ColaboradorSelec", colaboradorSelec);
		      argumentos.putString("titulo", titulo);
		      fragment.setArguments(argumentos);
		      
			  FragmentTransaction ft  =  getActivity().getSupportFragmentManager().beginTransaction();
			  ft.replace(R.id.opcion_detail_container, fragment);
			  ft.addToBackStack(null);
			  ft.commit();
			
			
		}
		
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
	
	private void llamarServicioConsultarEquipoTrabajo(String idUsuario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.getEquipoTrabajo + "?colaboradorID="
					+ idUsuario;
			System.out.println("pagina: " + request);
			new deserializarJSON().execute(request);
		}
		else{
			//offline
			String trama = PersistentHandler.getColaboradorFromFile(getActivity(), "colabReporte" + idUsuario  + ".txt");
			
			if (trama!=null){
			
				probarDeserializacionJSON(trama);
				//colaboradores
				for (int i=0;i<colaboradores.size();i++){
					lista.add(colaboradores.get(i).getNombres() + " " + colaboradores.get(i).getApellidoPaterno());
				}
				ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerColaborador.setAdapter(dataAdapter);
				spinnerColaborador.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
						
					/*	Toast.makeText(parent.getContext(), 
							"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
							Toast.LENGTH_SHORT).show(); */
						
						colaboradorSelec = Integer.parseInt(colaboradores.get(pos).getId());
						titulo = parent.getItemAtPosition(pos).toString();
	
					  }
					
				
					@Override
					  public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					  }
					
				});
		
			}
		}
	}

	public class deserializarJSON extends AsyncCall {
	
		
		@Override
		protected void onPostExecute(String result) {
			// MISMA LOGICA QUE probarDeserializacionGSON
			
			String trama=result;
			
			probarDeserializacionJSON(result);
			
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
			PersistentHandler.agregarArchivoPersistente(currentDateTimeString + "\n" + trama, getActivity(), "colabReporte"  +  LoginActivity.getUsuario().getID() +".txt");
			
			//colaboradores
			for (int i=0;i<colaboradores.size();i++){
				lista.add(colaboradores.get(i).getNombres() + " " + colaboradores.get(i).getApellidoPaterno());
			}
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lista);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerColaborador.setAdapter(dataAdapter);
			spinnerColaborador.setOnItemSelectedListener(new OnItemSelectedListener(){
				
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					
				/*	Toast.makeText(parent.getContext(), 
						"seleccionado : " + parent.getItemAtPosition(pos).toString() + " id: " + listaPeriodos.get(pos).getID(),
						Toast.LENGTH_SHORT).show(); */
					
					colaboradorSelec = Integer.parseInt(colaboradores.get(pos).getId());
					titulo = parent.getItemAtPosition(pos).toString();

				  }
				
			
				@Override
				  public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				  }
				
			});
			
			
			//ocultarMensajeProgreso();
		}
	}
	

	 public void probarDeserializacionJSON(String str) {
			String result;
			 
			
			// "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"CHRISTIAN MENDEZ\",\"area\":\"Logistica y Operaciones\",\"puesto\":\"Subgerente de Análisis\",\"anexo\":\"2891\",\"email\":\"jperez@rhpp.com\",\"cantidadSubordinados\":\"3\",\"listaSubordinados\":{\"colaborador1\":{\"nombreCompleto\":\"Carla Sanchez\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1A\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}},\"subcolaborador2\":{\"nombreCompleto\":\"practicante2A\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador2\":{\"nombreCompleto\":\"Mateo Soto\",	\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"1\",\"listaSubordinados\":{\"subcolaborador1\":{\"nombreCompleto\":\"practicante1B\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}},\"colaborador3\":{\"nombreCompleto\":\"Diego Bernal\",\"area\":\"xxxxx\",\"puesto\":\"xxxxx\",\"anexo\":\"xxxxx\",\"email\":\"xxxxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":{}}}}}";
			if (str != "")
				result = str;
			else
				// cadena ejemplo antes de que manolin termine el ws
				// result =
				// "{\"respuesta\":\"1\",\"jefeEquipo\":{\"nombreCompleto\":\"big boss\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"2\",	\"listaSubordinados\":[{\"nombreCompleto\":\"nombre1\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"2\",\"listaSubordinados\":[{\"nombreCompleto\":\"nombre2\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\"},{\"nombreCompleto\":\"nombre3\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\"}]},{\"nombreCompleto\":\"nombre4\",\"area\":\"xxx\",\"puesto\":\"xxx\",\"anexo\":\"xxx\",\"email\":\"xxx\",\"cantidadSubordinados\":\"0\",\"listaSubordinados\":[]}]}}";
				// cadena real despues que manolin termino el ws
				result = "{\"success\":true,\"data\":{\"NombreCompleto\":\"Moreno Reyes, Fortino Mario Alonso\",\"ID\":2,\"Nombre\":\"Fortino Mario Alonso\",\"ApellidoPaterno\":\"Moreno\",\"ApellidoMaterno\":\"Reyes\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":1,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":1,\"Area\":\"Directorio\",\"PuestoID\":1,\"Puesto\":\"Presidente\",\"Sueldo\":2300,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[],\"Subordinados\":[{\"NombreCompleto\":\"Vega Buendía, Miguel\",\"ID\":3,\"Nombre\":\"Miguel\",\"ApellidoPaterno\":\"Vega\",\"ApellidoMaterno\":\"Buendía\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":1,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":2,\"Area\":\"Gerencia general\",\"PuestoID\":2,\"Puesto\":\"Gerente general\",\"Sueldo\":2500,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[],\"Subordinados\":[{\"NombreCompleto\":\"Chavez Moreno, Rodrigo\",\"ID\":23,\"Nombre\":\"Rodrigo\",\"ApellidoPaterno\":\"Chavez\",\"ApellidoMaterno\":\"Moreno\",\"Telefono\":null,\"Direccion\":null,\"PaisID\":1,\"FechaNacimiento\":null,\"TipoDocumentoID\":2,\"NumeroDocumento\":null,\"Usuario\":null,\"EstadoColaboradorID\":1,\"CurriculumVitaeID\":0,\"ImagenColaboradorID\":0,\"CentroEstudios\":null,\"GradoAcademicoID\":0,\"CorreoElectronico\":null,\"FechaIngreso\":null,\"AreaID\":6,\"Area\":\"Márketing\",\"PuestoID\":5,\"Puesto\":\"Gerente de márketing\",\"Sueldo\":2000,\"ResumenEjecutivo\":null,\"Contrasenha\":null,\"NuevaContrasenha\":null,\"Objetivos\":[{\"ID\":5,\"Nombre\":\"Aumentar las ventas\",\"Peso\":50,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"},{\"ID\":6,\"Nombre\":\"Reducir los costos\",\"Peso\":25,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"},{\"ID\":7,\"Nombre\":\"Ganar nuevos clientes\",\"Peso\":25,\"AvanceFinal\":10,\"TipoObjetivoBSCID\":0,\"ObjetivoPadreID\":1,\"BSCID\":1,\"FechaDePropuesta\":\"\",\"FechaFinalizacion\":\"\"}],\"Subordinados\":null}]}]}}";
			// System.out.println("Recibido: " + result.toString());
			// deserializando el json parte por parte
			try {
				JSONObject jsonObject = new JSONObject(result);
				System.out.println("result: " + result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					// System.out.println("respuesta: "+respuesta);
					// Obtenemos el jefe de todos --NIVEL 0
					JSONObject dataObject = (JSONObject) jsonObject.get("data");
					JSONObject jefeObject = (JSONObject) dataObject.get("jefe");
					ColaboradorEquipoTrabajo jefe = new ColaboradorEquipoTrabajo(jefeObject.getString("ID"),
							jefeObject.getString("Nombre"),
							jefeObject.getString("ApellidoPaterno"),
							jefeObject.getString("ApellidoMaterno"),
							jefeObject.getString("Area"),
							jefeObject.getString("Puesto"),
							jefeObject.getString("Telefono"),
							jefeObject.getString("CorreoElectronico"));
					// jefeObject.getInt("cantidadSubordinados"));

					System.out.println("jefe: " + jefe.toString());
					// Obtenemos la lista de subordinados del jefe de todos --NIVEL
					// 1
					JSONArray listaSubordinadosNivel1 = (JSONArray) jefeObject
							.get("Subordinados");

					
					System.out.println("listaSubordinadosNivel1: "
							+ listaSubordinadosNivel1.toString());

					// Para cada uno de los subordinados del jefe de todos
					JSONObject subordinadoNivel1Object;
					
					
					
					colaboradores.add(jefe);
					for (int i = 0; i < listaSubordinadosNivel1.length(); i++) {
						subordinadoNivel1Object = listaSubordinadosNivel1
								.getJSONObject(i);

						ColaboradorEquipoTrabajo subordinadoNivel1 = new ColaboradorEquipoTrabajo(
								subordinadoNivel1Object.getString("ID"),
								subordinadoNivel1Object.getString("Nombre"),
								subordinadoNivel1Object
										.getString("ApellidoPaterno"),
								subordinadoNivel1Object
										.getString("ApellidoMaterno"),
								subordinadoNivel1Object.getString("Area"),
								subordinadoNivel1Object.getString("Puesto"),
								subordinadoNivel1Object.getString("Telefono"),
								subordinadoNivel1Object
										.getString("CorreoElectronico"));

						String nombre = subordinadoNivel1.getNombres();
						if (nombre.indexOf(" ") > -1) {
							nombre = nombre.substring(0, nombre.indexOf(" "));
							subordinadoNivel1.setNombres(nombre);
						}

						colaboradores.add(subordinadoNivel1);
						//System.out.println(subordinadoNivel1.toString());
						JSONArray listaSubordinadosNivel2 = (JSONArray) subordinadoNivel1Object
								.get("Subordinados");
						
						JSONObject subordinadoNivel2Object;

						for (int j = 0; j < listaSubordinadosNivel2.length(); j++) {
							subordinadoNivel2Object = (JSONObject) listaSubordinadosNivel2
									.get(j);

							ColaboradorEquipoTrabajo subordinadoNivel2 = new ColaboradorEquipoTrabajo(
									subordinadoNivel2Object.getString("ID"),
									subordinadoNivel2Object.getString("Nombre"),
									subordinadoNivel2Object
											.getString("ApellidoPaterno"),
									subordinadoNivel2Object
											.getString("ApellidoMaterno"),
									subordinadoNivel2Object.getString("Area"),
									subordinadoNivel2Object.getString("Puesto"),
									subordinadoNivel2Object.getString("Telefono"),
									subordinadoNivel2Object
											.getString("CorreoElectronico"));

							colaboradores.add(subordinadoNivel2);

							//System.out.println(subordinadoNivel2.toString());

						}
					}

				}
			} catch (JSONException e) {
				System.out.println("entre al catch1");
				System.out.println(e.toString());

				mostrarErrorComunicacion(e.toString());
			} catch (NullPointerException ex) {
				System.out.println("entre al catch2");
				System.out.println(ex.toString());

				mostrarErrorComunicacion(ex.toString());
			}
		}
	 
	 public boolean procesaRespuesta(String respuestaServidor) {
			if ("true".equals(respuestaServidor)) {
				return true;

			} else {
				// Se muestra mensaje de error
				AlertDialog.Builder builder = new AlertDialog.Builder(
						this.getActivity());
				builder.setTitle("Problema en el servidor");
				builder.setMessage("Hay un problema en el servidor.");
				builder.setCancelable(false);
				builder.setPositiveButton("Ok", null);
				builder.create();
				builder.show();
				return false;
			}
		}
	 
	 private void mostrarErrorComunicacion(String excepcion) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this
					.getActivity());
			builder.setTitle("Error de servicio");
			builder.setMessage("El servicio solicitado no está disponible en el servidor: "
					+ excepcion.toString());
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	

}
