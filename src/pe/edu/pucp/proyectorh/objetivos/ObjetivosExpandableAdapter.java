package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ObjetivosExpandableAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<ObjetivosBSC> groups;
	 
    private ArrayList<ArrayList<ObjetivosBSC>> children;

	private Context context;

	public ObjetivosExpandableAdapter(Context context, ArrayList<ObjetivosBSC> groups, ArrayList<ArrayList<ObjetivosBSC>> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }
	 

	@Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }


    @Override
    public ObjetivosBSC getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {

    	System.out.println("vere gpos="+groupPosition+ " y cpos="+childPosition);
    	String child = getChild(groupPosition, childPosition).Nombre;
    		 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_child, null);
        }

        TextView childtxt = (TextView) convertView.findViewById(R.id.TextViewChild01);

        childtxt.setText(child);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public ObjetivosBSC getGroup(int groupPosition) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

    	String group = getGroup(groupPosition).Nombre;

    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);
        }

        TextView grouptxt = (TextView) convertView.findViewById(R.id.TextViewGroup);

        grouptxt.setText(group);

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
