package pe.edu.pucp.proyectorh.miinformacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.connection.DataBaseContactosConnection;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

/**
 * Funcionalidad para que el usuario consulte sus contactos
 * 
 */
public class ContactosFragment extends Fragment {

	private View rootView;
	private ArrayList<Colaborador> contactos;
	private DataBaseContactosConnection dbConnection;

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
		if (LoginActivity.getUsuario().isSeTrajoContactos()) {
			obtenerContactosBDInterna();
		} else {
			llamarServicioContactos();
		}
		customizarEstilos(getActivity(), rootView);
		return rootView;
	}

	private void obtenerContactosBDInterna() {
		createBBDD();
		dbConnection.openDataBase();
		contactos = dbConnection.obtenerContactos();
		dbConnection.close();
		mostrarContactos();
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
						context.getAssets(), EstiloApp.FORMATO_LETRA_APP));
			}
		} catch (Exception e) {
		}
	}

	private void llamarServicioContactos() {
		obtenerContactos(LoginActivity.getUsuario());
	}

	/**
	 * Se crea la base de datos en las carpetas de la aplicacion
	 */
	public void createBBDD() {
		dbConnection = new DataBaseContactosConnection(this.getActivity());
		try {
			dbConnection.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
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
			new ObtencionContactos(this.getActivity()).execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	protected void mostrarContactoSeleccionado(Colaborador colaborador) {
		mostrarTexto(R.id.detalleContacto_title, colaborador.toString());
		mostrarTexto(R.id.miinfo_contactos_nombre, colaborador.getNombres());
		mostrarTexto(R.id.miinfo_contactos_apellidos,
				colaborador.getApellidos());
		mostrarTexto(R.id.miinfo_contactos_area, colaborador.getArea());
		mostrarTexto(R.id.miinfo_contactos_puesto, colaborador.getPuesto());
		mostrarTexto(R.id.miinfo_contactos_fecnacimiento,
				colaborador.getFechaNacimiento());
		mostrarTexto(R.id.miinfo_contactos_fecingreso,
				colaborador.getFechaIngreso());
		mostrarTexto(R.id.miinfo_contactos_correo,
				colaborador.getCorreoElectronico());
		mostrarTexto(R.id.miinfo_contactos_telefono, colaborador.getTelefono());
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
		public ObtencionContactos(Activity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
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
						contacto.setPuesto(contactoObject.getString("Puesto"));
						contacto.setTelefono(contactoObject
								.getString("Telefono"));
						contacto.setDireccion(contactoObject
								.getString("Direccion"));
						contacto.setPaisID(contactoObject.getString("PaisID"));
						contacto.setFechaNacimiento(contactoObject
								.getString("FechaNacimiento"));
						contacto.setFechaIngreso(contactoObject
								.getString("FechaIngreso"));
						contacto.setCentroEstudios(contactoObject
								.getString("CentroEstudios"));
						contacto.setNumeroDocumento(contactoObject
								.getString("NumeroDocumento"));
						contacto.setCorreoElectronico(contactoObject
								.getString("CorreoElectronico"));
						contactos.add(contacto);
					}
					LoginActivity.getUsuario().setSeTrajoContactos(true);
					mostrarContactos();
					ocultarMensajeProgreso();
					persistirContactosBDInterna();
				}
			} catch (JSONException e) {
				ocultarMensajeProgreso();
				ErrorServicio.mostrarErrorComunicacion(e.toString(),
						getActivity());
			} catch (NullPointerException ex) {
				ocultarMensajeProgreso();
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

	public void persistirContactosBDInterna() {
		createBBDD();
		dbConnection.openDataBase();
		dbConnection.insertarContactos(contactos);
		dbConnection.close();
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
				contactos) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView,
						parent);
				view.setTypeface(Typeface.createFromAsset(getActivity()
						.getAssets(), EstiloApp.FORMATO_LETRA_APP));
				return view;
			}
		};
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
		if ((texto != null) && (!Constante.CADENA_VACIA.equals(texto))
				&& (!Constante.NULL.equals(texto))) {
			textView.setText(texto);
		} else {
			textView.setText(Constante.ESPACIO_VACIO);
		}
	}

}
