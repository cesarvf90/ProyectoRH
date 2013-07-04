package pe.edu.pucp.proyectorh.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
//import pe.edu.pucp.proyectorh.evaluacion.ProcesoXEvaluadorXEvaluado;
import pe.edu.pucp.proyectorh.evaluacion360.ProcesoXEvaluadorXEvaluado;

public class AdaptadorDeEvaluaciones extends BaseExpandableListAdapter {
	
	private Context contexto;
	private ArrayList<Colaborador> subordinados;
	private ArrayList<ArrayList<ArrayList<ProcesoXEvaluadorXEvaluado>>> pxexes;
	
	
	public AdaptadorDeEvaluaciones(Context contexto, ArrayList<ProcesoXEvaluadorXEvaluado> evaluaciones) {
		this.contexto = contexto;
		agrupaEvaluacionesPorSubordinado(evaluaciones);
	}
	
	private void agrupaEvaluacionesPorSubordinado(ArrayList<ProcesoXEvaluadorXEvaluado> pxexes) {
		
		ArrayList<Colaborador> colaboradores = new ArrayList<Colaborador>();
		
		
		Map<Colaborador, ArrayList<ProcesoXEvaluadorXEvaluado>> mapa = new HashMap<Colaborador, ArrayList<ProcesoXEvaluadorXEvaluado>>();

		for(ProcesoXEvaluadorXEvaluado pxexe : pxexes)
		{
			Colaborador subordinado = pxexe.getElEvaluado();
			if (mapa.get(subordinado) == null) {
				mapa.put(subordinado, new ArrayList<ProcesoXEvaluadorXEvaluado>());
			}
			
			mapa.get(subordinado).add(pxexe);
		}
		
		Object[] subordinados = mapa.keySet().toArray();
		
		this.pxexes = new ArrayList<ArrayList<ArrayList<ProcesoXEvaluadorXEvaluado>>>();
		
		for(Object unSubordinado : subordinados)
		{
			colaboradores.add((Colaborador) unSubordinado );
			
			
			ArrayList<ProcesoXEvaluadorXEvaluado> evaluacionesDelSubordinado = mapa.get((Colaborador) unSubordinado );
			
			ArrayList<ArrayList<ProcesoXEvaluadorXEvaluado>> gruposDeUno = new ArrayList<ArrayList<ProcesoXEvaluadorXEvaluado>>();
			
			for(ProcesoXEvaluadorXEvaluado pxexe : evaluacionesDelSubordinado) {
				
				ArrayList<ProcesoXEvaluadorXEvaluado> grupo = new ArrayList<ProcesoXEvaluadorXEvaluado>();
				grupo.add(pxexe);
				gruposDeUno.add(grupo);
				
			}
			
			this.pxexes.add(gruposDeUno);
			
		}
		
		this.subordinados = colaboradores;
	}
	
	@Override
	public boolean areAllItemsEnabled()
	{
		return true;
	}
	
	@Override
	public ArrayList<ProcesoXEvaluadorXEvaluado> getChild(int groupPosition, int childPosition) 
	{
		return pxexes.get(groupPosition).get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
	      if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.subordinados_expandablelist_child, null);           
	        }
	        
		ProcesoXEvaluadorXEvaluado pxexe = getChild(groupPosition, childPosition).get(0);
		String nombre = "Evaluador: "+pxexe.getEvaluador().getNombreCompleto() +".";
		String estado = "Estado: "+pxexe.getEstado()+".";
		String nota = "Nota: "+pxexe.getLaCalificacion()+".";


		TextView szNombre = (TextView) convertView.findViewById(R.id.misSubEvaluador);
		szNombre.setText(nombre);
		szNombre.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,70));
		szNombre.setTextColor(Color.BLACK);	
		
		TextView szEstado = (TextView) convertView.findViewById(R.id.misSubEstado);
		szEstado.setText(estado);
		szEstado.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
		szEstado.setTextColor(Color.BLACK);	
		
		TextView szNota = (TextView) convertView.findViewById(R.id.misSubNota);
		szNota.setText(nota);
		szNota.setLayoutParams(new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,15));
		szNota.setTextColor(Color.BLACK);	
		
		return convertView;
		
	}
	
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return pxexes.get(groupPosition).size();
	}
	
	
	@Override
	public int getGroupCount() {
		return subordinados.size();
	}
	
	
	
	@Override
	public Colaborador getGroup(int groupPosition) {
		return subordinados.get(groupPosition);
		
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		Colaborador subordinado = subordinados.get(groupPosition);
		
		if (convertView == null) {
			LayoutInflater procesador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = procesador.inflate(R.layout.expandablelistview_group, null);
			convertView.setBackgroundColor(Color.parseColor("#2EFE9A"));
		}
		
		TextView groupTxt = (TextView) convertView.findViewById(R.id.TextViewGroup);
		
		groupTxt.setText(subordinado.getNombreCompleto());
		groupTxt.setTextColor(Color.BLACK);
		
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










































