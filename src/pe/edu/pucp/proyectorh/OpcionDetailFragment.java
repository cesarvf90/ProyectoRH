package pe.edu.pucp.proyectorh;

import pe.edu.pucp.proyectorh.model.Modulo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragmento de contenido de las funcionalidades
 * 
 * @author Cesar
 * 
 */
public class OpcionDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";

	Modulo.ModuloItem mItem;

	public OpcionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			mItem = Modulo.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_opcion_detail,
				container, false);
		if (mItem != null) {

			// ((TextView) rootView.findViewById(R.id.opcion_detail))
			// .setText(mItem.content);
			System.out.println("mIten="+mItem);			
			((TextView) rootView.findViewById(R.id.opcion_detail))
					.setText(mItem.nombre);
		}
		return rootView;
	}
}
