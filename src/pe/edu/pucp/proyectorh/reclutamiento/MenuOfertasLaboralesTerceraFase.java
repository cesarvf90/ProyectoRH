package pe.edu.pucp.proyectorh.reclutamiento;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.LoginActivity;
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
import android.support.v4.app.FragmentTransaction;
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
		obtenerOfertasPendientes(LoginActivity.getUsuario());
	}

	private void obtenerOfertasPendientes(Usuario usuario) {
		if (ConnectionManager.connect(getActivity())) {
			// TODO cvasquez: enviar id del usuario para filtrar sus ofertas
			String request = Servicio.OfertasLaboralesTerceraFase
					+ "?descripcionFase=" + "Aprobado%20Jefe";
			new ObtencionOfertas().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	public class ObtencionOfertas extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				ofertas = new ArrayList<OfertaLaboral>();
				postulantes = new ArrayList<ArrayList<ArrayList<Postulante>>>();
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					JSONArray ofertasListObject = (JSONArray) datosObject
							.get("ofertasLaborales");
					ofertas = new ArrayList<OfertaLaboral>();
					for (int i = 0; i < ofertasListObject.length(); ++i) {
						JSONObject ofertaObject = ofertasListObject
								.getJSONObject(i);
						OfertaLaboral oferta = new OfertaLaboral();
						oferta.setID(ofertaObject.getString("ID"));
						oferta.setPuesto(new Puesto(new Area(ofertaObject
								.getString("Area")), ofertaObject
								.getString("Puesto")));
						oferta.setSolicitante(ofertaObject
								.getString("Responsable"));
						oferta.setFechaRequerimiento(ofertaObject
								.getString("FechaRequerimiento"));
						oferta.setNumeroPostulantes(ofertaObject
								.getInt("NumeroPostulantes"));
						JSONArray postulantesListObject = ofertaObject
								.getJSONArray("Postulantes");

						postulantes.add(new ArrayList<ArrayList<Postulante>>());
						ArrayList<Postulante> listaPostulantes = new ArrayList<Postulante>();

						for (int j = 0; j < postulantesListObject.length(); ++j) {
							JSONObject postulanteObject = postulantesListObject
									.getJSONObject(j);
							Postulante postulante = new Postulante();
							postulante.setNombres(postulanteObject
									.getString("Nombres"));
							postulante.setApellidos(postulanteObject
									.getString("ApellidoPaterno")
									+ Constante.ESPACIO_VACIO
									+ postulanteObject
											.getString("ApellidoMaterno"));
							postulante.setID(postulanteObject.getString("ID"));
							postulante.setCentroEstudios(postulanteObject
									.getString("CentroEstudios"));
							postulante.setCorreoElectronico(postulanteObject
									.getString("CorreoElectronico"));
							postulante.setGradoAcademico(postulanteObject
									.getString("GradoAcademico"));
							postulante.setTipoDocumento(postulanteObject
									.getString("TipoDocumento"));
							postulante.setNumeroDocumento(postulanteObject
									.getString("NumeroDocumento"));

							postulantes.get(i).add(new ArrayList<Postulante>());
							postulantes.get(i).get(j).add(postulante);
							listaPostulantes.add(postulante);
						}
						oferta.setPostulantes(listaPostulantes);
						ofertas.add(oferta);
					}
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
	 * Muestra las ofertas y postulantes obtenidos por el servicio
	 */
	public void mostrarOfertas() {
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
				} else if (groupPosition == ofertaSeleccionadaPosicion) {
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
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				int tipoItem = ExpandableListView.getPackedPositionType(id);
				if (tipoItem == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
					final int childPosition = ExpandableListView
							.getPackedPositionChild(id);
					final int groupPosition = ExpandableListView
							.getPackedPositionGroup(id);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Evaluar postulante");
					builder.setMessage("�Desea realizar la evaluaci�n de entrevista final para este postulante?");
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
									Postulante postulante = ofertasList
											.get(groupPosition)
											.getPostulantes()
											.get(childPosition);
									OfertaLaboral oferta = ofertasList
											.get(groupPosition);
									FragmentTransaction ft = getActivity()
											.getSupportFragmentManager()
											.beginTransaction();
									EvaluacionPostulante fragment = new EvaluacionPostulante(
											oferta, postulante);
									ft.setCustomAnimations(
											android.R.anim.slide_in_left,
											android.R.anim.slide_out_right);
									ft.replace(R.id.opcion_detail_container,
											fragment, "detailFragment")
											.addToBackStack("tag").commit();
									// getActivity()
									// .getSupportFragmentManager()
									// .beginTransaction()
									// .replace(
									// R.id.opcion_detail_container,
									// fragment).commit();
								}
							});
					builder.create();
					builder.show();
					return false;
				} else {
					return false;
				}
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
		// SimpleDateFormat formatoFecha = new SimpleDateFormat();
		// formatoFecha.applyPattern("dd/MM/yyyy");

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
		// fechaSolicitudText.setText(formatoFecha.format(oferta
		// .getFechaRequerimiento()));
		fechaSolicitudText.setText(oferta.getFechaRequerimiento());
		TextView faseActualText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_faseactual);
		faseActualText.setText(oferta.getFaseActual());
		TextView numeroPostulantesText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_numeropostulantes);
		numeroPostulantesText.setText(String.valueOf(oferta.getPostulantes()
				.size()));
		TextView fechaUltimaEntrevistaText = (TextView) rootView
				.findViewById(R.id.rec_ofertas_fecultentrevista);
		// fechaUltimaEntrevistaText.setText(formatoFecha.format(oferta
		// .getFechaUltimaEntrevista()));
		fechaUltimaEntrevistaText.setText(oferta.getFechaUltimaEntrevista());
	}

	/**
	 * Se muestra en blanco todos los campos del postulante
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
			builder.setMessage("No se pueden obtener los ofertas laborales para evaluaci�n. Intente nuevamente");
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