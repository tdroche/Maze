package edu.wm.cs.cs301.thomasroche.ui;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import edu.wm.cs.cs301.thomasroche.R;
import edu.wm.cs.cs301.thomasroche.falstad.Constants;
import edu.wm.cs.cs301.thomasroche.falstad.Distance;
import edu.wm.cs.cs301.thomasroche.falstad.Maze;
import edu.wm.cs.cs301.thomasroche.falstad.MazeFileReader;
import edu.wm.cs.cs301.thomasroche.falstad.MazeFileWriter;
import edu.wm.cs.cs301.thomasroche.falstad.StateHolder;

/**
 * Class that deals with the generating state within the maze application.
 * Created by troche on 11/10/2015.
 */

public class GeneratingActivity extends Activity {
	String algorithm;
	String generateMaze;
	int skillLevel;
	private ProgressDialog progressDialog;
	private Spinner driverSelection;
	private CheckBox showTop;
	private CheckBox showWalls;
	private CheckBox showExit;
	Maze maze;
	boolean storeMaze = false;
	MazeFileReader mazeFileReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generating);

		// Create driver selection spinner values.

		driverSelection = (Spinner) findViewById(R.id.driverSelection);
		driverSelection.setOnItemSelectedListener(new DriverSelectionActivity());

		ArrayAdapter<CharSequence>	adapter = ArrayAdapter.createFromResource(this, R.array.driverSelection, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		driverSelection.setAdapter(adapter);

		// Store any passed data from previous activity.

		algorithm = getIntent().getStringExtra("algorithm");
		generateMaze = getIntent().getStringExtra("generateMaze");
		skillLevel = getIntent().getIntExtra("skillLevel", 3);
		StateHolder.mostRecentSkill = skillLevel;
		StateHolder.stepsTaken = 0;
		StateHolder.energyConsumed = 0;

		showTop = (CheckBox) findViewById(R.id.showTop);
		showWalls = (CheckBox) findViewById(R.id.walls);
		showExit = (CheckBox) findViewById(R.id.showExit);

		// Get reference to widgets.

		progressDialog = new ProgressDialog(findViewById(android.R.id.content).getContext());

		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading maze...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setMax(100);
		progressDialog.show();

		new GenerateMaze().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.generating, menu);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()== R.id.action_play) {
			Log.v(getClass().getName(), "Starting the maze");

			Intent intent = new Intent(this, PlayActivity.class);

			intent.putExtra("driverSelection", driverSelection.getSelectedItem().toString());
			intent.putExtra("showTop", showTop.isChecked());
			intent.putExtra("showWalls", showWalls.isChecked());
			intent.putExtra("showExit", showExit.isChecked());
			intent.putExtra("skillLevel", skillLevel);
			intent.putExtra("maze", maze);

			startActivity(intent);
			return true;
		}
		else if (item.getItemId()== android.R.id.home) {
			Log.v(getClass().getName(), "Home button clicked - starting over");
			startActivity(new Intent(this, AmazeActivity.class));
			return true;
		}
		else
			return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Log.v(getClass().getName(), "Back button pressed");
			maze.mazebuilder.interrupt();
			startActivity(new Intent(this, AmazeActivity.class));
		}

		return super.onKeyDown(keyCode, event);
	}

	class GenerateMaze extends AsyncTask<Integer, Void , Void> {
		@Override
		protected Void doInBackground(Integer...params) {
			// Generate the maze from a loaded file.

			publishProgress();

			if (generateMaze.equals("false")) {
				InputStream inputStream;

				try {
					publishProgress();
					inputStream = openFileInput("maze" + skillLevel + ".xml");

					Log.v(getClass().getName(), "Load the correct maze from the file");
					publishProgress();
					Log.v(getClass().getName(), "Skill level: " + skillLevel + 1);

					mazeFileReader = new MazeFileReader(inputStream);

					publishProgress();
					maze = new Maze();
					maze.init();
					publishProgress();
					maze.setMazeh(mazeFileReader.getHeight());
					maze.setMazew(mazeFileReader.getWidth());
					publishProgress();

					Distance d = new Distance(mazeFileReader.getDistances());
					publishProgress();

					maze.newMaze(mazeFileReader.getRootNode(), mazeFileReader.getCells(), d, mazeFileReader.getStartX(), mazeFileReader.getStartY());
					publishProgress();
				}
				catch (FileNotFoundException fnfe) {
					Log.v(getClass().getName(), "Load file not found - generate a maze file");
					generateMaze = "true";
					storeMaze = true;
				}
			}

			// Generate the maze will happen if loading a maze failed or the user selected to generate a maze.

			if (generateMaze.equals("true")) {
				// Generate the random maze.

				publishProgress();

				if (StateHolder.mazeSkill[skillLevel] != null)
					maze = StateHolder.mazeSkill[skillLevel];
				else {
					int method = 0; // Use default.

					if (algorithm.equals("Prim"))
						method = 1;
					else if (algorithm.equals("Eller"))
						method = 2;

					publishProgress();

					// Generate the maze using the correct builder. Adjust settings and launch generation in a separate thread.

					maze = new Maze(method);
					publishProgress();
					maze.build(skillLevel);
					publishProgress();
					StateHolder.mazeSkill[skillLevel] = maze;
					publishProgress();
				}

				try {
					maze.mazebuilder.buildThread.join();
					publishProgress();
					Log.v(getClass().getName(), "Maze build thread has completed.");

					if (storeMaze) {
						Log.v(getClass().getName(), "Store maze to a file.");
						MazeFileWriter.store("maze" + skillLevel + ".xml", maze.getMazew(), maze.getMazeh(),
								Constants.SKILL_ROOMS[skillLevel], Constants.SKILL_PARTCT[skillLevel], maze.getRoot(),
								maze.getMazecells(), maze.getMazedists().getDists(), maze.getStartX(), maze.getStartY());
					}
				}
				catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}

			while (progressDialog.getProgress() < 100)
				publishProgress();

			return null;
		}

		@Override
		protected void onProgressUpdate(Void... unused) {
			progressDialog.incrementProgressBy(10);
		}

		@Override
		protected void onPostExecute(Void unused) {
			progressDialog.dismiss();
		}
	}
}
