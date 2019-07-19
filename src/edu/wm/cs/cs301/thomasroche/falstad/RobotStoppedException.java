package edu.wm.cs.cs301.thomasroche.falstad;

@SuppressWarnings("serial")
public class RobotStoppedException extends Exception {
	public RobotStoppedException() {
		super("Robot is stopped.");
	}
}
