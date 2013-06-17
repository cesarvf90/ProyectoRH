package pe.edu.pucp.proyectorh.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MisEvaluacionesAdaptador extends BaseExpandableListAdapter {


	private Context context;
	
	public MisEvaluacionesAdaptador(Context contexto) {		
		this.context = contexto;
	}	
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}	
	
	@Override
	public String getChild(int groupPosition, int childPosition) {
		return "un Descendiente";
	}	
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}	
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		TextView unPuntaje = new TextView(this.context);
		
		unPuntaje.setText("Liderazgo - 75/100");
		
		return unPuntaje;
	}	
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return 5;
	}

	@Override
	public String getGroup(int groupPosition) {
		return "unGrupo";
	}

	@Override
	public int getGroupCount() {
		return 3;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		TextView unEventoDeEvaluacion = new TextView(this.context);
		
		unEventoDeEvaluacion.setText("Proceso de evaluación del último semestre");
		
		return unEventoDeEvaluacion;
		
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
