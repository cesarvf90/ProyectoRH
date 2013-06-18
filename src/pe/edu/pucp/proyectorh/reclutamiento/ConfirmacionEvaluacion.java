package pe.edu.pucp.proyectorh.reclutamiento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Evaluacion;
import pe.edu.pucp.proyectorh.model.Funcion;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.Respuesta;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.ErrorServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.EstiloApp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmacionEvaluacion extends Fragment {

	private View rootView;
	private Postulante postulante;
	private OfertaLaboral oferta;
	private ArrayList<Funcion> funciones;
	private ArrayList<Respuesta> respuestas;
	private Evaluacion evaluacion;
	private int puntajeTotal = 0;

	// private ProgressDialog progressDialog = null;

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
		customizarEstilos(getActivity(), rootView);
		return rootView;
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

	private void activarBotonRegistrarEvaluacion() {
		TextView tituloPuestoText = (TextView) rootView
				.findViewById(R.id.puesto_title);
		tituloPuestoText.setText("Puesto: " + oferta.getPuesto().getNombre());
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.postulante_title);
		tituloPostulanteText.setText("Postulante: " + postulante.toString());
		TextView tituloPuntajeText = (TextView) rootView
				.findViewById(R.id.puntaje_title);
		tituloPuntajeText.setText("Puntaje: " + obtenerPuntaje());
		TextView mensajeConfirmacionText = (TextView) rootView
				.findViewById(R.id.mensaje_confirmacion_evaluacion);
		mensajeConfirmacionText
				.setText("Complete los campos para registrar la evaluaci�n.");

		Button botonRegistrarEvaluacion = (Button) rootView
				.findViewById(R.id.finalizarEvaluacion);
		botonRegistrarEvaluacion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Registrar evaluaci�n");
				builder.setMessage("�Desea registrar la evaluaci�n?");
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
								new Thread(new Runnable() {
									@Override
									public void run() {
										llamarServicioEnviarRespuestas();
									}
								}).start();
								dialog.cancel();
							}

						});
				builder.create();
				builder.show();
			}
		});
	}

	private String obtenerPuntaje() {
		for (Respuesta respuesta : respuestas) {
			puntajeTotal += respuesta.getPuntaje();
		}
		return String.valueOf(puntajeTotal + "/" + 5 * respuestas.size());
	}

	private HttpResponse llamarServicioEnviarRespuestas() {
		// progressDialog = new ProgressDialog(getActivity());
		// progressDialog.setMessage("Registrando evaluaci�n...");
		// progressDialog.show();
		JSONObject registroEvaluacion = generaRegistroEvaluacionJSON();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				Servicio.RegistrarRespuestasEvaluacionTerceraFase);
		try {
			StringEntity stringEntity = new StringEntity(
					registroEvaluacion.toString());
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity resultentity = httpResponse.getEntity();
			InputStream inputstream = resultentity.getContent();
			Header contentencoding = httpResponse
					.getFirstHeader("Content-Encoding");
			if (contentencoding != null
					&& contentencoding.getValue().equalsIgnoreCase("gzip")) {
				inputstream = new GZIPInputStream(inputstream);
			}
			String resultstring = convertStreamToString(inputstream);
			System.out.println("Respuesta POST Recibido: "
					+ resultstring.toString());
			inputstream.close();
			resultstring = resultstring.substring(1, resultstring.length() - 1);
			JSONObject resultadoRegistroJSON = new JSONObject(resultstring);
			manejarRespuesta(resultadoRegistroJSON);
		} catch (ClientProtocolException e) {
			// progressDialog.dismiss();
			System.out.println("Excepcion al registrar " + e.toString());
		} catch (IOException e) {
			// progressDialog.dismiss();
			System.out.println("Excepcion al registrar " + e.toString());
		} catch (JSONException e) {
			// progressDialog.dismiss();
			System.out.println("Excepcion al registrar " + e.toString());
		}
		return null;
	}

	private void manejarRespuesta(JSONObject resultadoRegistroJSON) {
		try {
			String respuesta = resultadoRegistroJSON.getString("success");
			if (procesaRespuesta(respuesta)) {
				JSONObject datosObject = (JSONObject) resultadoRegistroJSON
						.get("data");
				JSONObject evaluacionObject = datosObject
						.getJSONObject("evaluacion");
				evaluacion.setID(evaluacionObject.getInt("ID"));
				mostrarConfirmacion();
			}
		} catch (JSONException e) {
			// progressDialog.dismiss();
			ErrorServicio.mostrarErrorComunicacion(e.toString(), getActivity());
		} catch (NullPointerException ex) {
			// progressDialog.dismiss();
			ErrorServicio
					.mostrarErrorComunicacion(ex.toString(), getActivity());
		}
	}

	private String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Stream Exception",
					Toast.LENGTH_SHORT).show();
		}
		return total.toString();
	}

	private JSONObject generaRegistroEvaluacionJSON() {
		EditText comentariosText = (EditText) rootView
				.findViewById(R.id.editTextComentarios);
		evaluacion.setComentarios(comentariosText.getText().toString());
		EditText observacionesText = (EditText) rootView
				.findViewById(R.id.editTextObservaciones);
		evaluacion.setObservaciones(observacionesText.getText().toString());
		JSONObject registroObject = new JSONObject();
		try {
			registroObject.put("idPostulante", postulante.getID());
			registroObject.put("idOfertaLaboral", oferta.getID());
			registroObject.put("descripcionFase", "Aprobado Jefe");
			JSONObject evaluacionObject = new JSONObject();
			evaluacionObject.put("FechaInicio", "02/06/2013 14:03:23");
			evaluacionObject.put("FechaFin", "02/06/2013 14:33:54");
			evaluacionObject.put("Comentarios", evaluacion.getComentarios());
			evaluacionObject
					.put("Observaciones", evaluacion.getObservaciones());
			evaluacionObject.put("Puntaje", puntajeTotal);
			registroObject.put("evaluacion", evaluacionObject);
			JSONArray respuestasListObject = new JSONArray();
			for (int i = 0; i < respuestas.size(); ++i) {
				JSONObject respuestaObject = new JSONObject();
				respuestaObject.put("Comentario", "");
				respuestaObject.put("Puntaje", respuestas.get(i).getPuntaje());
				respuestaObject.put("FuncionID", respuestas.get(i)
						.getFuncionID());
				respuestasListObject.put(respuestaObject);
			}
			registroObject.put("respuestas", respuestasListObject);
		} catch (JSONException e) {
			ErrorServicio.mostrarErrorComunicacion(e.toString(), getActivity());
		} catch (NullPointerException ex) {
			ErrorServicio
					.mostrarErrorComunicacion(ex.toString(), getActivity());
		}
		return registroObject;
	}

	private void mostrarConfirmacion() {
		TextView mensajeConfirmacionText = (TextView) rootView
				.findViewById(R.id.mensaje_confirmacion_evaluacion);
		mensajeConfirmacionText
				.setText("Se registr� la evaluaci�n realizada. El c�digo de la evaluaci�n es "
						+ evaluacion.getID());
	}

	public boolean procesaRespuesta(String respuestaServidor) {
		if (ConstanteServicio.SERVICIO_OK.equals(respuestaServidor)) {
			return true;
		} else if (ConstanteServicio.SERVICIO_ERROR.equals(respuestaServidor)) {
			// Se muestra mensaje de servicio no disponible
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Servicio no disponible");
			builder.setMessage("No se pudo registrar las respuestas de la evaluaci�n. Intente nuevamente");
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
