package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.SolicitudOfertaLaboral;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PostularOfertaLaboral extends Fragment {

	private View rootView;
	private View layoutVacio;
	private ListView listaSolicitudes;
	private ArrayAdapter<String> solicitudesAdapter;
	private ArrayList<SolicitudOfertaLaboral> solicitudes = null;
	private ArrayList<String> puestosSolicitudes = null;
	private static final String OPERACION_VALIDA = "true";
	private static final String OPERACION_INVALIDA = "false";
	private int IDOfertaLaboral;
	private Button postularButton;
	
	private int posicionLista = -1;
	private SolicitudOfertaLaboral solicitud;
	boolean espera = true;

	public PostularOfertaLaboral() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(
				R.layout.postular_oferta_laboral, container, false);

		// Llamamos al WS que poblar� "solicitudes"
		/*
		 * synchronized (this) { jcjj = true;
		 * llamarServiciosAprobarSolicitudOfertaLaboral("Pendiente"); try {
		 * this.wait(3000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */
		llamarServicioObtenerOfertasLaborales("Aprobado");

		// while (espera);
		// probarDeserializacionJSON("");

		// if (solicitudes != null) {

		/*
		 * } else { System.out.println("solicitudes == NULL"); // Caso
		 * contrario, mostramos una vista vac�a this.layoutVacio =
		 * inflater.inflate( R.layout.layout_vacio_para_errores, container,
		 * false); return layoutVacio; }
		 */
		return rootView;
	}

	private void llamarServicioObtenerOfertasLaborales(String estado) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.AprobarSolicitudOfertaLaboral
					+ "?estadoOfertaLaboral=" + estado;
			System.out.println("pagina: " + request);
			new deserializarJSON().execute(request);
		}
	}

	public class deserializarJSON extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("result: " + result);
			probarDeserializacionJSON(result);
		}
	}

	public void probarDeserializacionJSON(String str) {
		String result;
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		if (str != "")
			result = str;
		else
			result = "{\"success\":true,\"solicitudes\":[{\"solicitudID\":1,\"area\":\"TI\",\"cargo\":\"Practicante\",\"fechaRequerimiento\":\"01/05/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Teodoro Santos\",\"comentarios\":\"se requiere practicante para soporte\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":2,\"area\":\"Marketing\",\"cargo\":\"Asistente\",\"fechaRequerimiento\":\"22/03/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Karla River\",\"comentarios\":\"Se requiere asistente de investigaci�n\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":3,\"area\":\"Contabilidad\",\"cargo\":\"Analista\",\"fechaRequerimiento\":\"13/11/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Sergio Fuentes\",\"comentarios\":\"se requiere analista con o sin experiencia\",\"observacion\":null,\"estado\":\"pendiente\"}]}";
		System.out.println("Recibido: " + result.toString());
		// deserializando el json parte por parte
		try {
			JSONObject jsonObject = new JSONObject(result);
			// System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			if (procesaRespuesta(respuesta)) {
				JSONObject data = (JSONObject) jsonObject.get("data");
				JSONArray listaOfertasLaborales = (JSONArray) data
						.get("ofertasLaborales");

				solicitudes = new ArrayList<SolicitudOfertaLaboral>();
				puestosSolicitudes = new ArrayList<String>();
				JSONObject solicitudObject;
				for (int i = 0; i < listaOfertasLaborales.length(); i++) {
					solicitudObject = listaOfertasLaborales.getJSONObject(i);

					solicitud = new SolicitudOfertaLaboral(
							solicitudObject.getInt("ID"),
							solicitudObject.getString("Area"),
							solicitudObject.getString("Puesto"),
							solicitudObject.getInt("NumeroVacantes"),
							solicitudObject.getInt("SueldoTentativo"),
							formatoFecha.parse(solicitudObject
									.getString("FechaRequerimiento")),
							formatoFecha.parse(solicitudObject
									.getString("FechaFinRequerimiento")),
							solicitudObject.getString("ModoSolicitud"),
							solicitudObject.getString("Responsable"),
							solicitudObject.getString("Descripcion"),
							solicitudObject.getString("Comentarios"));

					solicitudes.add(solicitud);
					puestosSolicitudes.add(solicitud.getPuesto());
				}
				mostrarSolicitudes();
			}
		} catch (JSONException e) {
			System.out.println("entre al catch1");
			System.out.println(e.toString());
			mostrarErrorComunicacion(e.toString());
		} catch (NullPointerException ex) {
			System.out.println("entre al catch2");
			System.out.println(ex.toString());
			mostrarErrorComunicacion(ex.toString());
		} catch (ParseException e) {
			e.printStackTrace();
			mostrarErrorComunicacion(e.toString());
		}

	}

	private void mostrarSolicitudes() {
		this.listaSolicitudes = (ListView) rootView
				.findViewById(R.id.reclut_lista_solicit_of_laboral);
		this.postularButton = (Button) this.rootView
				.findViewById(R.id.reclu_btn_Validar);
		

		// System.out.println("solicitudes != NULL");
		this.solicitudesAdapter = new ArrayAdapter<String>(this.getActivity(),
				android.R.layout.simple_list_item_1, puestosSolicitudes);
		listaSolicitudes.setAdapter(solicitudesAdapter);
		listaSolicitudes
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el
						// adapter
						mostrarSolicitudSeleccionada(solicitudes.get(position));
						// obtenemos el id de la solicitud seleccionada
						// IDOfertaLaboral =
						// solicitudes.get(position).getSolicitudID();

						IDOfertaLaboral = solicitudes.get(position)
								.getID();
						posicionLista = position;
					}
				});

		postularButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ((solicitudes.size() > 0) && (posicionLista != -1)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Postular a Oferta Laboral");
					builder.setMessage("�Desea postular a la oferta laboral?");
					builder.setCancelable(false);
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.setPositiveButton("Aceptar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (posicionLista != -1) {
										solicitudes.remove(posicionLista);
										solicitudesAdapter
												.notifyDataSetChanged();
										// comunicarle al ws que se postulo
										// a la oferta
										// laboral

										//enviarPostulacionOfertaLaboral(LoginActivity.getUsuario().getID(), IDOfertaLaboral);
										posicionLista = -1; // volvemos a
															// colocar el
															// boton
															// en -1
										SolicitudOfertaLaboral nueva = new SolicitudOfertaLaboral();
										mostrarSolicitudSeleccionada(nueva);
									}
									dialog.cancel();
								}

							});
					builder.create();
					builder.show();
				}
			}
		});

		
	}

	private void mostrarErrorComunicacion(String excepcion) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle("Error de servicio");
		builder.setMessage("El servicio solicitado no est� disponible en el servidor: "
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();

	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (OPERACION_VALIDA.equals(respuestaServidor)) {
			return true;
			/*
			 * } else if (OPERACION_INVALIDA.equals(respuestaServidor)) { // Se
			 * muestra mensaje de usuario invalido AlertDialog.Builder builder =
			 * new AlertDialog.Builder(this .getActivity());
			 * builder.setTitle("Login inv�lido"); builder.setMessage(
			 * "Combinaci�n de usuario y/o contrase�a incorrectos.");
			 * builder.setCancelable(false); builder.setPositiveButton("Ok",
			 * null); builder.create(); builder.show(); return false;
			 */
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

	protected void mostrarSolicitudSeleccionada(
			SolicitudOfertaLaboral solicitudOfertaLaboral) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView area = (TextView) rootView
				.findViewById(R.id.reclut_area_input);
		area.setText(solicitudOfertaLaboral.getArea() == "null" ? " - "
				: solicitudOfertaLaboral.getArea());

		TextView puesto = (TextView) rootView
				.findViewById(R.id.reclut_cargo_input);
		puesto.setText(solicitudOfertaLaboral.getPuesto() == "null" ? " - "
				: solicitudOfertaLaboral.getPuesto());

		TextView nrovacantes = (TextView) rootView
				.findViewById(R.id.reclut_nro_vacantes_input);
		nrovacantes
				.setText(solicitudOfertaLaboral.getNroVacantes() == 0 ? " 0 "
						: String.valueOf(solicitudOfertaLaboral
								.getNroVacantes()));

		TextView sueldotentativo = (TextView) rootView
				.findViewById(R.id.reclut_sueldo_tentativo_input);
		sueldotentativo
				.setText(solicitudOfertaLaboral.getSueldoTentativo() == 0 ? " S/. 0 "
						: String.valueOf("S/. "
								+ solicitudOfertaLaboral.getSueldoTentativo()));

		TextView fechaRequerimiento = (TextView) rootView
				.findViewById(R.id.reclut_fecha_input);
		if (solicitudOfertaLaboral.getFechaRequerimiento() != null) {
			fechaRequerimiento.setText(formatoFecha
					.format(solicitudOfertaLaboral.getFechaRequerimiento()));
		} else {
			fechaRequerimiento.setText("");
		}
		
		TextView fechaLimiteSolicitud = (TextView) rootView
				.findViewById(R.id.reclut_fecha_limite_input);
		if (solicitudOfertaLaboral.getFechaLimiteSolicitud() != null) {
			fechaLimiteSolicitud.setText(formatoFecha
					.format(solicitudOfertaLaboral.getFechaLimiteSolicitud()));
		} else {
			fechaLimiteSolicitud.setText("");
		}

		TextView modoPublicacion = (TextView) rootView
				.findViewById(R.id.reclut_modo_input);
		modoPublicacion
				.setText(solicitudOfertaLaboral.getModoPublicacion() == "null" ? " - "
						: solicitudOfertaLaboral.getModoPublicacion());

		TextView responsable = (TextView) rootView
				.findViewById(R.id.reclut_responsable_input);
		responsable
				.setText(solicitudOfertaLaboral.getResponsable() == "null" ? " - "
						: solicitudOfertaLaboral.getResponsable());

		TextView descripcion = (TextView) rootView
				.findViewById(R.id.reclut_descripcion_input);
		descripcion
				.setText(solicitudOfertaLaboral.getDescripcion() == "null" ? " - "
						: solicitudOfertaLaboral.getDescripcion());
		
	}

	private void enviarPostulacionOfertaLaboral(int IDusuario,
			int IDofertaLaboral) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.EnviarPostulacionOfertaLaboral
					+ "?IDusuario=" + IDusuario + "?IDofertaLaboral=" + IDofertaLaboral;					
			System.out.println("pagina: " + request);
			new enviarMensajeWS().execute(request);
		}
	}

	public class enviarMensajeWS extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("result: " + result);
			confirmacionPostulacion(result);
		}
	}

	public void confirmacionPostulacion(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			// si no pudo actualizar, mostramos mensaje de error y volvemos a
			// mostrar todas las solicitudes pendientes
			if (!procesaRespuesta(respuesta)) {
				llamarServicioObtenerOfertasLaborales("Aprobado");
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
}
