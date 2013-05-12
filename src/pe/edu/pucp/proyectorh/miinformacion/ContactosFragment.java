package pe.edu.pucp.proyectorh.miinformacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
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
		// TODO cvasquez: llamar al servicio que devuelve los contactos del
		// usuario

		mostrarContactos();
	}

	private void mostrarContactos() {
		// TODO cvasquez: pinta los contactos en los controles correspondientes
		ListView listaContactos = (ListView) rootView
				.findViewById(R.id.mi_info_lista_contactos);
		final ArrayList<Colaborador> contactos = new ArrayList<Colaborador>();
		// Colaborador colaborador1 = new Colaborador("César", "Vásquez Flores",
		// "Tecnología", "Gerente");
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
						contactos.remove(position);
						colaboradoresAdapter.notifyDataSetChanged();
					}

					// TODO cvasquez: implementar llamada o envio de correo
					// onItemLongClick
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
				.findViewById(R.id.reclut_area_input);
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

}
