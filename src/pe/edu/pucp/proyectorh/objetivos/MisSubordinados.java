package pe.edu.pucp.proyectorh.objetivos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;
import pe.edu.pucp.proyectorh.LoginActivity;
import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.connection.ConnectionManager;
import pe.edu.pucp.proyectorh.model.AvanceDeObjetivo;
import pe.edu.pucp.proyectorh.model.Colaborador;
import pe.edu.pucp.proyectorh.model.Objetivo;
import pe.edu.pucp.proyectorh.model.Periodo;
import pe.edu.pucp.proyectorh.model.ObjetivosBSC;
import pe.edu.pucp.proyectorh.reclutamiento.MenuOfertasLaboralesTerceraFase;
import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.services.Servicio;
import pe.edu.pucp.proyectorh.utils.AdaptadorDeObjetivos;

public class MisSubordinados extends Fragment {

	private View rootView;
	private ArrayList<Objetivo> objetivos;
	private ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avances;
	public ArrayList<Colaborador> colaboradores ;
		
	public MisSubordinados() {
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			rootView  = inflater.inflate(R.layout.mis_subordinados,container, false);
			Context contexto = rootView.getContext();
			rootView.findViewById(R.layout.mis_subordinados);
			
			Resources res = getResources();
			
			final ArrayList<Objetivo> objetivosSubordinado = Objetivo.tomarPrestadoDataDePrueba();
			final ArrayList<AvanceDeObjetivo> avancesDeObjetivos = AvanceDeObjetivo.tomarPrestadoDataDePrueba(); 
			final ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>> avancesHastaHoy = new ArrayList<ArrayList<ArrayList<AvanceDeObjetivo>>>();
			
//			avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El primer objetivo no presenta progreso
//			avancesHastaHoy.add(new ArrayList<ArrayList<AvanceDeObjetivo>>()); //El segundo tampoco
			
			ArrayList<AvanceDeObjetivo> grupoDeUno = new ArrayList<AvanceDeObjetivo>();
			
			grupoDeUno.add(avancesDeObjetivos.get(2));
			
			ArrayList<AvanceDeObjetivo> otroGrupoDeUno = new ArrayList<AvanceDeObjetivo>();
			
			otroGrupoDeUno.add(avancesDeObjetivos.get(1));	
			
			ArrayList<ArrayList<AvanceDeObjetivo>> parDeAvancesTercerObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
			
			parDeAvancesTercerObjetivo.add(grupoDeUno);
			parDeAvancesTercerObjetivo.add(otroGrupoDeUno);
			
			avancesHastaHoy.add(parDeAvancesTercerObjetivo);
			
			//El primer y segundo objetivo están sin comenzar
			
			ArrayList<AvanceDeObjetivo> elAvanceDelCeroPorCiento = new ArrayList<AvanceDeObjetivo>();
			
			elAvanceDelCeroPorCiento.add(avancesDeObjetivos.get(0));
			
			ArrayList<ArrayList<AvanceDeObjetivo>> elUnicoAvanceDelObjetivo = new ArrayList<ArrayList<AvanceDeObjetivo>>();
			
			elUnicoAvanceDelObjetivo.add(elAvanceDelCeroPorCiento);
			
			avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
			avancesHastaHoy.add(0, elUnicoAvanceDelObjetivo);
			
			//Coloca la data en los cajones de la vista
			
			ExpandableListView listaDeObjetivos = (ExpandableListView) rootView.findViewById(R.id.AquiSupervisoSusObjetivos);
			AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), objetivosSubordinado, avancesHastaHoy);
			listaDeObjetivos.setAdapter(adaptador);
			listaDeObjetivos.setLongClickable(true);
			
			final MisSubordinados elContenedor = this;
			
			listaDeObjetivos.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					
					getFragmentManager().beginTransaction()
						.replace(elContenedor.getId(), new MenuOfertasLaboralesTerceraFase(), "Una etiqueta")
						.commit();
					
					return true;
				}
			});			
			
			
			
			
			//Esta parte responde: ¿De donde saca los subordinados?

			ArrayList<String> listadoSubordinadosNombreYApellidos = Colaborador.tomarPrestadoDataDePrueba();
			
			Spinner elMenuDeSubordinados = (Spinner) rootView.findViewById(R.id.UnoDeEstosSubordinados);
			
			ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listadoSubordinadosNombreYApellidos);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			elMenuDeSubordinados.setAdapter(dataAdapter);
			
//			Colaborador.consultarColaboradoresDelServidorDeProduccion();
//			MisSubordinados.consultarColaboradoresDelServidorDeProduccion();
			consultarColaboradoresDelServidorDeProduccion();
			
			return rootView;
		}	
	
	
	
	//Llamdas 
	//Llamadas al servidor
	
	public ArrayList<String> consultarColaboradoresDelServidorDeProduccion()
	{
//		String direccionDeDestino = "http://10.0.2.2:2642/Evaluacion360/GestorDatosDeColaboradores/consultarSusCompanerosPares?deEsteColaborador=23";
//		String direccionDeDestino = Servicio.ConsultarSubordinados + "?deEsteColaborador=23";
		String direccionDeDestino = Servicio.ConsultarSubordinados + "?deEsteColaborador=" + LoginActivity.usuario.getID();
//		String direccionDeDestino = "http://dp2kendo.apphb.com/Evaluacion360/GestorDatosDeColaboradores/conocerEquipoDeTrabajo?deEsteColaborador=23";
		
		System.out.println("pide="+direccionDeDestino);
		new UnaConsultaDeDatos().execute(direccionDeDestino);
		
		ArrayList<String> empleadosConSusDatos = new ArrayList<String>();
		
		empleadosConSusDatos.add("Todos los empleados");
		
		return empleadosConSusDatos;
		
	}

	public class UnaConsultaDeDatos extends AsyncCall {
		@Override
		protected void onPostExecute(String loQueRespondio) {
			System.out.println("Recibido: " + loQueRespondio.toString());
			
			try {
				
				JSONObject losSubordinados = new JSONObject(loQueRespondio);
				
				JSONObject losEmpleados = (JSONObject) losSubordinados.get("data");
				
				JSONArray elGrupoDeColaboradores = (JSONArray) losEmpleados
						.get("losEmpleadosQueLeReportan");
				
				ArrayList<Colaborador> susNombres = new ArrayList<Colaborador>();
				
				for (int i = 0; i < elGrupoDeColaboradores.length(); i++) {
					
					JSONObject suInformacionPersonal = elGrupoDeColaboradores.getJSONObject(i);
					
					Colaborador laPersona = new Colaborador();
					
					laPersona.setId(suInformacionPersonal.getString("ID"));
					laPersona.setNombres(suInformacionPersonal.getString("NombreCompleto"));
//					laPersona.set
					
//					JSONArray elGrupoDeColaboradores = (JSONArray) losEmpleados
//							.get("losEmpleadosQueLeReportan");		
					
					
					JSONArray losObjetivos = (JSONArray) suInformacionPersonal
							.get("Objetivos");	
					
//					laPersona.setSusObjetivos()
//					laPersona.setSusObjetivos(new)
//					ArrayList<Obje>
					ArrayList<Objetivo> objetivosDelModelo = new ArrayList<Objetivo>();
					
//					objetivosDelModelo.a
					for(int j = 0; j < losObjetivos.length(); j++)
					{
//						JSONObject objetivoDeLaRespuesta = losObjetivos.getJSONObject(index);
						JSONObject objetivoDeLaRespuesta = losObjetivos.getJSONObject(j);
						
						Objetivo objetivoDelModelo = new Objetivo();
						
//						objetivoDelModelo.setID(iD)
//						objetivoDelModelo = 
						objetivoDelModelo.setID(objetivoDeLaRespuesta.getString("ID"));
						objetivoDelModelo.setDescripcion(objetivoDeLaRespuesta.getString("Nombre"));
//						objetivoDelModelo.setPeso(objetivoDeLaRespuesta.getString("Peso"));
						objetivoDelModelo.setPeso(Integer.parseInt(objetivoDeLaRespuesta.getString("Peso")));
						
//						for (k)
//						for (int k = 0; k < )
						
						JSONArray losAvances = objetivoDeLaRespuesta.getJSONArray("LosProgresos");
						
						ArrayList<AvanceDeObjetivo> avancesDelModelo = new ArrayList<AvanceDeObjetivo>();
						
//						for (k)
//						for ( int k = 0; k < losAvances.g)
//						for ( int k = 0; k < losAvances.length(); k++)
//						{
//							
//							
//						}
						
						for (int k = 0; k < losAvances.length(); k++)
						{
//							AvanceDeObjetivo unAvance = new AvanceDeObjetivo(iD, fechaRegistro, logroAlcanzado)
//							AvanceDeObjetivo unAvance = new AvanceDeObjetivo(iD, fechaRegistro, logroAlcanzado)
							AvanceDeObjetivo unAvance = new AvanceDeObjetivo();
//							unAvance.d
//							unAvance.setID(iD)
							JSONObject avanceDeLaRespuesta = losAvances.getJSONObject(k);
							
							unAvance.setID(avanceDeLaRespuesta.getString("ID"));
//							unAvance.setLogroAlcanzado(avanceDeLaRespuesta.getString("Valor"));
							unAvance.setLogroAlcanzado(Integer.parseInt((avanceDeLaRespuesta.getString("Valor"))));
							unAvance.setFechaRegistro(avanceDeLaRespuesta.getString("FechaCreacion"));
							
//							objetivoDelModelo.
							avancesDelModelo.add(unAvance);
						}
						
						objetivoDelModelo.setSusAvances(avancesDelModelo);
						objetivosDelModelo.add(objetivoDelModelo);
					}
					
//					laPersona.ad
//					laPersona.setSusObjetivos(susObjetivos);
					laPersona.setSusObjetivos(objetivosDelModelo);
					
//					elGrupoDeColaboradores
					susNombres.add(laPersona);
				}
				actualizaColaboradores(susNombres);
//				this.colaboradores = susNombres;
				
//				comunicaLosSubordinados(susNombres);
//				actualizaComponentesDeLaVista(ArrayList<Colaborador> susNombres)
				actualizaComponentesDeLaVista(susNombres);
				
			} catch (Exception ocurrioUnProblema) {
				System.out.println("Sucedio un inconveniente: " + ocurrioUnProblema);
				
			}
			
		}
	}		
	
	public void actualizaColaboradores(ArrayList<Colaborador> susNombres){
		this.colaboradores = susNombres;
	}
//	public void comunicaLosSubordinados(ArrayList<Colaborador> susNombres)
//	public void actualizaComponentesDeLavista(ArrayList<Colaborador> susNombres)
	
//	public void retornaColaboradorSeleccionado(String id) {
	public Colaborador retornaColaboradorSeleccionado(String id) {
		
		for(Colaborador colaborador : colaboradores)
		{
			if (colaborador.getId().compareTo(id) == 0) return colaborador;
			
		}
		
		return null;
		
	}
	
	public Context retornaContexto() {
		
		return this.getActivity().getApplicationContext();
		
		
	}	
	
	public void actualizaComponentesDeLaVista(ArrayList<Colaborador> susNombres)
	{
		final ArrayList<Colaborador> losEmpleados = susNombres;
		
		
		//Los 
		//El combo de subordinados
		Spinner elMenuDeSubordinados = (Spinner) rootView.findViewById(R.id.UnoDeEstosSubordinados);
		
		ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, susNombres);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		elMenuDeSubordinados.setAdapter(dataAdapter);
		
		elMenuDeSubordinados.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				String elColaboradorSeleccionado = losEmpleados.get(pos).getId();
				System.out.println("Persona: " + elColaboradorSeleccionado);
				Colaborador colaborador = retornaColaboradorSeleccionado(elColaboradorSeleccionado);
//				actualizaTabs();
				ExpandableListView listaDeObjetivos = (ExpandableListView) rootView.findViewById(R.id.AquiSupervisoSusObjetivos);
//				AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), objetivosSubordinado, avancesHastaHoy);
//				AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), losEmpleados);
//				AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), losEmpleados);
				AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(retornaContexto(), colaborador);
				listaDeObjetivos.setAdapter(adaptador);				
			}
			
			@Override
			  public void onNothingSelected(AdapterView<?> arg0) {
		    	// TODO Auto-generated method stub
			  }
		});		
		

		
//		Colaborador.consultarColaboradoresDelServidorDeProduccion();
//		MisSubordinados.consultarColaboradoresDelServidorDeProduccion();
//				
		//E
		//La lista expandible de objetivos y avances
//		ExpandableListView listaDeObjetivos = (ExpandableListView) rootView.findViewById(R.id.AquiSupervisoSusObjetivos);
////		AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), objetivosSubordinado, avancesHastaHoy);
//		AdaptadorDeObjetivos adaptador = new AdaptadorDeObjetivos(this.getActivity().getApplicationContext(), losEmpleados);
//		listaDeObjetivos.setAdapter(adaptador);
//		listaDeObjetivos.setLongClickable(true);		
		
	}	
	
}
