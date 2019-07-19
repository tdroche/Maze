package edu.wm.cs.cs301.thomasroche.falstad;

@SuppressWarnings("serial")
public class HitObstacleException extends Exception {
	public HitObstacleException() {
		super("Robot has hit an obstacle.");
	}
}
