package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.ColaboradorEquipoTrabajo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AdapterEquipoObjetosColaboradores extends
		BaseExpandableListAdapter {

	private ArrayList<ColaboradorEquipoTrabajo> groups;

	private ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> children;

	private Context context;

	public AdapterEquipoObjetosColaboradores(Context context,
			ArrayList<ColaboradorEquipoTrabajo> groups,
			ArrayList<ArrayList<ArrayList<ColaboradorEquipoTrabajo>>> children) {
		this.context = context;
		this.groups = groups;
		this.children = children;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public ArrayList<ColaboradorEquipoTrabajo> getChild(int groupPosition,
			int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ColaboradorEquipoTrabajo child = getChild(groupPosition, childPosition)
				.get(0);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_child, null);
		}

		TextView childtxt = (TextView) convertView
				.findViewById(R.id.TextViewChild01);

		childtxt.setText((child.getNombres() == "null" ? "" : child
				.getNombres())
				+ " "
				+ (child.getApellidoPaterno() == "null" ? "" : child
						.getApellidoPaterno()));

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@Override
	public ColaboradorEquipoTrabajo getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ColaboradorEquipoTrabajo group = getGroup(groupPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_group, null);
		}

		TextView grouptxt = (TextView) convertView
				.findViewById(R.id.TextViewGroup);

		// Si coincide con la persona logueada (la que hace la
		// consulta) le agregamos una marca [] para distinguirlo
		if (group.getId() == LoginActivity.usuario.getID()) {
			grouptxt.setText("[ "
					+ (group.getNombres() == "null" ? "" : group.getNombres())
					+ " "
					+ (group.getApellidoPaterno() == "null" ? "" : group
							.getApellidoPaterno()) + " ]");
		} else {
			grouptxt.setText((group.getNombres() == "null" ? "" : group
					.getNombres())
					+ " "
					+ (group.getApellidoPaterno() == "null" ? "" : group
							.getApellidoPaterno()));
		}
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
