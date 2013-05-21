package pe.edu.pucp.proyectorh.reclutamiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Area;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.Puesto;
import pe.edu.pucp.proyectorh.model.Usuario;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.Constante;
import pe.edu.pucp.proyectorh.utils.OfertasAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MenuOfertasLaboralesTerceraFase extends Fragment {

	private View rootView;
	private ArrayList<OfertaLaboral> ofertas;
	private ArrayList<ArrayList<ArrayList<Postulante>>> postulantes;

	// Ayudan a conocer que oferta o postulante se esta mostrando
	private int ofertaSeleccionadaPosicion = -1;
	private int postulanteSeleccionadoPosicion = -1;

	public MenuOfertasLaboralesTerceraFase() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.menu_ofertas_tercera_fase,
				container, false);
		llamarServicioOfertasLaboralesTerceraFase();
		return rootView;
	}

	private void llamarServicioOfertasLaboralesTerceraFase() {
		// TODO cvasquez: coordinar formato del servicio e implementar su
		// llamada
		// obtenerOfertasPendientes(LoginActivity.getUsuario());
		llamarServicioOfertasLaboralesTerceraFaseMock();
	}

	private void obtenerOfertasPendientes(Usuario usuario) {
		if (ConnectionManager.connect(getActivity())) {
			String request = Servicio.ofertasLaboralesTerceraFase + "?id="
					+ usuario.getID();
			new ObtencionOfertas().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	private void obtenerEvaluacionPostulante(String ofertaID,
			String postulanteID) {
		if (ConnectionManager.connect(getActivity())) {
			// construir llamada al servicio
			String request = Servicio.obtenerEvaluacionTerceraFase
					+ "?ofertaId=" + ofertaID + ",postulanteID=" + postulanteID;
			new ObtencionEvaluacion().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	public class ObtencionOfertas extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					mostrarOfertas();
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

	public class ObtencionEvaluacion extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					mostrarOfertas();
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

	/**
	 * Genera data mock y la muestra
	 */
	private void llamarServicioOfertasLaboralesTerceraFaseMock() {
		// genera fechas
		Date fecha1 = new Date(2012, 3, 7);
		Date fecha2 = new Date(1990, 11, 18);
		Date fecha3 = new Date(1986, 5, 17);
		Date fecha4 = new Date(2011, 10, 25);
		Date fecha5 = new Date(1982, 7, 11);
		Date fecha6 = new Date(2013, 3, 4);

		ArrayList<Postulante> postulantes1 = new ArrayList<Postulante>();
		Postulante postulante1 = new Postulante("José", "Castillo");
		Postulante postulante2 = new Postulante("Bruce", "Dallas");
		Postulante postulante3 = new Postulante("Esteban", "Juarez");
		postulantes1.add(postulante1);
		postulantes1.add(postulante2);
		postulantes1.add(postulante3);

		ArrayList<Postulante> postulantes2 = new ArrayList<Postulante>();
		postulante1 = new Postulante("Karina", "Olivos");
		postulante2 = new Postulante("Verónica", "Lasalle");
		postulante3 = new Postulante("Arturo", "Díaz");
		postulantes2.add(postulante1);
		postulantes2.add(postulante2);
		postulantes2.add(postulante3);

		ArrayList<Postulante> postulantes3 = new ArrayList<Postulante>();
		postulante1 = new Postulante("Renzo", "Piaggio");
		postulante2 = new Postulante("Carla", "Solorzano");
		postulante3 = new Postulante("Angelica", "Goyzueta");
		postulantes3.add(postulante1);
		postulantes3.add(postulante2);
		postulantes3.add(postulante3);

		ArrayList<Postulante> postulantes4 = new ArrayList<Postulante>();
		postulante1 = new Postulante("José", "Feliciano");
		postulante2 = new Postulante("Elena", "Rodríguez");
		postulante3 = new Postulante("Alberto", "Alva");
		postulantes4.add(postulante1);
		postulantes4.add(postulante2);
		postulantes4.add(postulante3);

		// genera ofertas
		ofertas = new ArrayList<OfertaLaboral>();
		Puesto puesto1 = new Puesto(new Area("Tecnología"),
				"Ingeniero de Software");
		Puesto puesto2 = new Puesto(new Area("Ventas"), "Ejecutivo");
		Puesto puesto3 = new Puesto(new Area("Marketing"),
				"Analista de tendencias");
		Puesto puesto4 = new Puesto(new Area("RRHH"), "Practicante");
		OfertaLaboral oferta1 = new OfertaLaboral(puesto1, postulantes1,
				"César Legario", fecha1, fecha6, "Entrevista con jefe");
		OfertaLaboral oferta2 = new OfertaLaboral(puesto2, postulantes2,
				"Gustavo López", fecha2, fecha3, "Entrevista con jefe");
		OfertaLaboral oferta3 = new OfertaLaboral(puesto3, postulantes3,
				"Margarita Reyes", fecha5, fecha3, "Entrevista con jefe");
		OfertaLaboral oferta4 = new OfertaLaboral(puesto4, postulantes4,
				"Stephano Dalma", fecha4, fecha5, "Entrevista con jefe");
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta3);
		ofertas.add(oferta4);

		// genera postulantes
		postulantes = new ArrayList<ArrayList<ArrayList<Postulante>>>();
		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(0).add(postulantes1.get(0));
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(1).add(postulantes1.get(1));
		postulantes.get(0).add(new ArrayList<Postulante>());
		postulantes.get(0).get(2).add(postulantes1.get(2));

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(0).add(postulantes2.get(0));
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(1).add(postulantes2.get(1));
		postulantes.get(1).add(new ArrayList<Postulante>());
		postulantes.get(1).get(2).add(postulantes2.get(2));

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(0).add(postulantes3.get(0));
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(1).add(postulantes3.get(1));
		postulantes.get(2).add(new ArrayList<Postulante>());
		postulantes.get(2).get(2).add(postulantes3.get(2));

		postulantes.add(new ArrayList<ArrayList<Postulante>>());
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(0).add(postulantes4.get(0));
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(1).add(postulantes4.get(1));
		postulantes.get(3).add(new ArrayList<Postulante>());
		postulantes.get(3).get(2).add(postulantes4.get(2));
		mostrarOfertasLaborales(ofertas, postulantes);
	}

	public void mostrarOfertas() {
		// TODO cvasquez: implementar similar a mostrarOfertasLaborales

	}

	private void mostrarOfertasLaborales(ArrayList<OfertaLaboral> ofertas,
			ArrayList<ArrayList<ArrayList<Postulante>>> postulantes) {
		// TODO cvasquez: pinta las ofertas en los controles correspondientes
		// for (int i = 0; i < ofertas.size(); i++) {
		// ofertas.add(oferta);
		// postulantes.add(new ArrayList<ArrayList<Postulante>>());
		// for (int j = 0; j < oferta.getPostulantes().size(); ++j) {
		// postulantes.get(i).add(new ArrayList<Postulante>());
		// postulantes.get(i).get(j).add(oferta.getPostulantes().get(j));
		// }
		// }
		final ArrayList<OfertaLaboral> ofertasList = ofertas;
		ExpandableListView listaOfertas = (ExpandableListView) rootView
				.findViewById(R.id.evaluar_lista_ofertas);
		OfertasAdapter adapter = new OfertasAdapter(this.getActivity()
				.getApplicationContext(), ofertas, postulantes);
		listaOfertas.setAdapter(adapter);
		listaOfertas.setLongClickable(true);

		// Se muestra la informacion de la oferta
		listaOfertas.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				System.out.println("Grupo " + (groupPosition));
				if (groupPosition != ofertaSeleccionadaPosicion) {
					mostrarOfertaSeleccionada(ofertasList.get(groupPosition));
					mostrarPostulanteVacio();
					ofertaSeleccionadaPosicion = groupPosition;
				}
				return false;
			}
		});

		// Se muestra la informacion de el postulante
		listaOfertas.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (groupPosition != ofertaSeleccionadaPosicion) {
					// Si se selecciono un postulante de una oferta distinta a
					// la que se esta mostrando se refresca tambien el detalle
					// de la oferta
					mostrarOfertaSeleccionada(ofertasList.get(groupPosition));
					mostrarPostulanteSeleccionado(ofertasList
							.get(groupPosition).getPostulantes()
							.get(childPosition));
					postulanteSeleccionadoPosicion = childPosition;
					ofertaSeleccionadaPosicion = groupPosition;
				} else if ((childPosition != postulanteSeleccionadoPosicion)
						&& (groupPosition == ofertaSeleccionadaPosicion)) {
					// Si se selecciono un postulante de la misma oferta que se
					// esta mostrando solo se refresca el detalle del postulante
					mostrarPostulanteSeleccionado(ofertasList
							.get(groupPosition).getPostulantes()
							.get(childPosition));
					postulanteSeleccionadoPosicion = childPosition;
				}
				return false;
			}
		});

		// Se dirige a la evaluacion del postulante
		listaOfertas.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Evaluar postulante");
				builder.setMessage("¿Desea realizar la evaluación de entrevista final para este postulante?");
				builder.setCancelable(false);
				builder.setCancelable(false);
				builder.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				builder.setPositiveButton("Evaluar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								EvaluacionPostulanteFragment fragment = new EvaluacionPostulanteFragment();
								getActivity()
										.getSupportFragmentManager()
										.beginTransaction()
										.replace(R.id.opcion_detail_container,
												fragment).commit();
							}
						});
				builder.create();
				builder.show();
				return false;
			}
		});
	}

	protected void mostrarPostulanteSeleccionado(Postulante postulante) {
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.infopostulante_title);
		tituloPostulanteText.setText(postulante.toString());
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.rec_postulante_nombre);
		nombreText.setText(postulante.getNombres());
		TextView apellidosText = (TextView) rootView
				.findViewById(R.id.rec_postulante_apellidos);
		apellidosText.setText(postulante.getApellidos());
		TextView documentoIdentidadText = (TextView) rootView
				.findViewById(R.id.rec_postulante_docidentidad);
		documentoIdentidadText.setText(postulante.getTipoDocumento()
				+ postulante.getNumeroDocumento());
		TextView centroEstudiosText = (TextView) rootView
				.findViewById(R.id.rec_postulante_centroestudios);
		centroEstudiosText.setText(postulante.getCentroEstudios());
		TextView gradoAcademicoText = (TextView) rootView
				.findViewById(R.id.rec_postulante_gradoacademico);
		gradoAcademicoText.setText(postulante.getGradoAcademico());
	}

	protected void mostrarOfertaSeleccionada(OfertaLaboral oferta) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat();
		formatoFecha.applyPattern("dd/MM/yyyy");

		TextView tituloOfertaText = (TextView) rootView
				.findViewById(R.id.detalleofertas_title);
		tituloOfertaText.setText(oferta.toString());
		TextView puestoText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_puesto);
		puestoText.setText(oferta.getPuesto().getNombre());
		TextView areaText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_area);
		areaText.setText(oferta.getPuesto().getArea().getNombre());
		TextView solicitanteText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_solicitante);
		solicitanteText.setText(oferta.getSolicitante());
		TextView fechaSolicitudText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_fechasolicitud);
		fechaSolicitudText.setText(formatoFecha.format(oferta
				.getFechaRequerimiento()));
		TextView faseActualText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_faseactual);
		faseActualText.setText(oferta.getFaseActual());
		TextView numeroPostulantesText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_numeropostulantes);
		numeroPostulantesText.setText(String.valueOf(oferta.getPostulantes()
				.size()));
		TextView fechaUltimaEntrevistaText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_fecultentrevista);
		fechaUltimaEntrevistaText.setText(formatoFecha.format(oferta
				.getFechaUltimaEntrevista()));
	}

	/**
	 * Se muestra en blanco todos los campos del postulante
	 * 
	 * @param postulante
	 */
	protected void mostrarPostulanteVacio() {
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.infopostulante_title);
		tituloPostulanteText.setText(Constante.CADENA_VACIA);
		TextView nombreText = (TextView) rootView
				.findViewById(R.id.rec_postulante_nombre);
		nombreText.setText(Constante.CADENA_VACIA);
		TextView apellidosText = (TextView) rootView
				.findViewById(R.id.rec_postulante_apellidos);
		apellidosText.setText(Constante.CADENA_VACIA);
		TextView documentoIdentidadText = (TextView) rootView
				.findViewById(R.id.rec_postulante_docidentidad);
		documentoIdentidadText.setText(Constante.CADENA_VACIA);
		TextView centroEstudiosText = (TextView) rootView
				.findViewById(R.id.rec_postulante_centroestudios);
		centroEstudiosText.setText(Constante.CADENA_VACIA);
		TextView gradoAcademicoText = (TextView) rootView
				.findViewById(R.id.rec_postulante_gradoacademico);
		gradoAcademicoText.setText(Constante.CADENA_VACIA);
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pueden obtener los ofertas laborales para evaluación. Intente nuevamente");
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

}
