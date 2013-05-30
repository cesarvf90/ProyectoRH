package pe.edu.pucp.proyectorh.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Objetivo;

public class AdaptadorDeObjetivos extends BaseExpandableListAdapter {
	
	private ArrayList<Objetivo> objetivos;
	private ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avances;
	private Context context;
	
	public AdaptadorDeObjetivos(Context contexto, ArrayList<Objetivo> objetivos, ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avances) {
		this.context = contexto;
		this.objetivos = objetivos;
		this.avances = avances;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}	
	
	@Override
	public ArrayList<AvanceDeObjetivo> getChild(int groupPosition, int childPosition) {
		return avances.get(groupPosition).get(childPosition);
	}	
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}	
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		AvanceDeObjetivo avance = getChild(
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
		textoAvance.setText(avance.descritoBrevemente());

		return convertView;
	}	
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return avances.get(groupPosition).size();
	}

	@Override
	public Objetivo getGroup(int groupPosition) {
		return objetivos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return objetivos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		Objetivo group = getGroup(groupPosition);

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
		grouptxt.setText(group.descritoBrevemente());

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
