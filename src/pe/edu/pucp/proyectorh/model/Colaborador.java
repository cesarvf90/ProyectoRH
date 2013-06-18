package pe.edu.pucp.proyectorh.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.edu.pucp.proyectorh.services.AsyncCall;
import pe.edu.pucp.proyectorh.utils.Constante;

/**
 * Clase Colaborador mapeada contra ColaboradorDTO de la aplicacion web
 * 
 * @author Cesar
 * 
 */
public class Colaborador {

	private String id;
	private String nombreCompleto;
	private String nombres;
	private String apellidos;
	private String areaID;
	private String area;
	private String puesto;
	private String puestoID;
	private String anexo;
	private String fechaIngreso;
	private String fechaNacimiento;
	private String correoElectronico;
	private String telefono;
	private String centroEstudios;
	private String direccion;
	private String paisID;
	private String tipoDocumentoID;
	private String numeroDocumento;
	private String imagenColaborador;
	private String Sueldo;
	private String resumenEjecutivo;
	private String estadoColaboradorID;
	
//	private List<Objetivo> susObjetivos;
	private ArrayList<Objetivo> susObjetivos;

	public static ArrayList<String> consultarColaboradoresDelServidorDeProduccion() {
		// String direccionDeDestino =
		// "http://10.0.2.2:2642/Evaluacion360/GestorDatosDeColaboradores/consultarSusCompanerosPares?deEsteColaborador=23";
		String direccionDeDestino = "http://dp2kendo.apphb.com/Evaluacion360/GestorDatosDeColaboradores/conocerEquipoDeTrabajo?deEsteColaborador=23";

		new UnaConsultaDeDatos().execute(direccionDeDestino);

		ArrayList<String> empleadosConSusDatos = new ArrayList<String>();

		empleadosConSusDatos.add("Todos los empleados");

		return empleadosConSusDatos;

	}

	public static class UnaConsultaDeDatos extends AsyncCall {
		@Override
		protected void onPostExecute(String loQueRespondio) {
			System.out.println("Recibido: " + loQueRespondio.toString());

			try {

				JSONObject losSubordinados = new JSONObject(loQueRespondio);

				JSONObject losEmpleados = (JSONObject) losSubordinados
						.get("data");

				JSONArray elGrupoDeColaboradores = (JSONArray) losEmpleados
						.get("losEmpleadosQueLeReportan");

				ArrayList<Colaborador> susNombres = new ArrayList<Colaborador>();

				for (int i = 0; i < elGrupoDeColaboradores.length(); i++) {

					JSONObject suInformacionPersonal = elGrupoDeColaboradores
							.getJSONObject(i);

					Colaborador laPersona = new Colaborador();

					laPersona.setId(suInformacionPersonal.getString("ID"));
					laPersona.setNombres(suInformacionPersonal
							.getString("NombreCompleto"));

					// elGrupoDeColaboradores
					susNombres.add(laPersona);
				}

			} catch (Exception ocurrioUnProblema) {
				System.out.println("Sucedio un inconveniente: "
						+ ocurrioUnProblema);

			}

		}
	}

	public static ArrayList<String> tomarPrestadoDataDePrueba() {

		String rchavez = "Chávez Alcántara, Rodrigo";
		String crios = "Ríos Montesinos, Carmen";
		String surteaga = "Urteaga Gonzáles, Sammantha";
		String ccamino = "Camino Prades, Carla";
		String abustamante = "Bustamante Ferda, Alejandra";
		String areas = "Reas Fernández, Antonnella";

		ArrayList<String> losSupuestosSubordinados = new ArrayList<String>();

		losSupuestosSubordinados.add(rchavez);
		losSupuestosSubordinados.add(crios);
		losSupuestosSubordinados.add(surteaga);
		losSupuestosSubordinados.add(ccamino);
		losSupuestosSubordinados.add(abustamante);
		losSupuestosSubordinados.add(areas);

		return losSupuestosSubordinados;

	}

	public static ArrayList<Colaborador> devolverSubordinadosFicticios() {
		ArrayList<String> losNombres = tomarPrestadoDataDePrueba();

		ArrayList<Colaborador> personas = new ArrayList<Colaborador>();

		// for(String elEmpleado : losNombres)
		// {
		// // personas.add(new Colaborador());
		// Colaborador subordinado = new Colaborador();
		// subordinado.setNombres(elEmpleado);
		// subordinado.setPuesto("Gerente de Ventas");
		// // subordinado.setid(i);
		//
		// personas.add(subordinado);
		// }

		Colaborador subordinado = new Colaborador();
		// subordinado.setNombres("Chávez Alcántara, Rodrigo");
		subordinado.setNombres(losNombres.get(0));
		subordinado.setPuesto("Gerente de Ventas");

		Colaborador crios = new Colaborador();
		crios.setNombres(losNombres.get(1));
		crios.setPuesto("Gerente de Marketing");

		Colaborador surteaga = new Colaborador();
		surteaga.setNombres(losNombres.get(2));
		surteaga.setPuesto("Gerente de Operaciones");

		Colaborador ccamino = new Colaborador();
		ccamino.setNombres(losNombres.get(3));
		ccamino.setPuesto("Gerente de Recursos Humanos");

		Colaborador abustamante = new Colaborador();
		abustamante.setNombres(losNombres.get(4));
		abustamante.setPuesto("Subgerente");

		Colaborador areas = new Colaborador();
		areas.setNombres(losNombres.get(5));
		areas.setPuesto("Analista de riesgos");

		personas.add(subordinado);
		personas.add(crios);
		personas.add(surteaga);
		personas.add(ccamino);
		personas.add(abustamante);
		personas.add(areas);

		return personas;

	}

	public Colaborador() {
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
	}

	public Colaborador(String nombres, String apellidos, String area,
			String puesto, String fechaIngreso, String fechaNacimiento,
			String correoElectronico, String telefono) {
		super();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.area = area;
		this.puesto = puesto;
		this.fechaIngreso = fechaIngreso;
		this.fechaNacimiento = fechaNacimiento;
		this.correoElectronico = correoElectronico;
		this.telefono = telefono;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCentroEstudios() {
		return centroEstudios;
	}

	public void setCentroEstudios(String centroEstudios) {
		this.centroEstudios = centroEstudios;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getPuestoID() {
		return puestoID;
	}

	public void setPuestoID(String puestoID) {
		this.puestoID = puestoID;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPaisID() {
		return paisID;
	}

	public void setPaisID(String paisID) {
		this.paisID = paisID;
	}

	public String getTipoDocumentoID() {
		return tipoDocumentoID;
	}

	public void setTipoDocumentoID(String tipoDocumentoID) {
		this.tipoDocumentoID = tipoDocumentoID;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getImagenColaborador() {
		return imagenColaborador;
	}

	public void setImagenColaborador(String imagenColaborador) {
		this.imagenColaborador = imagenColaborador;
	}

	public String getSueldo() {
		return Sueldo;
	}

	public void setSueldo(String sueldo) {
		Sueldo = sueldo;
	}

	public String getResumenEjecutivo() {
		return resumenEjecutivo;
	}

	public void setResumenEjecutivo(String resumenEjecutivo) {
		this.resumenEjecutivo = resumenEjecutivo;
	}

	public String getEstadoColaboradorID() {
		return estadoColaboradorID;
	}

	public void setEstadoColaboradorID(String estadoColaboradorID) {
		this.estadoColaboradorID = estadoColaboradorID;
	}

	public ArrayList<Objetivo> getSusObjetivos() {
		return susObjetivos;
	}

	public void setSusObjetivos(ArrayList<Objetivo> susObjetivos) {
		this.susObjetivos = susObjetivos;
	}
	
	@Override
	public String toString() {
		return nombres + Constante.ESPACIO_VACIO + apellidos;
	}

//	public String retornarPresentacionBreve() {
//		return nombres + " - " + puesto;
//	}
	
	public String retornarPresentacionBreve() {
		return nombres;
	}	


}