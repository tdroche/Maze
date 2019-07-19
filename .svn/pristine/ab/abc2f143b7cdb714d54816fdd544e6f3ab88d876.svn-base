package edu.wm.cs.cs301.thomasroche.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
// import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import edu.wm.cs.cs301.thomasroche.R;
import edu.wm.cs.cs301.thomasroche.falstad.BasicRobot;
import edu.wm.cs.cs301.thomasroche.falstad.CuriousMouse;
import edu.wm.cs.cs301.thomasroche.falstad.GraphicsWrapper;
import edu.wm.cs.cs301.thomasroche.falstad.HitObstacleException;
import edu.wm.cs.cs301.thomasroche.falstad.ManualDriver;
import edu.wm.cs.cs301.thomasroche.falstad.Maze;
import edu.wm.cs.cs301.thomasroche.falstad.Robot;
import edu.wm.cs.cs301.thomasroche.falstad.Robot.Turn;
import edu.wm.cs.cs301.thomasroche.falstad.RobotDriver;
import edu.wm.cs.cs301.thomasroche.falstad.RobotOutOfEnergyException;
import edu.wm.cs.cs301.thomasroche.falstad.RobotStoppedException;
import edu.wm.cs.cs301.thomasroche.falstad.StateHolder;
import edu.wm.cs.cs301.thomasroche.falstad.WallFollower;
import edu.wm.cs.cs301.thomasroche.falstad.Wizard;

/**
 * Class that deals with the play state within the maze application.
 * Created by troche on 11/10/2015.
 */

@SuppressWarnings("unused")
public class PlayActivity extends Activity {
	public static ProgressBar energy;
	private Maze maze;
	private GraphicsWrapper wrapper;

	private String driverSelection;
	private RadioButton map;
	private RadioButton solution;
	private RadioButton walls;
	private int skillLevel;

	public RobotDriver robotDriver;
	public BasicRobot robot;
	private int battery = 2500;

	private RobotTask robotTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		// Initialize battery display.

		energy = (ProgressBar) findViewById(R.id.energy);
		energy.setMax(battery);
		energy.setProgress(battery);

		// Get intent extras.

		maze = (Maze) getIntent().getExtras().get("maze");
		// wrapper = new GraphicsWrapper(this, maze);
		// maze.setGraphicsWrapper(wrapper);

		// setContentView(wrapper);
		// context = getApplicationContext();

		driverSelection = getIntent().getStringExtra("driverSelection");

		map = (RadioButton) findViewById(R.id.map);
		solution = (RadioButton) findViewById(R.id.solution);
		walls = (RadioButton) findViewById(R.id.walls);

		map.setChecked(getIntent().getBooleanExtra("showTop", false));
		solution.setChecked(getIntent().getBooleanExtra("showExit", false));
		walls.setChecked(getIntent().getBooleanExtra("showWalls", false));
		skillLevel = getIntent().getIntExtra("skillLevel", 0);

		maze.setShowMaze(getIntent().getBooleanExtra("showTop", false));
		maze.setShowSolution(getIntent().getBooleanExtra("showWalls", false));
		maze.setSolving(getIntent().getBooleanExtra("showExit", false));

		// Create the view.

		StateHolder.wrapper = (GraphicsWrapper) findViewById(R.id.mazeDisplay);
		StateHolder.wrapper.setDisplay();

		// draw the view

		maze.setGraphicsWrapper(StateHolder.wrapper);
		maze.notifyViewerRedraw();

		if (driverSelection.equals("Manual")) {
			robot = new BasicRobot(maze);
			robotDriver = new ManualDriver();
		}
		else if (driverSelection.equals("Curious Mouse")) {
			robot = new BasicRobot(maze, true, true, true, true);
			robotDriver = new CuriousMouse();
		}
		else if (driverSelection.equals("Wall Follower")) {
			robot = new BasicRobot(maze, true, false, true, false);
			robotDriver = new WallFollower();
		}
		else if (driverSelection.equals("Wizard")) {
			robot = new BasicRobot(maze);
			robotDriver = new Wizard(maze.getMazedists());
		}

		robotDriver.setRobot(robot);
		maze.setBasicRobot(robot);
		maze.setRobotDriver(robotDriver);

		
		if (!driverSelection.equals("Manual"))
			new RobotTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.play, menu);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
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
			Log.v(getClass().getName(), "Stopping the robot task");
			robotTask.cancel(true);
			startActivity(new Intent(this, AmazeActivity.class));
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onRadioButtonClicked(View view) {
		switch (view.getId()) {

		case R.id.map:

			if (((RadioButton) view).isChecked()) {
				Log.v(getClass().getName(), "Show walls button checked");

				maze.setMapMode(!maze.isInMapMode());
				maze.notifyViewerRedraw() ;
				StateHolder.wrapper.postInvalidate();
			}

			break;

		case R.id.solution:

			if (((RadioButton) view).isChecked()) {
				Log.v(getClass().getName(), "Show walls button checked");

				if (maze.isInMapMode()) {
					maze.setShowSolution(!maze.isInShowSolutionMode()); 		
					maze.notifyViewerRedraw() ;
					StateHolder.wrapper.postInvalidate();
				}
			}

			break;

		case R.id.walls:

			if (((RadioButton) view).isChecked())
				Log.v(getClass().getName(), "Show walls button checked");

			break;
		}
	}

	public void moveButtonClicked(View view) {
		switch(view.getId()) {

		case R.id.moveLeft:

			Log.v(getClass().getName(), "Moving robot LEFT");

			try {
				if (robotDriver.drive2Exit()) {
					Log.v("PlayActivity", "Robot task has finished.");

					Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

					intent.putExtra("message", "Success!");
					StateHolder.completedMaze = maze;
					intent.putExtra("skillLevel", skillLevel);
					PlayActivity.this.startActivity(intent);
				}
			}
			catch (HitObstacleException hoe) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has hit an obstacle!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotStoppedException rse) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has stopped!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			try {
				maze.getBasicRobot().rotate(Turn.LEFT);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			if (maze.getBasicRobot().hasStopped()) {
				Log.v(getClass().getName(), "Robot ran out of energy or hit obstacle");
				startActivity(new Intent(this, FinishActivity.class));
			}

			break;

		case R.id.moveForward:

			Log.v(getClass().getName(), "Moving robot FORWARD");

			try {
				if (robotDriver.drive2Exit()) {
					Log.v("PlayActivity", "Robot task has finished.");

					Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

					intent.putExtra("message", "Success!");
					//intent.putExtra("maze", maze);
					StateHolder.completedMaze = maze;
					intent.putExtra("skillLevel", skillLevel);
					PlayActivity.this.startActivity(intent);
				}
			}
			catch (HitObstacleException hoe) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has hit an obstacle!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotStoppedException rse) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has stopped!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			try {
				maze.getBasicRobot().move(1);
			}
			catch (HitObstacleException hoe) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has hit an obstacle!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			if (maze.getBasicRobot().hasStopped()) {
				Log.v(getClass().getName(), "Robot ran out of energy or hit obstacle");
				startActivity(new Intent(this, FinishActivity.class));
			}

			break;

		case R.id.moveRight:

			Log.v(getClass().getName(), "Moving robot RIGHT");

			try {
				if (robotDriver.drive2Exit()) {
					Log.v("PlayActivity", "Robot task has finished.");

					Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

					intent.putExtra("message", "Success!");
					//intent.putExtra("maze", maze);
					StateHolder.completedMaze = maze;
					intent.putExtra("skillLevel", skillLevel);
					PlayActivity.this.startActivity(intent);
				}
			}
			catch (HitObstacleException hoe) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has hit an obstacle!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotStoppedException rse) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has stopped!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			try {
				maze.getBasicRobot().rotate(Turn.RIGHT);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			if (maze.getBasicRobot().hasStopped()) {
				Log.v(getClass().getName(), "Robot ran out of energy or hit obstacle");
				startActivity(new Intent(this, FinishActivity.class));
			}

			break;

		case R.id.rotate:

			Log.v(getClass().getName(), "Moving robot ROTATE");

			try {
				if (robotDriver.drive2Exit()) {
					Log.v("PlayActivity", "Robot task has finished.");

					Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

					intent.putExtra("message", "Success!");
					//intent.putExtra("maze", maze);
					StateHolder.completedMaze = maze;
					intent.putExtra("skillLevel", skillLevel);
					PlayActivity.this.startActivity(intent);
				}
			}
			catch (HitObstacleException hoe) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has hit an obstacle!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}
			catch (RobotStoppedException rse) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has stopped!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			try {
				maze.getBasicRobot().rotate(Turn.AROUND);
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v("PlayActivity", "Robot task has finished.");

				Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

				intent.putExtra("message", "Robot has run out of energy!");
				StateHolder.completedMaze = maze;
				intent.putExtra("skillLevel", skillLevel);
				PlayActivity.this.startActivity(intent);
			}

			if (maze.getBasicRobot().hasStopped()) {
				Log.v(getClass().getName(), "Robot ran out of energy or hit obstacle");
				startActivity(new Intent(this, FinishActivity.class));
			}

			break;
		}
	}

	class RobotTask extends AsyncTask<Integer , Void , Integer> {
		private int SUCCESS = 0;
		private int HIT_OBSTACLE = 1;
		private int ROBOT_OUT_OF_ENERGY = 2;
		private int ROBOT_STOPPED = 3;
		private String[] message = { "Success!", "Robot has hit an obstacle!", "Robot has run out of energy!", "Robot has stopped!"};

		@Override
		protected Integer doInBackground(Integer... params) {
			Integer returnCode = new Integer(SUCCESS);

			try {
				if (robotDriver.drive2Exit())
					returnCode = SUCCESS;
			}
			catch (HitObstacleException hoe) {
				Log.v(getClass().getName(), "Robot hit an obstacle");
				returnCode = HIT_OBSTACLE;
			}
			catch (RobotOutOfEnergyException rooee) {
				Log.v(getClass().getName(), "Robot ran out of energy");
				returnCode = ROBOT_OUT_OF_ENERGY;
			}
			catch (RobotStoppedException rse) {
				Log.v(getClass().getName(), "Robot stopped");
				returnCode = ROBOT_STOPPED;
			}

			return returnCode;
		}

		protected void onProgressUpdate(Integer... progress) {
			energy.incrementProgressBy(-progress[0]);
		}

		protected void onPostExecute(Integer returnCode) {
			Log.v("PlayActivity", "Robot task has finished.");

			Intent intent = new Intent(PlayActivity.this,  FinishActivity.class);

			intent.putExtra("message", message[returnCode.intValue()]);
			//intent.putExtra("maze", maze);
			StateHolder.completedMaze = maze;
			intent.putExtra("skillLevel", skillLevel);
			PlayActivity.this.startActivity(intent);
		}
	}
}
