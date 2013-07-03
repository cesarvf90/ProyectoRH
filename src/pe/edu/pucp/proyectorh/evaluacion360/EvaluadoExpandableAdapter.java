package pe.edu.pucp.proyectorh.evaluacion360;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDTO;
import pe.edu.pucp.proyectorh.model.Competencia;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.model.ProcesoEvaluacion360;
import pe.edu.pucp.proyectorh.model.ProcesoXCompetencias;
import android.content.Context;
import android.graphics.Color;
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

public class EvaluadoExpandableAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<ProcesoEvaluacion360> groups;
    private ArrayList<ArrayList<ArrayList<Competencia>>> children;

    private ArrayList<Integer> contadorImpresionHijos;
    
    TableLayout lay;
    
	private Context contexto;
	boolean flagMostrar;
	boolean primeraVez=true;

	public EvaluadoExpandableAdapter(Context contexto, ArrayList<ProcesoXCompetencias> resultados) {
        this.contexto = contexto;
    	this.groups = new ArrayList<ProcesoEvaluacion360>();
		this.children = new ArrayList<ArrayList<ArrayList<Competencia>>>();
		
		for (ProcesoXCompetencias resultado : resultados) {
			
			
			this.groups.add(resultado.getUnProceso());
			ArrayList<ArrayList<Competencia>> lasCompetenciasEnEseProceso = new ArrayList<ArrayList<Competencia>>();
			
			for (Competencia capacidad : resultado.getCompetencias()) {
				
				ArrayList<Competencia> grupoDeUno = new ArrayList<Competencia>();
				
				grupoDeUno.add(capacidad);
				
				lasCompetenciasEnEseProceso.add(grupoDeUno);
				
			}
			
			this.children.add(lasCompetenciasEnEseProceso);
		}
	}

	 

	@Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }


    @Override
	public ArrayList<Competencia> getChild(int groupPosition, int childPosition) {
		return this.children.get(groupPosition).get(childPosition);
		
	}

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

   
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {
  	
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.evaluado_expandablelist_child, null);           
        }
        
        Competencia unResultado = this.children.get(groupPosition).get(childPosition).get(0);
		
		TextView mensaje = (TextView) convertView.findViewById(R.id.evaluadoDesc);
		mensaje.setText(unResultado.getDescripcion());
		mensaje.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,90));
		System.out.println("res="+unResultado.getDescripcion()+"fin");

		TextView mensaje2 = (TextView) convertView.findViewById(R.id.evaluadoLogrado);
		mensaje2.setText(" - "+unResultado.getNota() + "/100 ");
		mensaje2.setLayoutParams(new TableLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,10));
		System.out.println("res2="+unResultado.getNota()+"fin2");

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
    	System.out.println("groupView de ="+group +" isExp="+isExpanded);    	

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