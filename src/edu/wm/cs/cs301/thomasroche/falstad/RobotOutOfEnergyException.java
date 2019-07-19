package edu.wm.cs.cs301.thomasroche.falstad;

@SuppressWarnings("serial")
public class RobotOutOfEnergyException extends Exception {
	public RobotOutOfEnergyException() {
		super("Robot is out of energy.");
	}
}
