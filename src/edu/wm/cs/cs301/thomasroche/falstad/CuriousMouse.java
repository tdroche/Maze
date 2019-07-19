package edu.wm.cs.cs301.thomasroche.falstad;

import java.util.Random;

import edu.wm.cs.cs301.thomasroche.falstad.Robot.Direction;
import edu.wm.cs.cs301.thomasroche.falstad.Robot.Turn;

/**
 * Class that implements the CuriousMouse Robot driver algorithm.
 * The mouse retains a memory of previous cells, checking its options using distance sensors
 * and randomly picking a direction, rotating if necessary. It repeats these steps until a
 * solution to the maze is found.
 * 
 * @author troche
 */
public class CuriousMouse extends ManualDriver {
	public CuriousMouse() {
	}

	/**
	 * Drives the robot to an exit using the Robot driver algorithm.
	 */
	public boolean drive2Exit() throws RobotStoppedException, HitObstacleException, RobotOutOfEnergyException {
		// Set all cells to not visited (1) before starting.

		for (int w = 0; w < robot.getMaze().getMazew(); w++) {
			for (int h = 0; h < robot.getMaze().getMazeh(); h++) {
				robot.getMaze().getMazecells().setBitToOne(w, h, Constants.CW_VISITED);
			}
		}

		while (true) {
			if (checkIfCanSeeExit())
				return true;

			int[] dists = { robot.distanceToObstacle(Robot.Direction.FORWARD), robot.distanceToObstacle(Robot.Direction.LEFT),
							robot.distanceToObstacle(Robot.Direction.RIGHT), robot.distanceToObstacle(Robot.Direction.BACKWARD) };
			boolean[] isFirstVisit = { false, false, false, false };
			int[] currentPosition = new int[2];
			int[] newDirection = new int[2];

			try {
				currentPosition = robot.getCurrentPosition();
			}
			catch (OutOfMazeException oome) {
				oome.printStackTrace();
				throw new RobotStoppedException();
			}

			if (dists[0] > 0) {
				newDirection = ((BasicRobot) robot).getNewDirection(robot.getCurrentDirection(), Direction.FORWARD);
				isFirstVisit[0] = robot.getMaze().getMazecells().isFirstVisit(currentPosition[0] + newDirection[0], currentPosition[1] + newDirection[1]);
			}

			if (dists[1] > 0) {
				newDirection = ((BasicRobot) robot).getNewDirection(robot.getCurrentDirection(), Direction.LEFT);
				isFirstVisit[1] = robot.getMaze().getMazecells().isFirstVisit(currentPosition[0] + newDirection[0], currentPosition[1] + newDirection[1]);
			}

			if (dists[2] > 0) {
				newDirection = ((BasicRobot) robot).getNewDirection(robot.getCurrentDirection(), Direction.RIGHT);
				isFirstVisit[2] = robot.getMaze().getMazecells().isFirstVisit(currentPosition[0] + newDirection[0], currentPosition[1] + newDirection[1]);
			}

			if (dists[3] > 0) {
				newDirection = ((BasicRobot) robot).getNewDirection(robot.getCurrentDirection(), Direction.BACKWARD);
				isFirstVisit[3] = robot.getMaze().getMazecells().isFirstVisit(currentPosition[0] + newDirection[0], currentPosition[1] + newDirection[1]);
			}

			if (isFirstVisit[0] || isFirstVisit[1] || isFirstVisit[2] || isFirstVisit[3]) {
				// Randomly choose a cell that hasn't been visited.

				int	num;

				while (true) {
					num = new Random().nextInt(isFirstVisit.length);

					if (isFirstVisit[num] && (dists[num] > 0))
						break;
				}

				if (num == 1) {
					// Move left.
					robot.rotate(Turn.LEFT);
				}
				else if (num == 2) {
					// Move right.
					robot.rotate(Turn.RIGHT);
				}
				else if (num == 3) {
					// Move backward.
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
				}

				robot.move(1);
			}
			else {
				// Randomly choose a cell that has been visited.

				int	num;

				while (true) {
					num = new Random().nextInt(isFirstVisit.length);

					if (!isFirstVisit[num] && (dists[num] > 0))
						break;
				}

				if (num == 1) {
					// Move left.
					robot.rotate(Turn.LEFT);
				}
				else if (num == 2) {
					// Move right.
					robot.rotate(Turn.RIGHT);
				}
				else if (num == 3) {
					// Move backward.
					robot.rotate(Turn.RIGHT);
					robot.rotate(Turn.RIGHT);
				}

				robot.move(1);
			}
		}
	}
}
