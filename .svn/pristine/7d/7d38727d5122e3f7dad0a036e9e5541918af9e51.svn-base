package edu.wm.cs.cs301.thomasroche.falstad;

/**
 * Class that implements the WallFollower Robot driver algorithm.
 * The robot picks one side (here, the left), and and follows the wall on that side until
 * a solution to the maze is found. It rotates when necessary and continues until it either
 * finds the exit or encounters an error (i.e. runs out of energy or hits an obstacle).
 * 
 * @author troche
 */
public class WallFollower extends ManualDriver {
	public WallFollower() {
	}

	@Override
	/**
	 * Drives the robot to an exit using the Robot driver algorithm.
	 * @param robot to operate
	 */
	public boolean drive2Exit() throws HitObstacleException, RobotOutOfEnergyException {
		while (true) {
			try {
				// check if the goal can be sensed in front of or to the left of the robot
				if (robot.canSeeGoal(Robot.Direction.FORWARD)) {
					while (!robot.isAtGoal())
						robot.move(1);

					return true;
				}

				// no wall left and no wall forward, or no wall left and wall forward, so rotate left
				if (robot.distanceToObstacle(Robot.Direction.LEFT) != 0) {
					robot.rotate(Robot.Turn.LEFT);
					robot.move(1);
				}
				
				else if ((robot.distanceToObstacle(Robot.Direction.LEFT) == 0) && (robot.distanceToObstacle(Robot.Direction.FORWARD) != 0))
					robot.move(1);
				
				else if ((robot.distanceToObstacle(Robot.Direction.LEFT) == 0) && (robot.distanceToObstacle(Robot.Direction.FORWARD) == 0))
					robot.rotate(Robot.Turn.RIGHT);
			}
			catch (HitObstacleException hoe) {
				System.out.println(hoe.getMessage());
				throw new HitObstacleException();
			}
			catch (RobotOutOfEnergyException rooee) {
				System.out.println(rooee.getMessage());
				throw new RobotOutOfEnergyException();
			}
		}
	}
}
