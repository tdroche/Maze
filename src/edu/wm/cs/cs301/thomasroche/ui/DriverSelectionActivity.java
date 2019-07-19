package edu.wm.cs.cs301.thomasroche.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.wm.cs.cs301.thomasroche.R;
import android.widget.Toast;

/**
 * Class that deals with the driver selection activity within the maze application.
 * Created by troche on 11/11/2015.
 */

public class DriverSelectionActivity extends Activity implements OnItemSelectedListener {
	static String driverSelection;

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.driverSelection) {
			driverSelection = (String) parent.getItemAtPosition(position);

			String msg = "Driver selection changed to " + driverSelection;

			Log.v("DriverSelection Activity", msg);
			Toast.makeText(parent.getContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}
}
