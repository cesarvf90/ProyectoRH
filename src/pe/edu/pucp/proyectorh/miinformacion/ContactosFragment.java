package pe.edu.pucp.proyectorh.miinformacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
import pe.edu.pucp.proyectorh.services.ErrorServicio;
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
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	protected void mostrarContactoSeleccionado(Colaborador colaborador) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		// TextView tituloContactoText = (TextView) rootView
		// .findViewById(R.id.detalleContacto_title);
		// tituloContactoText.setText(colaborador.toString());
		mostrarTexto(R.id.detalleContacto_title, colaborador.toString());
		// TextView nombreText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_nombre);
		// nombreText.setText(colaborador.getNombres());
		mostrarTexto(R.id.miinfo_contactos_nombre, colaborador.getNombres());
		// TextView apellidosText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_apellidos);
		// apellidosText.setText(colaborador.getApellidos());
		mostrarTexto(R.id.miinfo_contactos_apellidos,
				colaborador.getApellidos());
		// TextView areaText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_area);
		// areaText.setText(colaborador.getArea());
		mostrarTexto(R.id.miinfo_contactos_area, colaborador.getArea());
		// TextView puestoText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_puesto);
		// puestoText.setText(colaborador.getPuesto());
		mostrarTexto(R.id.miinfo_contactos_puesto, colaborador.getPuesto());
		// TextView fechaNacimientoText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_fecnacimiento);
		// fechaNacimientoText.setText(formatoFecha.format(colaborador
		// .getFechaNacimiento().getTime()));
		mostrarTexto(R.id.miinfo_contactos_fecnacimiento,
				formatoFecha.format(colaborador.getFechaNacimiento().getTime()));
		// TextView fechaIngresoText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_fecingreso);
		// fechaIngresoText.setText(formatoFecha.format(colaborador
		// .getFechaIngreso().getTime()));
		mostrarTexto(R.id.miinfo_contactos_fecingreso,
				formatoFecha.format(colaborador.getFechaIngreso().getTime()));
		// TextView correoElectronicoText = (TextView) rootView
		// .findViewById(R.id.miinfo_contactos_correo);
		// correoElectronicoText.setText(colaborador.getCorreoElectronico());
		mostrarTexto(R.id.miinfo_contactos_correo,
				colaborador.getCorreoElectronico());
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
								+ Constante.ESPACIO_VACIO
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
				ErrorServicio.mostrarErrorComunicacion(e.toString(),
						getActivity());
			} catch (NullPointerException ex) {
				ErrorServicio.mostrarErrorComunicacion(ex.toString(),
						getActivity());
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

	private void mostrarTexto(int idTextView, String texto) {
		TextView textView = (TextView) rootView.findViewById(idTextView);
		if (texto != null) {
			textView.setText(texto);
		} else {
			textView.setText(Constante.ESPACIO_VACIO);
		}
	}

}
