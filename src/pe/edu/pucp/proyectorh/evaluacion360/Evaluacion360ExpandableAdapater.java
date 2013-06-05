package pe.edu.pucp.proyectorh.evaluacion360;


import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Evaluados360;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.model.OfertaLaboral;
import pe.edu.pucp.proyectorh.model.Postulante;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.reclutamiento.EvaluacionPostulante;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Evaluacion360ExpandableAdapater extends BaseExpandableListAdapter {
	
	private ArrayList<ProcesoEvaluacion360> groups;
	 
    private ArrayList<ArrayList<Evaluados360>> children;

	private Context contexto;
	FragmentActivity act;

	public Evaluacion360ExpandableAdapater(Context contexto,FragmentActivity act, ArrayList<ProcesoEvaluacion360> groups, ArrayList<ArrayList<Evaluados360>> children) {
        this.contexto = contexto;
        this.groups = groups;
        this.children = children;
        this.act = act;
	}
	
	public void actualizaHijos(ArrayList<ArrayList<Evaluados360>> children){
		System.out.println("actualiza hijos con childrencant="+children.size());
		this.children = children;
	}
	 

	@Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }


    @Override
    public Evaluados360 getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

   
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {
    	String texto = getChild(groupPosition, childPosition).Nombre;
    	String estado = getChild(groupPosition, childPosition).estado;
    	
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.evaluacion_expandablelist_child, null);           
        }
     
  	    TextView childTxt = (TextView) convertView.findViewById(R.id.evaluadoNombre);
  	    childTxt.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
  	    childTxt.setText(texto);

  	    TextView childTxtEstado = (TextView) convertView.findViewById(R.id.evaluadoEstado);
  	    childTxtEstado.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
  	    childTxtEstado.setText(estado);
  	    if(estado.compareTo("Evaluar")==0){
	  	    childTxtEstado.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					FragmentTransaction ft = act
							.getSupportFragmentManager()
							.beginTransaction();
					Evaluar fragment = new Evaluar();
					ft.setCustomAnimations(
							android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					ft.replace(R.id.opcion_detail_container,
							fragment, "detailFragment")
							.addToBackStack("tag").commit();
				}
			});
  	    }
  	    
	    return convertView;       
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public ProcesoEvaluacion360 getGroup(int groupPosition) {
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
    	System.out.println("vista de = "+group);
    	
    	if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);
            convertView.setBackgroundColor(Color.parseColor("#2EFE9A"));
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
