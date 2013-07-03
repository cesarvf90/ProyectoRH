package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.*;
import pe.edu.pucp.proyectorh.objetivos.MisObjetivos.ListadoObjetivosChild;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.ConstanteServicio;
import pe.edu.pucp.proyectorh.services.Servicio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;


public class Evaluar extends Fragment {
	View rootView;
	Context contexto;
	FragmentActivity actv;
	
	ExpandableListView listaProcesos;
	
	public Evaluados360 evaluado;
	
	private int numPagina;
	private int totalPaginas;
	int modoPrueba=1;
	
	LinearLayout lay;
	LinearLayout lay2;
	
	public Evaluar(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public class ListadoPreguntas extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("RecibidoPreg: " + result.toString());
				ArrayList<Pregunta360> listPregs = Pregunta360.getPreguntasByResult(result);		
				loadData(listPregs);
			}catch(Exception e){
				System.out.println("se cayo en ListadoPreguntas e="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}

	public class ListadoRespuestas extends AsyncCall {
		@Override
		protected void onPostExecute(String result) {
			try{
				System.out.println("RecibidoResp: " + result.toString());
				JSONObject jsonObject = new JSONObject(result);
				String respuesta = jsonObject.getString("success");
				if (ConstanteServicio.SERVICIO_OK.equals(respuesta)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(actv);
					builder.setTitle("Evaluación 360");
					builder.setMessage("Se guardaron todas las respuestas.");
					builder.setCancelable(false);
					builder.setPositiveButton("Ok", null);
					builder.create();
					builder.show();
					FragmentTransaction ft = actv.getSupportFragmentManager().beginTransaction();
					RolEvaluador fragment = new RolEvaluador();
					ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
					ft.replace(R.id.opcion_detail_container,fragment, "detailFragment").addToBackStack("tag").commit();

				}else{
					Servicio.mostrarErrorComunicacion("Error: No se pudo guardar las respuestas.",actv);
				}
			}catch(Exception e){
				System.out.println("se cayo en ListadoRespuestas, e="+e.toString());
				Servicio.mostrarErrorComunicacion(e.toString(),actv);
			}
		}
	}
	
	
	public void obtenerPuntajes(){
		ArrayList<Respuesta360> respuestas = new ArrayList<Respuesta360>();
		for( int i = 0; i < lay2.getChildCount(); i++ ){
			  if( lay2.getChildAt(i) instanceof RatingEvaluar ){
				  RatingEvaluar estrellitas = (RatingEvaluar)lay2.getChildAt(i);
				  System.out.println("encontro lay de preg="+estrellitas.idPregunta+ " con punt="+estrellitas.getRating());
				  Respuesta360 rpta = new Respuesta360();
				  rpta.PreguntaID = estrellitas.idPregunta;
				  rpta.Puntaje = (int)estrellitas.getRating();
				  respuestas.add(rpta);
			  }
		}
		Gson geson= new Gson();
		System.out.println("rpta="+geson.toJson(respuestas));
		
		ListadoRespuestas lo = new ListadoRespuestas();
    	String rutaLlamada = Servicio.EnviarRespuestas+"?respuestas="+geson.toJson(respuestas)+"&tablaEvaluadorID="+evaluado.ID; 
    	System.out.println("Ruta-Hijos="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI
	}
	
	class RatingEvaluar extends RatingBar{
		
		int idPregunta;

		public RatingEvaluar(Context context) {
			super(context);
			this.setNumStars(5);
			this.setStepSize(1);
			
			// TODO Auto-generated constructor stub
		}
		
	}
	public void loadData(ArrayList<Pregunta360> listPregs){
		
		lay2 = new LinearLayout(contexto);
		lay2.setOrientation(LinearLayout.VERTICAL);
		
		for (int i=0;i<listPregs.size();i++){
			System.out.println("Entra estrellitas con i="+i);
    		
			TextView txt = new TextView(contexto);
    		txt.setText(listPregs.get(i).TextoPregunta);
    		txt.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    		
    		lay2.addView(txt); 
    		
    		RatingEvaluar estrellitas = new RatingEvaluar(contexto);
    		estrellitas.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    		
    		estrellitas.idPregunta= listPregs.get(i).ID;
    		lay2.addView(estrellitas);
    	}
		lay2.setLongClickable(true);
		lay.addView(lay2);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView  = inflater.inflate(R.layout.evaluar,container, false);
		actv = getActivity();
		contexto = rootView.getContext();
		rootView.findViewById(R.layout.evaluar);
		
		Resources res = getResources();
		
		/*
		 * CODIGO PARA MANEJO DE PERIODO (SPINNER)
		 */
		QuickContactBadge imagen = (QuickContactBadge) rootView.findViewById(R.id.evaluacionImagenContacto);
		TextView nombre = (TextView)rootView.findViewById(R.id.evaluacion360Nombre);
		TextView area = (TextView)rootView.findViewById(R.id.evaluacion360Area);
		TextView puesto = (TextView)rootView.findViewById(R.id.evaluacion360Puesto);
		
		nombre.setText(evaluado.evaluado.NombreCompleto);
		area.setText(evaluado.evaluado.Area);
		puesto.setText(evaluado.evaluado.Puesto);
		
		lay = (LinearLayout)rootView.findViewById(R.id.layEvaluacion);
		lay.setLongClickable(true);

		ListadoPreguntas lo = new ListadoPreguntas();
    	String rutaLlamada = Servicio.ListarPreguntas+"?idEvaluador="+LoginActivity.getUsuario().getID()+"&idProcesoEvaluacion="+evaluado.ProcesoEnElQueParticipanID+"&idColaboradorEvaluado="+evaluado.evaluado.ID; 
    	System.out.println("Ruta-Hijos="+rutaLlamada);
		Servicio.llamadaServicio(this.getActivity(), lo,rutaLlamada); //SE LLAMA A VER MIS OBJETIVOS DEFINIDOS PARA MI

		Button guardarCambios = (Button) rootView.findViewById(R.id.finalizarEva360);
	 	guardarCambios.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  System.out.println("finaliza cambios");
				  //VALIDAR TODOS LOS OBJETIVOS
				  if(validaLlenado()){
					  obtenerPuntajes();
				  }
			  }
		});
		
		return rootView;
	}
	
	public boolean validaLlenado(){
		boolean valida = true;
		for( int i = 0; i < lay2.getChildCount(); i++ ){
			  if( lay2.getChildAt(i) instanceof RatingEvaluar ){
				  RatingEvaluar estrellitas = (RatingEvaluar)lay2.getChildAt(i);
				  if(estrellitas.getRating()==0){
					  valida = false;
				  }
			  }
		}
		
		System.out.println("valida="+valida);
		if(valida){
			return true;
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Error en validación");
			builder.setMessage("Debe llenar todas las preguntas.");
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			builder.create();
			builder.show();
	  }
		return false;
	}

}
