package pe.edu.pucp.proyectorh.miinformacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import pe.edu.pucp.proyectorh.R;
import pe.edu.pucp.proyectorh.utils.CalendarAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AgendaFragment extends Fragment {

	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items;
	private View rootView;

	public AgendaFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// rootView = inflater.inflate(R.layout.mi_info_agenda,
		// container, false);
		rootView = inflater.inflate(R.layout.calendar, container, false);
		month = Calendar.getInstance();
		onNewIntent(getActivity().getIntent());

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(this.getActivity(), month);

		GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) rootView.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		TextView previous = (TextView) rootView.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMinimum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) - 1),
							month.getActualMaximum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
				}
				refreshCalendar();
			}
		});

		TextView next = (TextView) rootView.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (month.get(Calendar.MONTH) == month
						.getActualMaximum(Calendar.MONTH)) {
					month.set((month.get(Calendar.YEAR) + 1),
							month.getActualMinimum(Calendar.MONTH), 1);
				} else {
					month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
				}
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView date = (TextView) v.findViewById(R.id.date);
				if (date instanceof TextView && !date.getText().equals("")) {

					Intent intent = new Intent();
					String day = date.getText().toString();
					if (day.length() == 1) {
						day = "0" + day;
					}
					// return chosen date as string format
					intent.putExtra(
							"date",
							android.text.format.DateFormat.format("yyyy-MM",
									month) + "-" + day);
//					getActivity().setResult(RESULT_OK, intent);
					getActivity().setResult(0, intent);
					getActivity().finish();
				}

			}
		});
		llamarServicioEventos();
		return rootView;
	}

	public void refreshCalendar() {
		TextView title = (TextView) rootView.findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some random calendar items

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}

	public void onNewIntent(Intent intent) {
		intent.putExtra("date", "2013-5-20");
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
				Integer.parseInt(dateArr[2]));
	}

	public Runnable calendarUpdater = new Runnable() {

		@Override
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to
			// provide real values
			for (int i = 0; i < 31; i++) {
				Random r = new Random();

				if (r.nextInt(10) > 6) {
					items.add(Integer.toString(i));
				}
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};

	private void llamarServicioEventos() {
		// TODO cvasquez: llamar servicio para consultar eventos del usuario

	}

}
