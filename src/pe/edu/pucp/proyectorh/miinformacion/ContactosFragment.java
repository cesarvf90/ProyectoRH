package pe.edu.pucp.proyectorh.miinformacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactosFragment extends Fragment {

	private View rootView;
	private ArrayList<Colaborador> contactos;

	public ContactosFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mi_info_contactos, container,
				false);
		llamarServicioContactos();
		return rootView;
	}

	private void llamarServicioContactos() {
		// llama al servicio que devuelve los contactos del usuario
		obtenerContactos(LoginActivity.getUsuario());
	}

	/**
	 * Llama al servicio que obtiene los contactos del usuario
	 * 
	 * @param usuario
	 */
	private void obtenerContactos(Usuario usuario) {
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = Servicio.MisContactosService + "?id="
					+ usuario.getID();
			new ObtencionContactos().execute(request);
		} else {
			// Se muestra mensaje de error de conexion con el servicio
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Error de conexión");
			builder.setMessage(ConstanteServicio.MENSAJE_PROBLEMA_CONEXION);
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
		}
	}

	private void mostrarContactosMock() {
		ListView listaContactos = (ListView) rootView
				.findViewById(R.id.mi_info_lista_contactos);
		final ArrayList<Colaborador> contactos = new ArrayList<Colaborador>();
		Date fecha1 = new Date(2012, 3, 7);
		Date fecha2 = new Date(1990, 11, 18);
		Date fecha3 = new Date(1986, 5, 17);
		Date fecha4 = new Date(2011, 10, 25);
		Date fecha5 = new Date(1982, 7, 11);
		Date fecha6 = new Date(2013, 3, 4);
		Calendar date = new GregorianCalendar();
		date.setTime(fecha1);
		Colaborador colaborador1 = new Colaborador("César", "Vásquez Flores",
				"Tecnología", "Gerente", fecha1, fecha2, "cesarvf90@gmail.com",
				"945872121");
		Colaborador colaborador2 = new Colaborador("Yordy", "Reyna Serna",
				"Ventas", "Analista", fecha4, fecha3, "yreyna@gmail.com",
				"998547124");
		Colaborador colaborador3 = new Colaborador("Claudia", "Montero Reyes",
				"Logística", "Jefe de Logística", fecha6, fecha5,
				"claudia.montero@gmail.com", "958411142");
		for (int i = 0; i < 7; ++i) {
			contactos.add(colaborador1);
			contactos.add(colaborador2);
			contactos.add(colaborador3);
		}
		Collections.sort(contactos, new Comparator<Colaborador>() {

			@Override
			public int compare(Colaborador lhs, Colaborador rhs) {
				return lhs.toString().compareTo(rhs.toString());
			}
		});
		final ArrayAdapter<Colaborador> colaboradoresAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				contactos);
		listaContactos.setAdapter(colaboradoresAdapter);
		listaContactos
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el adapter
						mostrarContactoSeleccionado(contactos.get(position));
						// contactos.remove(position);
						colaboradoresAdapter.notifyDataSetChanged();
					}
				});
	}

	protected void mostrarContactoSeleccionado(Colaborador colaborador) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView tituloContactoText = (TextView) rootView
				.findViewById(R.id.detalleContacto_title);
		tituloContactoText.setText(colaborador.toString());
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_nombre);
		nombreText.setText(colaborador.getNombres());
		TextView apellidosText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_apellidos);
		apellidosText.setText(colaborador.getApellidos());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_area);
		areaText.setText(colaborador.getArea());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_puesto);
		puestoText.setText(colaborador.getPuesto());
		TextView fechaNacimientoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_fecnacimiento);
		fechaNacimientoText.setText(formatoFecha.format(colaborador
				.getFechaNacimiento().getTime()));
		TextView fechaIngresoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_fecingreso);
		fechaIngresoText.setText(formatoFecha.format(colaborador
				.getFechaIngreso().getTime()));
		TextView correoElectronicoText = (TextView) rootView
				.findViewById(R.id.miinfo_contactos_correo);
		correoElectronicoText.setText(colaborador.getCorreoElectronico());
	}

	private void llamarContacto(String telefono) {
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + telefono));
			startActivity(callIntent);
		} catch (ActivityNotFoundException e) {
			System.out.println("Falló la llamada " + e.toString());
		}
	}

	private void enviarEmail(String email) {
		/* Create the Intent */
		final Intent emailIntent = new Intent(
				android.content.Intent.ACTION_SEND);

		/* Fill it with Data */
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { "to@email.com" });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

		/* Send it off to the Activity-Chooser */
		getActivity().startActivity(
				Intent.createChooser(emailIntent, "Send mail..."));

	}

	public class ObtencionContactos extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			// deserializando el json parte por parte
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					JSONArray contactosListObject = (JSONArray) datosObject
							.get("contactos");
					contactos = new ArrayList<Colaborador>();
					for (int i = 0; i < contactosListObject.length(); ++i) {
						JSONObject contactoObject = contactosListObject
								.getJSONObject(i);
						Colaborador contacto = new Colaborador();
						contacto.setId(contactoObject.getString("ID"));
						contacto.setNombres(contactoObject.getString("Nombre"));
						contacto.setApellidos(contactoObject
								.getString("ApellidoPaterno")
								+ Constante.CADENA_VACIA
								+ contactoObject.getString("ApellidoMaterno"));
						contacto.setArea(contactoObject.getString("Area"));
						contacto.setAreaID(contactoObject.getString("AreaID"));
						contacto.setPuesto(contactoObject.getString("Puesto"));
						contacto.setPuestoID(contactoObject
								.getString("PuestoID"));
						contacto.setTelefono(contactoObject
								.getString("Telefono"));
						contacto.setDireccion(contactoObject
								.getString("Direccion"));
						contacto.setPaisID(contactoObject.getString("PaisID"));
						// TODO cvasquez: parsear de string a date
						// contacto.setFechaNacimiento(contactoObject.getString("FechaNacimiento"));
						contacto.setFechaNacimiento(new Date(2012, 3, 7));
						// contacto.setFechaIngreso(contactoObject
						// .getString("FechaIngreso"));
						contacto.setFechaIngreso(new Date(2012, 3, 7));
						contacto.setTipoDocumentoID(contactoObject
								.getString("TipoDocumentoID"));
						contacto.setCentroEstudios(contactoObject
								.getString("CentroEstudios"));
						contacto.setNumeroDocumento(contactoObject
								.getString("NumeroDocumento"));
						contacto.setCorreoElectronico(contactoObject
								.getString("CorreoElectronico"));

						contactos.add(contacto);
					}

					mostrarContactos();
				}
			} catch (JSONException e) {
				mostrarErrorComunicacion(e.toString());
			} catch (NullPointerException ex) {
				mostrarErrorComunicacion(ex.toString());
			}
		}
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pueden obtener los contactos. Intente nuevamente");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
			return false;
		} else {
			// Se muestra mensaje de la respuesta invalida del servidor
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Error de servicio");
		builder.setMessage(ConstanteServicio.MENSAJE_SERVICIO_NO_DISPONIBLE
				+ excepcion.toString());
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", null);
		builder.create();
		builder.show();
	}

	private void mostrarContactos() {
		ListView listaContactos = (ListView) rootView
				.findViewById(R.id.mi_info_lista_contactos);
		Collections.sort(contactos, new Comparator<Colaborador>() {

			@Override
			public int compare(Colaborador colaborador1,
					Colaborador colaborador2) {
				return colaborador1.toString().compareTo(
						colaborador2.toString());

			}
		});
		final ArrayAdapter<Colaborador> colaboradoresAdapter = new ArrayAdapter<Colaborador>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				contactos);
		listaContactos.setAdapter(colaboradoresAdapter);
		listaContactos
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View childView, int position, long id) {
						// position tiene la posicion de la vista en el adapter
						mostrarContactoSeleccionado(contactos.get(position));
						colaboradoresAdapter.notifyDataSetChanged();
					}

					// TODO cvasquez: implementar llamada o envio de correo
					// onItemLongClick
				});
	}

}
