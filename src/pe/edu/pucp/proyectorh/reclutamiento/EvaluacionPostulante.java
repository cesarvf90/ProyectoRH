package pe.edu.pucp.proyectorh.reclutamiento;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Funcion;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

@SuppressLint({ "ValidFragment", "ValidFragment" })
public class EvaluacionPostulante extends Fragment {

	private View rootView;
	private Postulante postulante;
	private OfertaLaboral oferta;
	private ArrayList<Funcion> funciones;

	public EvaluacionPostulante(OfertaLaboral oferta, Postulante postulante) {
		this.oferta = oferta;
		this.postulante = postulante;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.rendir_evaluaciones, container,
				false);
		llamarServicioObtenerEvaluacion();
		activarBotonFinalizar();
		return rootView;
	}

	private void activarBotonFinalizar() {
		Button botonFinalizar = (Button) rootView.findViewById(R.id.finalizar);
		botonFinalizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Finalizar evaluación");
				builder.setMessage("¿Desea finalizar la evaluación con los resultados registrados?");
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
								// recogerResultados();
								dialog.cancel();
							}

						});
				builder.create();
				builder.show();

			}
		});
	}

	private void llamarServicioObtenerEvaluacion() {
		obtenerEvaluacionPostulante();
	}

	private void mostrarEvaluacion() {
		TextView tituloPuestoText = (TextView) rootView
				.findViewById(R.id.puesto_title);
		tituloPuestoText.setText("Puesto: " + oferta.getPuesto().getNombre());
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.postulante_title);
		tituloPostulanteText.setText("Postulante: " + postulante.toString());

		TextView pregunta1Text = (TextView) rootView
				.findViewById(R.id.pregunta1);
		pregunta1Text.setText(funciones.get(0).getDescripcion());
		TextView pregunta2Text = (TextView) rootView
				.findViewById(R.id.pregunta2);
		pregunta2Text.setText(funciones.get(1).getDescripcion());
		TextView pregunta3Text = (TextView) rootView
				.findViewById(R.id.pregunta3);
		pregunta3Text.setText(funciones.get(2).getDescripcion());
	}

	private void recogerResultados() {
		RatingBar ratingPregunta1 = (RatingBar) rootView
				.findViewById(R.id.ratingPregunta1);
		RatingBar ratingPregunta2 = (RatingBar) rootView
				.findViewById(R.id.ratingPregunta2);
		RatingBar ratingPregunta3 = (RatingBar) rootView
				.findViewById(R.id.ratingPregunta3);
		if (seEvaluo(ratingPregunta1) && seEvaluo(ratingPregunta2)
				&& seEvaluo(ratingPregunta3)) {
			
		}
	}

	private void obtenerEvaluacionPostulante() {
		if (ConnectionManager.connect(getActivity())) {
			String request = Servicio.ObtenerEvaluacionTerceraFase
					+ "?idOfertaLaboral=" + oferta.getID();
			new ObtencionEvaluacion().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
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
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					JSONArray funcionesListObject = (JSONArray) datosObject
							.get("funciones");
					funciones = new ArrayList<Funcion>();
					for (int i = 0; i < funcionesListObject.length(); ++i) {
						JSONObject funcionObject = funcionesListObject
								.getJSONObject(i);
						Funcion funcion = new Funcion();
						funcion.setID(funcionObject.getString("ID"));
						funcion.setDescripcion(funcionObject
								.getString("Descripcion"));
						funcion.setPuestoID(funcionObject.getString("PuestoID"));
						funciones.add(funcion);
					}
					mostrarEvaluacion();
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

	private boolean seEvaluo(RatingBar ratingBar) {
		return ratingBar.getRating() > 0 ? false : true;
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pudo obtener la evaluación. Intente nuevamente");
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
