package pe.edu.pucp.proyectorh.utils;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Evaluador;
import pe.edu.pucp.proyectorh.model.Objetivo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class EvaluacionesAdaptador extends BaseExpandableListAdapter {
	
	private ArrayList<Colaborador> evaluados;
	private ArrayList<ArrayList<ArrayList<Evaluador>>> losParticipantes;
	private Context context;
	
	public EvaluacionesAdaptador(Context contexto, ArrayList<Colaborador> subordinados, ArrayList<ArrayList<ArrayList<Evaluador>>> empleados) {
		this.context = contexto;
		this.evaluados = subordinados;
		this.losParticipantes = empleados;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}	
	
	@Override
	public ArrayList<Evaluador> getChild(int groupPosition, int childPosition) {
		return losParticipantes.get(groupPosition).get(childPosition);
	}	
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}	
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		Evaluador colaborador = getChild(
				groupPosition, childPosition).get(0);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
//					R.layout.expandablelistview_child, null);
					R.layout.expandable_ofertas_child, null);
		}

		TextView textoAvance = (TextView) convertView
				.findViewById(R.id.TextViewChild01);

//		textoAvance.setText(avance.toString());
		textoAvance.setText(colaborador.presentaSuInformacion());

		return convertView;
	}	
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return losParticipantes.get(groupPosition).size();
	}

	@Override
	public Colaborador getGroup(int groupPosition) {
		return evaluados.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return evaluados.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		Colaborador group = getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
			// R.layout.expandablelistview_group, null);
					R.layout.expandable_ofertas_group, null);
		}

		TextView grouptxt = (TextView) convertView
				.findViewById(R.id.TextViewGroup);

//		grouptxt.setText(group.toString());
		grouptxt.setText(group.retornarPresentacionBreve());

		return convertView;
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
