package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;

import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.objetivos.ObjetivosEmpresa.TableFila;
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

public class ObjetivosExpandableAdapter extends BaseExpandableListAdapter {
	
	private ArrayList<ObjetivosBSC> groups;
	 
    private ArrayList<ArrayList<ObjetivosBSC>> children;

    
	private Context contexto;

	public ObjetivosExpandableAdapter(Context contexto, ArrayList<ObjetivosBSC> groups, ArrayList<ArrayList<ObjetivosBSC>> children) {
        this.contexto = contexto;
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
            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.objetivos_expandablelist_child, null);
        }
        
        TableLayout lay = (TableLayout) convertView.findViewById(R.id.objChild);
        TableFila fila = agregaFila(getChild(groupPosition, childPosition),0);
        
        lay.addView(fila);

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
    
	class TableFila extends TableRow {
		int flagUlt = 0;
		
		public TableFila(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}		
	}
    
	public TableFila agregaFila(ObjetivosBSC objBSC, final int flagUltimo){
		final TableFila fila = new TableFila(contexto);
		fila.flagUlt=flagUltimo;
		String szNombre ="";
		String szPeso ="";
		String szCreador=LoginActivity.getUsuario().getUsername();
		
		if(objBSC != null){
			szNombre=objBSC.Nombre;
			szPeso = Integer.toString(objBSC.Peso);
			szCreador = LoginActivity.getUsuario().getUsername(); //objBSC.CreadorID;
		}
		
		fila.setLayoutParams(new TableLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

	    EditText descripObj = new EditText(contexto);
	    descripObj.setInputType(InputType.TYPE_CLASS_TEXT);
	  
	    descripObj.setText(szNombre);
	    descripObj.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,85));
	    fila.addView(descripObj);
		
	    EditText peso = new EditText(contexto);
	    peso.setInputType(InputType.TYPE_CLASS_NUMBER);
	    peso.setText(szPeso);
	    peso.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
	    fila.addView(peso);
	    
	    /*
	    TextView creador = new TextView(contexto);
	    creador.setText(szCreador);
	    creador.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,20));
	    fila.addView(creador);
	    */
	    
	    Button eliminarObj = new Button(contexto);
	    eliminarObj.setText("X");
	    eliminarObj.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View v) {

			  }
		});
	    fila.addView(eliminarObj);	
	    
	    if(fila.flagUlt==1){
		    final Button aumentarObj = new Button(contexto);
		    aumentarObj.setText("+");
		    aumentarObj.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View v) {	
					 
				  }
			});
		    fila.addView(aumentarObj);	
	    }
	    System.out.println("retorna fila");
	return fila;
}

}
