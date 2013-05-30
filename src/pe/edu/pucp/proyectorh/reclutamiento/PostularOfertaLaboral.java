package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PostularOfertaLaboral extends Fragment {

	private View rootView;
	private View layoutVacio;
	private ListView listaOfertasLaborales;
	private ArrayAdapter<SolicitudOfertaLaboral> solicitudesAdapter;
	private ArrayList<SolicitudOfertaLaboral> solicitudes = null;
	private static final String OPERACION_VALIDA = "true";
	private static final String OPERACION_INVALIDA = "false";
	private int IDSolicitudSeleccionada;
	private Button aceptarButton;
	private int posicionLista = -1;
	private SolicitudOfertaLaboral solicitud;

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
		this.listaOfertasLaborales = (ListView) rootView
				.findViewById(R.id.reclut_lista_ofertas_laborales);
		this.aceptarButton = (Button) this.rootView
				.findViewById(R.id.reclu_btn_Validar);

		// Llamamos al WS que poblará "solicitudes"
		// llamarServiciosAprobarSolicitudOfertaLaboral("laboral");
		probarDeserializacionJSON("");

		if (solicitudes != null) {
			this.solicitudesAdapter = new ArrayAdapter<SolicitudOfertaLaboral>(
					this.getActivity(), android.R.layout.simple_list_item_1,
					solicitudes);
			listaOfertasLaborales.setAdapter(solicitudesAdapter);
			listaOfertasLaborales
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View childView, int position, long id) {
							// position tiene la posicion de la vista en el
							// adapter
							mostrarSolicitudSeleccionada(solicitudes
									.get(position));
							// obtenemos el id de la solicitud seleccionada
							// IDSolicitudSeleccionada =
							// solicitudes.get(position).getSolicitudID();

							IDSolicitudSeleccionada = solicitudes.get(position)
									.getSolicitudID();
							posicionLista = position;
						}
					});

			aceptarButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if ((solicitudes.size() > 0) && (posicionLista != -1)) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setTitle("Postular a Oferta Laboral");
						builder.setMessage("¿Desea postular a la oferta laboral?");
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
											// comunicarle al ws que se acepto
											// solicitud oferta
											// laboral

											EditText observacion = (EditText) rootView
													.findViewById(R.id.reclut_comentarios_input);
											// enviarMensajeWS(aprobada,
											// IDSolicitudSeleccionada,
											// observacion);
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

			return rootView;
		} else {
			// Caso contrario, mostramos una vista vacía
			this.layoutVacio = inflater.inflate(
					R.layout.layout_vacio_para_errores, container, false);
			return layoutVacio;
		}

	}

	private void llamarServiciosAprobarSolicitudOfertaLaboral(
			String tipoSolicitud) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.AprobarSolicitudOfertaLaboral + "?tipo="
					+ tipoSolicitud;
			System.out.println("pagina: " + request);
			new deserializarJSON().execute(request);
		}
	}

	public class deserializarJSON extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
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
			result = "{\"success\":true,\"solicitudes\":[{\"solicitudID\":1,\"area\":\"TI\",\"cargo\":\"Practicante\",\"fechaRequerimiento\":\"01/05/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Teodoro Santos\",\"comentarios\":\"se requiere practicante para soporte\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":2,\"area\":\"Marketing\",\"cargo\":\"Asistente\",\"fechaRequerimiento\":\"22/03/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Karla River\",\"comentarios\":\"Se requiere asistente de investigación\",\"observacion\":null,\"estado\":\"pendiente\"},{\"solicitudID\":3,\"area\":\"Contabilidad\",\"cargo\":\"Analista\",\"fechaRequerimiento\":\"13/11/2013\",\"modoPublicacion\":\"publico\",\"responsable\":\"Sergio Fuentes\",\"comentarios\":\"se requiere analista con o sin experiencia\",\"observacion\":null,\"estado\":\"pendiente\"}]}";
		System.out.println("Recibido: " + result.toString());
		// deserializando el json parte por parte
		try {
			JSONObject jsonObject = new JSONObject(result);
			System.out.println("result: " + result);
			String respuesta = jsonObject.getString("success");
			if (procesaRespuesta(respuesta)) {
				JSONArray listaOfertasLaborales = (JSONArray) jsonObject
						.get("data");

				JSONObject solicitudObject;
				solicitudes = new ArrayList<SolicitudOfertaLaboral>();
				for (int i = 0; i < listaOfertasLaborales.length(); i++) {
					solicitudObject = listaOfertasLaborales.getJSONObject(i);

					solicitud = new SolicitudOfertaLaboral(
							solicitudObject.getInt("ID"),
							solicitudObject.getString("area"),
							solicitudObject.getString("cargo"),
							formatoFecha.parse(solicitudObject
									.getString("fechaRequerimiento")),
							solicitudObject.getString("modoPublicacion"),
							solicitudObject.getString("responsable"),
							solicitudObject.getString("comentarios"),
							solicitudObject.getString("observacion"),
							solicitudObject.getString("estado"));

					solicitudes.add(solicitud);
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
		} catch (ParseException e) {
			e.printStackTrace();
			mostrarErrorComunicacion(e.toString());
		}
	}

	private void mostrarErrorComunicacion(String excepcion) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this
				.getActivity().getApplicationContext());
		builder.setTitle("Error de servicio");
		builder.setMessage("El servicio solicitado no está disponible en el servidor: "
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (OPERACION_VALIDA.equals(respuestaServidor)) {
			return true;
		} else if (OPERACION_INVALIDA.equals(respuestaServidor)) {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this
					.getActivity().getApplicationContext());
			builder.setTitle("Login inválido");
			builder.setMessage("Combinación de usuario y/o contraseña incorrectos.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		} else {
			// Se muestra mensaje de usuario invalido
			AlertDialog.Builder builder = new AlertDialog.Builder(this
					.getActivity().getApplicationContext());
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

		TextView cargo = (TextView) rootView
				.findViewById(R.id.reclut_cargo_input);
		cargo.setText(solicitudOfertaLaboral.getCargo() == "null" ? " - "
				: solicitudOfertaLaboral.getCargo());

		TextView fechaRequerimiento = (TextView) rootView
				.findViewById(R.id.reclut_fecha_input);
		if (solicitudOfertaLaboral.getFechaRequerimiento() != null) {
			fechaRequerimiento.setText(formatoFecha
					.format(solicitudOfertaLaboral.getFechaRequerimiento()));
		} else {
			fechaRequerimiento.setText("");
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

		TextView comentarios = (TextView) rootView
				.findViewById(R.id.reclut_descripcion_input);
		if ((solicitudOfertaLaboral.getComentarios() == null)
				|| ((solicitudOfertaLaboral.getComentarios() == "null"))) {
			comentarios.setText("");
		} else {
			comentarios.setText(solicitudOfertaLaboral.getComentarios());
		}

		EditText observacion = (EditText) rootView
				.findViewById(R.id.reclut_comentarios_input);
		if ((solicitudOfertaLaboral.getObservacion() == null)
				|| ((solicitudOfertaLaboral.getObservacion() == "null"))) {
			observacion.setText("");
		} else {
			observacion.setText(solicitudOfertaLaboral.getObservacion());
		}
	}

	private void enviarMensajeWS(String nuevoEstado, int ID, String comentario) {
		if (ConnectionManager.connect(this.getActivity())) {
			// construir llamada al servicio
			String request = Servicio.RespuestaAprobarSolicitudOfertaLaboral
					+ "?nuevoEstado=" + nuevoEstado + "?ID=" + ID
					+ "?comentario=" + comentario;
			System.out.println("pagina: " + request);
		}
	}

}
