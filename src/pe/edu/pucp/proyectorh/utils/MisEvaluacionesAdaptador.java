package pe.edu.pucp.proyectorh.utils;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.model.Competencia;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.model.ProcesoXCompetencias;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MisEvaluacionesAdaptador extends BaseExpandableListAdapter {


	private Context context;
	private ArrayList<ProcesoEvaluacion360> procesos;
	private ArrayList<ArrayList<ArrayList<Competencia>>> competencias;
	
	public MisEvaluacionesAdaptador(Context contexto) {		
		this.context = contexto;
	}	
	
//	public MisEvaluacionesAdaptador(Context)
//	public MisEvaluacionesAdaptador(Context contexto, ArrayList<ProcesoXCompetencias> resultados) {
//		this.contexto = contexto;
////		this.resultados 
//		this.procesosXCompetencias = resultados;
//		
//	} 
//	
	
//	public MisEvaluacionesAdaptador(Context contexto, ArrayList<ProcesoXCompetencias> resultados) {
//		this.context = contexto;
//		
//	}
	
	public MisEvaluacionesAdaptador(Context contexto, ArrayList<ProcesoXCompetencias> resultados) {
//		this.context = contexto;
		
//		this.procesos = new ArrayList<ProcesoEvaluacion360>();
		
//		for (Proceso unProceso : )
		
//		this.context = contexto;
//		this.procesos = new ArrayList<ProcesoEvaluacion360>();
//		
//		for(ProcesoEvaluacion360 unProceso : )
		
//		for (ProcesoXCompetencias resultado : resultados) {
//			
//			this.procesos.add(resultado.getUnProceso());
//			
//		}
		this.context = contexto;
		this.procesos = new ArrayList<ProcesoEvaluacion360>();
		this.competencias = new ArrayList<ArrayList<ArrayList<Competencia>>>();
		
		for (ProcesoXCompetencias resultado : resultados) {
			
			
			this.procesos.add(resultado.getUnProceso());
//			this.compe
			
//			ArrayList<ArrayList<Competencia>> conjuntoDeGrupos = new ArrayLis
			ArrayList<ArrayList<Competencia>> lasCompetenciasEnEseProceso = new ArrayList<ArrayList<Competencia>>();
			
//			for(Competencia)
			
//			for(Competencia capacidad : resultado.getCompetencias()) {
				
//				ArrayList<Competencia> grupoDeUno = new ArrayList<Competencia>();
				
//				grupoDeUno.add(capacidad);
				
//				conjun
				
//				lasCompetenciasEn
				
//			}
			
			for (Competencia capacidad : resultado.getCompetencias()) {
				
				ArrayList<Competencia> grupoDeUno = new ArrayList<Competencia>();
				
				grupoDeUno.add(capacidad);
				
				lasCompetenciasEnEseProceso.add(grupoDeUno);
				
			}
			
//			this.competencias.add(lasCompetenciasEnEseProceso);
			this.competencias.add(lasCompetenciasEnEseProceso);
		}
		
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}	
	
//	@Override
//	public String getChild(int groupPosition, int childPosition) {
//		return "un Descendiente";
//	}	
	
	@Override
	public ArrayList<Competencia> getChild(int groupPosition, int childPosition) {
		return this.competencias.get(groupPosition).get(childPosition);
		
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}	
	
//	@Override
//	public View getChildView(int groupPosition, int childPosition,
//			boolean isLastChild, View convertView, ViewGroup parent) {
//
//		TextView unPuntaje = new TextView(this.context);
//		
//		unPuntaje.setText("Liderazgo - 75/100");
//		
//		return unPuntaje;
//	}	
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		Competencia unResultado = this.competencias.get(groupPosition).get(childPosition).get(0);
		
		TextView mensaje = new TextView(this.context);
		
		mensaje.setText(unResultado.getDescripcion() + " - " + unResultado.getNota() + "/100" );
		
		
		return mensaje;
	}
	
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		return 5;
//	}
	
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		return this.competencias.get(groupPosition).ss;
//		
//	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return this.competencias.get(groupPosition).size();
		
	}

//	@Override
//	public String getGroup(int groupPosition) {
//		return "unGrupo";
//	}
	
	
	@Override
	public ProcesoEvaluacion360 getGroup(int groupPosition) {
		return this.procesos.get(groupPosition);
		
	}

//	@Override
//	public int getGroupCount() {
//		return 3;
//	}
	
	@Override
	public int getGroupCount() {
		return this.procesos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parent) {
//
//		TextView unEventoDeEvaluacion = new TextView(this.context);
//		
//		unEventoDeEvaluacion.setText("Proceso de evaluación del último semestre");
//		
//		return unEventoDeEvaluacion;
//		
//	}
	
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, )
	
	
	
	
	
//	@Override
//	public View getGroupView(int groupPosition, boolean isExpanded,
//			View convertView, ViewGroup parentView) {
//		
//		
//		ProcesoEvaluacion360 unProceso = this.procesos.get(groupPosition);
//		
//		TextView nombre = new TextView(this.context);
//		
//		
//		nombre.setText(unProceso.Nombre);
//		
//		return nombre;
//		
//	}
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parentView) {
		
		
		ProcesoEvaluacion360 unProceso = this.procesos.get(groupPosition);
		
		TextView nombre = new TextView(this.context);
		
		nombre.setText(unProceso.Nombre);
		
		return nombre;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}	
}



