package pe.edu.pucp.proyectorh;

import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.proyectorh.model.Modulo;
import pe.edu.pucp.proyectorh.model.Modulo.ModuloItem;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Fragmento que contiene la lista de opciones
 * 
 * @author Cesar
 * 
 */
public class OpcionListFragment extends ListFragment {

	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks mCallbacks = menuOpcionesCallbacks;
	private int mActivatedPosition = ListView.INVALID_POSITION;

	public interface Callbacks {

		public void onItemSelected(String id);
	}

	private static Callbacks menuOpcionesCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	public OpcionListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<Modulo.ModuloItem>(getActivity(),
				R.layout.accordion_list, R.id.accordion_list, Modulo.MODULOS));
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = menuOpcionesCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		if (OpcionListActivity.NAVEGACION == 1) {
			OpcionListActivity.NAVEGACION = 2;
			List<ModuloItem> submodulos = new ArrayList<ModuloItem>();
			switch (((int) id) + 1) {
			case 1:
				submodulos = Modulo.obtenerFuncionalidadesMiInformacion();
				break;
			case 2:
				submodulos = Modulo.obtenerFuncionalidadesAdministracion();
				break;
			case 3:
				submodulos = Modulo.obtenerFuncionalidadesReclutamiento();
				break;
			case 4:
				submodulos = Modulo.obtenerFuncionalidadesEvaluacion360();
				break;
			case 5:
				submodulos = Modulo.obtenerFuncionalidadesObjetivos();
				break;
			case 6:
				submodulos = Modulo.obtenerFuncionalidadesReportes();
				break;
			}
			setListAdapter(new ArrayAdapter<Modulo.ModuloItem>(getActivity(),
					R.layout.accordion_list, R.id.accordion_list, submodulos));
			Modulo.MODULOS_MOSTRADOS_ACTUAL = submodulos;
		} else if (OpcionListActivity.NAVEGACION == 2) {
			mCallbacks.onItemSelected(Modulo.MODULOS_MOSTRADOS_ACTUAL
					.get(position).id);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	public void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}
