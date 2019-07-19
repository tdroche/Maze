package edu.wm.cs.cs301.thomasroche.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.wm.cs.cs301.thomasroche.R;

/**
 * Class that deals with the algorithm activity within the maze application.
 * Created by troche on 11/10/2015.
 */

public class AlgorithmActivity extends Activity implements OnItemSelectedListener {
	static String algorithm;

	/**
	 * Alerts the user when a change has been made to the maze algorithm selection.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.algorithm) {
			algorithm = (String) parent.getItemAtPosition(position);

			String msg = "Algorithm changed to " + algorithm;

			Log.v("Algorithm Activity", msg);
			Toast.makeText(parent.getContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}
}
