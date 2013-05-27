package pe.edu.pucp.proyectorh.reclutamiento;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.Evaluacion;
import pe.edu.pucp.proyectorh.model.Funcion;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.Respuesta;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmacionEvaluacion extends Fragment {

	private View rootView;
	private Postulante postulante;
	private OfertaLaboral oferta;
	private ArrayList<Funcion> funciones;
	private ArrayList<Respuesta> respuestas;
	private Evaluacion evaluacion;

	public ConfirmacionEvaluacion(OfertaLaboral oferta, Postulante postulante,
			ArrayList<Funcion> funciones, ArrayList<Respuesta> respuestas,
			Evaluacion evaluacion) {
		this.oferta = oferta;
		this.postulante = postulante;
		this.funciones = funciones;
		this.respuestas = respuestas;
		this.evaluacion = evaluacion;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.evaluacion_confirmacion,
				container, false);
		activarBotonRegistrarEvaluacion();
		return rootView;
	}

	private void activarBotonRegistrarEvaluacion() {
		TextView tituloPuestoText = (TextView) rootView
				.findViewById(R.id.puesto_title);
		tituloPuestoText.setText("Puesto: " + oferta.getPuesto().getNombre());
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.postulante_title);
		tituloPostulanteText.setText("Postulante: " + postulante.toString());

		Button botonRegistrarEvaluacion = (Button) rootView
				.findViewById(R.id.finalizarEvaluacion);
		botonRegistrarEvaluacion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Registrar evaluación");
				builder.setMessage("¿Desea registrar la evaluación?");
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
								llamarServicioEnviarRespuestas();
								dialog.cancel();
							}

						});
				builder.create();
				builder.show();

			}
		});
	}

	private void llamarServicioEnviarRespuestas() {
		EditText comentariosText = (EditText) rootView
				.findViewById(R.id.editTextComentarios);
		evaluacion.setComentarios(comentariosText.getText().toString());
		EditText observacionesText = (EditText) rootView
				.findViewById(R.id.editTextObservaciones);
		evaluacion.setObservaciones(observacionesText.getText().toString());
		if (ConnectionManager.connect(getActivity())) {
			String request = Servicio.RegistrarRespuestasEvaluacionTerceraFase;
			new RegistroEvalaucion().execute(request);
		} else {
			ErrorServicio.mostrarErrorConexion(getActivity());
		}
	}

	public class RegistroEvalaucion extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			System.out.println("Recibido: " + result.toString());
			try {
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (procesaRespuesta(respuesta)) {
					JSONObject datosObject = (JSONObject) jsonObject
							.get("data");
					mostrarConfirmacion();
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

	private void mostrarConfirmacion() {

	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pudo registrar las respuestas de la evaluación. Intente nuevamente");
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
