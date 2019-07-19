package edu.wm.cs.cs301.thomasroche.falstad;

import edu.wm.cs.cs301.thomasroche.falstad.Robot.Turn;

/**
 * Class that implements the Wizard Robot driver algorithm.
 * The wizard is a cheater that knows the distance matrix of the maze cells and
 * uses it to find the solution to the maze. (In this way, it is a baseline
 * algorithm, and the most efficient in terms of energy consumed and path length.)
 * 
 * @author troche
 */
public class Wizard extends ManualDriver {
	private int[][] distsToGoal;
	private int dimensions;

	public Wizard(Distance dists) {
		distsToGoal = dists.getDists();
	}

	/**
	 * Drives the robot to an exit using the Robot driver algorithm.
	 */
	public boolean drive2Exit() throws RobotStoppedException, HitObstacleException, RobotOutOfEnergyException {
		while (true) {
			// FORWARD, LEFT, RIGHT, BACKWARD
			dimensions = (((robot.getMaze().getMazew()) * (robot.getMaze().getMazeh())) + 1);
			int[] cellDistance = { dimensions, dimensions, dimensions, dimensions };

			if (checkIfCanSeeExit())
				return true;

			int[] distsToObstacle = { robot.distanceToObstacle(Robot.Direction.FORWARD), robot.distanceToObstacle(Robot.Direction.LEFT),
									robot.distanceToObstacle(Robot.Direction.RIGHT), robot.distanceToObstacle(Robot.Direction.BACKWARD) };
			int[] currentPosition = new int[2];
			int[] currentDirection = new int[2];

			try {
				currentPosition = robot.getCurrentPosition();
				currentDirection = robot.getCurrentDirection();
			}
			catch (OutOfMazeException oome) {
				oome.printStackTrace();
				throw new RobotStoppedException();
			}

			// assign each surrounding cell's distance to the goal as a variable if there
			// is not an obstacle there
			if (distsToObstacle[0] > 0) {
				// FORWARD
				if ((currentDirection[0] == 1) && (currentDirection[1] == 0))
					cellDistance[0] = distsToGoal[currentPosition[0] + 1][currentPosition[1]];

				if ((currentDirection[0] == 0) && (currentDirection[1] == 1))
					cellDistance[0] = distsToGoal[currentPosition[0]][currentPosition[1] + 1];

				if ((currentDirection[0] == -1) && (currentDirection[1] == 0))
					cellDistance[0] = distsToGoal[currentPosition[0] - 1][currentPosition[1]];

				if ((currentDirection[0] == 0) && (currentDirection[1] == -1))
					cellDistance[0] = distsToGoal[currentPosition[0]][currentPosition[1] - 1];
			}

			if (distsToObstacle[1] > 0) {
				// LEFT
				if ((currentDirection[0] == 1) && (currentDirection[1] == 0))
					cellDistance[1] = distsToGoal[currentPosition[0]][(currentPosition[1] + 1)];

				if ((currentDirection[0] == 0) && (currentDirection[1] == 1))
					cellDistance[1] = distsToGoal[(currentPosition[0] - 1)][currentPosition[1]];

				if ((currentDirection[0] == -1) && (currentDirection[1] == 0))
					cellDistance[1] = distsToGoal[currentPosition[0]][(currentPosition[1] - 1)];

				if ((currentDirection[0] == 0) && (currentDirection[1] == -1))
					cellDistance[1] = distsToGoal[(currentPosition[0] + 1)][currentPosition[1]];
			}

			if (distsToObstacle[2] > 0) {
				// RIGHT
				if ((currentDirection[0] == 1) && (currentDirection[1] == 0))
					cellDistance[2] = distsToGoal[currentPosition[0]][(currentPosition[1] - 1)];

				if ((currentDirection[0] == 0) && (currentDirection[1] == 1))
					cellDistance[2] = distsToGoal[(currentPosition[0] + 1)][currentPosition[1]];

				if ((currentDirection[0] == -1) && (currentDirection[1] == 0))
					cellDistance[2] = distsToGoal[currentPosition[0]][(currentPosition[1] + 1)];

				if ((currentDirection[0] == 0) && (currentDirection[1] == -1))
					cellDistance[2] = distsToGoal[(currentPosition[0] - 1)][currentPosition[1]];
			}

			if (distsToObstacle[3] > 0) {
				// BACKWARD
				if ((currentDirection[0] == 1) && (currentDirection[1] == 0))
					cellDistance[3] = distsToGoal[(currentPosition[0] - 1)][currentPosition[1]];

				if ((currentDirection[0] == 0) && (currentDirection[1] == 1))
					cellDistance[3] = distsToGoal[currentPosition[0]][(currentPosition[1] - 1)];

				if ((currentDirection[0] == -1) && (currentDirection[1] == 0))
					cellDistance[3] = distsToGoal[(currentPosition[0] + 1)][currentPosition[1]];

				if ((currentDirection[0] == 0) && (currentDirection[1] == -1))
					cellDistance[3] = distsToGoal[currentPosition[0]][(currentPosition[1] + 1)];
			}

			int i = 0;

			if (cellDistance[1] < cellDistance[i])
				i = 1;

			if (cellDistance[2] < cellDistance[i])
				i = 2;

			if (cellDistance[3] < cellDistance[i])
				i = 3;

			if (i == 1)
				robot.rotate(Turn.LEFT);
			else if (i == 2)
				robot.rotate(Turn.RIGHT);
			else if (i == 3) {
				robot.rotate(Turn.LEFT);
				robot.rotate(Turn.LEFT);
			}

			robot.move(1);
		}
	}
}
