package edu.wm.cs.cs301.thomasroche.falstad;

public class StateHolder {
	public static GraphicsWrapper wrapper;
	public static Maze completedMaze;
	public static Maze[] mazeSkill = new Maze[15];
	public static int mostRecentSkill;
	public static int stepsTaken;
	public static int[] successes = new int[15];
	public static int[] shortestPath = new int[15];
	public static int energyConsumed;
	public static int[] leastEnergyConsumed = new int[15];
}
