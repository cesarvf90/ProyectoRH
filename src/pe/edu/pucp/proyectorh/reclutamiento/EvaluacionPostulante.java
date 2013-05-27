package pe.edu.pucp.proyectorh.reclutamiento;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Funcion;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
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

public class EvaluacionPostulante extends Fragment {

	private View rootView;
	private Postulante postulante;
	private OfertaLaboral oferta;
	private ArrayList<Funcion> funciones;

	public EvaluacionPostulante(OfertaLaboral oferta,
			Postulante postulante) {
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

		mostrarEvaluacion();

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
		return rootView;
	}

	private void mostrarEvaluacion() {
		TextView tituloPuestoText = (TextView) rootView
				.findViewById(R.id.puesto_title);
		tituloPuestoText.setText("Puesto: " + oferta.getPuesto().getNombre());
		TextView tituloPostulanteText = (TextView) rootView
				.findViewById(R.id.postulante_title);
		tituloPostulanteText.setText("Postulante: " + postulante.toString());
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

	private boolean seEvaluo(RatingBar ratingBar) {
		return ratingBar.getRating() == 0 ? false : true;
	}
}
