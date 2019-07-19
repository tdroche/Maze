package edu.wm.cs.cs301.thomasroche.falstad;

import edu.wm.cs.cs301.thomasroche.falstad.Robot.Turn;

/**
 * This class implements the RobotDriver interface in RobotDriver.java. It operates on a
 * robot to escape from a given maze.
 * 
 * @author troche
 */
public class ManualDriver implements RobotDriver {
	protected Robot robot;
	
	@Override
	/**
	 * Assigns a robot platform to the driver.
	 * @param robot to operate
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	@Override
	/**
	 * Provides the robot driver with information on the dimensions of the maze.
	 * @param width of the maze
	 * @param height of the maze
	 * @precondition 0 <= width, 0 <= height of the maze
	 */
	public void setDimensions(int width, int height) {
	}

	@Override
	/**
	 * Provides the robot driver with information on the distance to the exit.
	 * @param distance to exit
	 * @precondition distance != null
	 */
	public void setDistance(Distance distance) {
	}

	@Override
	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to some problem, e.g. lack of energy
	 */
	public boolean drive2Exit() throws RobotStoppedException, HitObstacleException, RobotOutOfEnergyException {
		if (robot.hasStopped())
			throw new RobotStoppedException();

		if (robot.isAtGoal())
			return true;

		return false;
	}

	@Override
	/**
	 * Returns the total energy consumption of the journey.
	 * @return total energy consumption
	 */
	public float getEnergyConsumption() {
		return BasicRobot.INITIAL_BATTERY_LEVEL - robot.getBatteryLevel();
	}

	@Override
	/**
	 * Returns the total length of the journey in number of cells traversed.
	 * @return path length
	 */
	public int getPathLength() {
		return ((BasicRobot) robot).getPathLength();
	}

	/**
	 * Method that checks if the robot can see the goal in any direction. If it can, it
	 * drives to the goal.
	 * @return boolean true if can see exit
	 * @throws RobotOutOfEnergyException 
	 * @throws HitObstacleException 
	 */
	public boolean checkIfCanSeeExit() throws RobotOutOfEnergyException, HitObstacleException {
		// Check whether you can see the goal in all directions.

		boolean	goForward = robot.canSeeGoal(Robot.Direction.FORWARD),
				goLeft = robot.canSeeGoal(Robot.Direction.LEFT),
				goRight = robot.canSeeGoal(Robot.Direction.RIGHT),
				goBack = robot.canSeeGoal(Robot.Direction.BACKWARD);

		if (goForward || goLeft || goRight || goBack) {
			if (goLeft)
				robot.rotate(Turn.LEFT);
			else if (goRight)
				robot.rotate(Turn.RIGHT);
			else if (goBack) {
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}

			while (!robot.isAtGoal()) {
				try {
					robot.move(1);
				}
				catch (HitObstacleException hoe) {
					System.out.println(hoe.getMessage());
					throw new HitObstacleException();
				}
			}

			return true;
		}

		return false;
	}
}
