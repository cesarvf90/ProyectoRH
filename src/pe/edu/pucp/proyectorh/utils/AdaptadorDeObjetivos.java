package pe.edu.pucp.proyectorh.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
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
	
//	public AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), losEmpleados);
//	public AdaptadorDeObjetivos(Context contexto, ArrayList<Colaborador> losEmpleados) {
	public AdaptadorDeObjetivos(Context contexto, Colaborador elEmpleado) {
		this.context = contexto;
		
//		rootView  = inflater.inflate(R.layout.mis_subordinados,container, false);
//		Context contexto = rootView.getContext();
//		rootView.findViewById(R.layout.mis_subordinados);
		
//		Resources res = getResources();
		
//		final ArrayList<Objetivo> objetivosSubordinado = Objetivo.tomarPrestadoDataDePrueba();
//		final ArrayList<AvanceDeObjetivo> avancesDeObjetivos = AvanceDeObjetivo.tomarPrestadoDataDePrueba(); 
//		final ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avancesHastaHoy = new ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>>();
//		
		final ArrayList<Objetivo> objetivosSubordinado =new ArrayList<Objetivo>();
//		new ArrayList<Objetivo>();
//		final ArrayList<AvanceDeObjetivo> avancesDeObjetivos = new ArrayList<ArrayList<AvanceDeObjetivo>>();
//		final ArrayList<AvanceDeObjetivo> avancesDeObjetivos = new ArrayList<AvanceDeObjetivo>();
		final ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avancesHastaHoy = new ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>>();
			
//		avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El primer objetivo no presenta progreso
//		avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El segundo tampoco
		
//		fpr
//		for (Colaborador empleado : losEmpleados)
//		{
//			Objetivo unObjetivo = new Objetivo();
//			Objetivo unoDeSusObjetivos = new Objetivo();
//			for (Objetivo objetivo in empleado.getO)
//			for (Objetivo objetivo : empleado.getO)
		
		for (Objetivo objetivo : elEmpleado.getSusObjetivos())
		{
//			avancesHastaHoy.add(object);
//			avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>());
			
//			avancesHastaHoy.get(avancesHastaHoy.size() - 1) = new ArrayList<Ava>
//			avancesHastaHoy.get(avancesHastaHoy.size() - 1) = new ArrayList<AvanceDeObjetivo>();
			
			ArrayList<ArrayList<AvanceDeObjetivo>> losAvances = new ArrayList<ArrayList<AvanceDeObjetivo>>();
//			losAvances.
//			losAvances
//			for (AvanceDeObjetivo avance : objetivo)
			for (AvanceDeObjetivo avance : objetivo.getSusAvances())
			{
//				ArrayList<AvanceDeObjetivo> n
				ArrayList<AvanceDeObjetivo> unGrupoDeUno = new ArrayList<AvanceDeObjetivo>();
				unGrupoDeUno.add(avance);
				losAvances.add(unGrupoDeUno);
//			}
			}
			avancesHastaHoy.add(losAvances);
		}
		
		
			
			
			
//			unObjetivo
			
			
//		}
		
//		ArrayList<AvanceDeObjetivo> grupoDeUno = new ArrayList<AvanceDeObjetivo>();
//		
//		grupoDeUno.add(avancesDeObjetivos.get(2));
//		
//		ArrayList<AvanceDeObjetivo> otroGrupoDeUno = new ArrayList<AvanceDeObjetivo>();
//		
//		otroGrupoDeUno.add(avancesDeObjetivos.get(1));	
//		
//		ArrayList<ArrayList<AvanceDeObjetivo>> parDeAvancesTercerObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
//		
//		parDeAvancesTercerObjetivo.add(grupoDeUno);
//		parDeAvancesTercerObjetivo.add(otroGrupoDeUno);
//		
//		avancesHastaHoy.add(parDeAvancesTercerObjetivo);
//		
//		//El primer y segundo objetivo están sin comenzar
//		
//		ArrayList<AvanceDeObjetivo> elAvanceDelCeroPorCiento = new ArrayList<AvanceDeObjetivo>();
//		
//		elAvanceDelCeroPorCiento.add(avancesDeObjetivos.get(0));
//		
//		ArrayList<ArrayList<AvanceDeObjetivo>> elUnicoAvanceDelObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
//		
//		elUnicoAvanceDelObjetivo.add(elAvanceDelCeroPorCiento);
//		
//		avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
//		avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
		
		//Coloca la data en los cajones de la vista
		
//		ExpandableListView listaDeObjetivos = (ExpandableListView) rootView.findViewById(R.id.AquiSupervisoSusObjetivos);
//		AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), objetivosSubordinado, avancesHastaHoy);
//		listaDeObjetivos.setAdapter(adaptador);
//		listaDeObjetivos.setLongClickable(true);
//		
//		
//		
	
//		this.avances
//		this.onb
		this.objetivos = elEmpleado.getSusObjetivos();
		this.avances = avancesHastaHoy;
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

//		TextView textoAvance = (TextView) convertView
//				.findViewById(R.id.TextViewChild01);

//		textoAvance.setText(avance.toString());
//		textoAvance.setText(avance.descritoBrevemente());
		
//		ViewGroup unElemento = new ViewGroup(parent.getContext()) {
		LinearLayout unElemento = new LinearLayout(parent.getContext()) {
			
			@Override
			protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
				// TODO Auto-generated method stub
				
				final int cuenta = getChildCount();
				
//				int ancho = hi
				
//				for (int i = 0; i < cuenta; i++)
//				{
//					final View hijo = this.getChildAt(i);
//					
////					int ancho = hijo.getMeasuredHeight();
////					int ancho = hijo.getMeasuredWidth();
////					int alto = hijo.getMeasuredHeight();
//					
//					int ancho = 100;
//					int alto = 100;
//					
//					
//					hijo.layout(10, 10, 10 + ancho, 10 + alto);
//					
//				}
//				

				int ancho, alto;
				
				
				final View descripcion = this.getChildAt(0);
				
				
				ancho = 500;
				alto = 100;
				
				
				descripcion.layout(10, 10, 10 + ancho, 10 + alto);	
				
				
				final View autorizar = this.getChildAt(1);
				
				
				ancho = 150;
				alto = 30;
				
				
				autorizar.layout(500, 5, 10 + ancho, 5 + alto);		
				
				
				final View ajustar = this.getChildAt(2);
				
				
				ancho = 150;
				alto = 30;
				
				
				ajustar.layout(700, 5, 820 + ancho, 5 + alto);						
			}
		};
		
//		View elAvance = new TextView
//		TextView elAvance = new TextView(parent.getContext()).setText(avance.descritoBrevemente());
		TextView elAvance = new TextView(parent.getContext());
//		elAvance.setWidth(pixels);
		elAvance.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		elAvance.setText(avance.descritoBrevemente());
		
		Button aceptar = new Button(parent.getContext());
		aceptar.setText("Aprobar");
		aceptar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		Button cambiarValor = new Button(parent.getContext());
		cambiarValor.setText("Modificar");
		cambiarValor.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		unElemento.addView(elAvance);
		unElemento.addView(aceptar);
		unElemento.addView(cambiarValor);
		
//		View unElemento = new Vi

//		return convertView;
		return unElemento;
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
