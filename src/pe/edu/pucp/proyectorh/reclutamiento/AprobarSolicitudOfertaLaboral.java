package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.MainActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.miinformacion.ConsultarEquipoTrabajoFragment.deserializarJSON;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import pe.edu.pucp.proyectorh.model.SolicitudOfertaLaboral;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
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

public class AprobarSolicitudOfertaLaboral extends Fragment {

	private View rootView;
	private View layoutVacio;
	private ListView listaSolicitudes;
	private ArrayAdapter<SolicitudOfertaLaboral> solicitudesAdapter;
	private ArrayList<SolicitudOfertaLaboral> solicitudes = null;
	private static final String OPERACION_VALIDA = "true";
	private static final String OPERACION_INVALIDA = "false";
	private int IDSolicitudSeleccionada;
	private Button aceptarButton;
	private Button rechazarButton;
	private int posicionLista = -1;
	private SolicitudOfertaLaboral solicitud;

	public AprobarSolicitudOfertaLaboral() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.rootView = inflater.inflate(
				R.layout.aprobar_solicitud_oferta_laboral, container, false);
		this.listaSolicitudes = (ListView) rootView
				.findViewById(R.id.reclut_lista_solicit_of_laboral);
		this.aceptarButton = (Button) this.rootView
				.findViewById(R.id.reclu_btn_Validar);
		this.rechazarButton = (Button) this.rootView
				.findViewById(R.id.reclu_btn_Rechazar);

		// Llamamos al WS que poblará "solicitudes"
		// llamarServiciosAprobarSolicitudOfertaLaboral("laboral");
		probarDeserializacionJSON("");

		if (solicitudes != null) {
			this.solicitudesAdapter = new ArrayAdapter<SolicitudOfertaLaboral>(
					this.getActivity(), android.R.layout.simple_list_item_1,
					solicitudes);
			listaSolicitudes.setAdapter(solicitudesAdapter);
			listaSolicitudes
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View childView, int position, long id) {
							// position tiene la posicion de la vista en el
							// adapter
							mostrarSolicitudSeleccionada(solicitudes
									.get(position));
							//obtenemos el id de la solicitud seleccionada IDSolicitudSeleccionada = solicitudes.get(position).getSolicitudID();
							
							IDSolicitudSeleccionada = solicitudes.get(position)
									.getSolicitudID();
							posicionLista = position;
						}
					});

			aceptarButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (posicionLista != -1) {
						solicitudes.remove(posicionLista);
						solicitudesAdapter.notifyDataSetChanged();
						// comunicarle al ws que se acepto solicitud oferta
						// laboral

						EditText observacion = (EditText) rootView
								.findViewById(R.id.reclut_observacion_input);
						// enviarMensajeWS(aprobada, IDSolicitudSeleccionada,
						// observacion);
						posicionLista = -1; // volvemos a colocar el boton en -1
						SolicitudOfertaLaboral nueva = new SolicitudOfertaLaboral();
						mostrarSolicitudSeleccionada(nueva);
					}
				}
			});

			rechazarButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (posicionLista != -1) {
						solicitudes.remove(posicionLista);
						solicitudesAdapter.notifyDataSetChanged();
						// comunicarle al ws que se rechazo solicitud oferta
						// laboral

						EditText observacion = (EditText) rootView
								.findViewById(R.id.reclut_observacion_input);
						// enviarMensajeWS(rechazada, IDSolicitudSeleccionada,
						// observacion);
						posicionLista = -1; // volvemos a colocar el boton en -1
						SolicitudOfertaLaboral nueva = new SolicitudOfertaLaboral();
						mostrarSolicitudSeleccionada(nueva);
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
				JSONArray listaSolicitudes = (JSONArray) jsonObject
						.get("solicitudes");

				JSONObject solicitudObject;
				solicitudes = new ArrayList<SolicitudOfertaLaboral>();
				for (int i = 0; i < listaSolicitudes.length(); i++) {
					solicitudObject = listaSolicitudes.getJSONObject(i);

					solicitud = new SolicitudOfertaLaboral(
							solicitudObject.getInt("solicitudID"),
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

	/*
	 * private void poblarSolicitudes() { // TODO cvasquez: pinta los
	 * solicitudes en los controles // correspondientes
	 * 
	 * // Colaborador colaborador1 = new Colaborador("César", "Vásquez Flores",
	 * // "Tecnología", "Gerente"); Date fecha1 = new Date(2012, 3, 7); Date
	 * fecha2 = new Date(1990, 11, 18); Date fecha3 = new Date(1986, 5, 17);
	 * Date fecha4 = new Date(2011, 10, 25); Date fecha5 = new Date(1982, 7,
	 * 11); Date fecha6 = new Date(2013, 3, 4); Calendar date = new
	 * GregorianCalendar(); date.setTime(fecha1); Colaborador colaborador1 = new
	 * Colaborador("César", "Vásquez Flores", "Tecnología", "Gerente", fecha1,
	 * fecha2, "cesarvf90@gmail.com", "945872121"); Colaborador colaborador2 =
	 * new Colaborador("Yordy", "Reyna Serna", "Ventas", "Analista", fecha4,
	 * fecha3, "yreyna@gmail.com", "998547124"); Colaborador colaborador3 = new
	 * Colaborador("Claudia", "Montero Reyes", "Logística", "Jefe de Logística",
	 * fecha6, fecha5, "claudia.montero@gmail.com", "958411142"); for (int i =
	 * 0; i < 7; ++i) { solicitudes.add(colaborador1);
	 * solicitudes.add(colaborador2); solicitudes.add(colaborador3); } }
	 */

	protected void mostrarSolicitudSeleccionada(
			SolicitudOfertaLaboral solicitudOfertaLaboral) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView area = (TextView) rootView
				.findViewById(R.id.reclut_area_input);
		area.setText(solicitudOfertaLaboral.getArea());

		TextView cargo = (TextView) rootView
				.findViewById(R.id.reclut_cargo_input);
		cargo.setText(solicitudOfertaLaboral.getCargo());

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
		modoPublicacion.setText(solicitudOfertaLaboral.getModoPublicacion());

		TextView responsable = (TextView) rootView
				.findViewById(R.id.reclut_responsable_input);
		responsable.setText(solicitudOfertaLaboral.getResponsable());

		TextView comentarios = (TextView) rootView
				.findViewById(R.id.reclut_comentarios_input);
		if ((solicitudOfertaLaboral.getComentarios() == null)
				|| ((solicitudOfertaLaboral.getComentarios() == "null"))) {
			comentarios.setText("");
		} else {
			comentarios.setText(solicitudOfertaLaboral.getComentarios());
		}	

		EditText observacion = (EditText) rootView
				.findViewById(R.id.reclut_observacion_input);
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
