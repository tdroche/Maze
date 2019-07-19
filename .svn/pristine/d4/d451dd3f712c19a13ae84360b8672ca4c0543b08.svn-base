package edu.wm.cs.cs301.thomasroche.falstad;

/**
 * This class has the responsibility to create a maze of given dimensions (width, height) together with a solution based on a distance matrix.
 * The Maze class depends on it. The MazeBuilder performs its calculations within its own separate thread such that communication between 
 * Maze and MazeBuilder operates as follows. Maze calls the build() method and provides width and height. Maze has a call back method newMaze that
 * this class calls to communicate a new maze and a BSP root node and a solution.
 * 
 * The maze is built with a randomized version of Eller's algorithm.
 * 
 * @author troche
 */

public class MazeBuilderEller extends MazeBuilder {
	private int column;
	private int row;
	private int[][] cellSet;

	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}

	/**
	 * Constructor with option to make maze generation deterministic or random.
	 */
	public MazeBuilderEller(boolean deterministic) {
		super(deterministic);
	}

	@Override
	/**
	 * Method to generate pathways in the maze. Calls on separate methods createXPaths() and createYPaths() to
	 * generate horizontally across rows or vertically across columns.
	 */
	protected void generatePathways() {
		// Next set for next row.

		int nextSet = width;

		// Initialize the first row and add it to its own set.

		row = 0;
		column = 0;
		cellSet = new int[width][height];
	
		for (int i = 0; i < width; i++)
			cellSet[i][0] = i;

		while (row != height) {
			// Copy the top row. If a cell has a wall above it, put it in its own set.

			for (int i = 0; i < width; i++) {
				if (cells.hasWallOnTop(i, row))
					cellSet[i][row] = nextSet++;
				else
					cellSet[i][row] = cellSet[i][row - 1];
			}

			createXPaths();
			column = 0;
			createYPaths();

			row++;
			column = 0;
		}
	}

	private void createXPaths() {
		int randInt = 0;

		// Create x-paths (horizontally).

		while (column != width) {
			// Check for the last row.

			if (row == (height - 1)) {
				for (int i = 0; i < (width - 1); i++) {
					if (cellSet[i][row] != cellSet[i + 1][row])
						cells.deleteWall(i, row, 1, 0);
				}

				break;
			}

			if (column != (width - 1)) {
				if (cellSet[column][row] != cellSet[column + 1][row])
					randInt = random.nextIntWithinInterval(0, 1);
				else {
					column++;
					continue;
				}
			}
			else {
				column++;
				continue;
			}

			// Delete the wall.

			if (randInt == 0) {
				cells.deleteWall(column, row, 1, 0);
				cellSet[column + 1][row] = cellSet[column][row];
			}
			else
				column++;
		}
	}

	private void createYPaths() {
		// Create y-paths (vertically).

		while (column != width) {
			if (row == (height - 1))
				break;

			int startCol = column;

			if (column == (width - 1)) {
				// Only gets here if last column is not in set with second to last.

				cells.deleteWall(column, row, 0, 1);
				break;
			}

			while (true) {
				if (column == (width - 1))
					break;

				if (cellSet[column][row] == cellSet[column + 1][row]) {
					column++;
					continue;
				}
				else
					break;
			}

			// Delete a random wall from the bottom set.

			int randInt = random.nextIntWithinInterval(startCol, column);

			cells.deleteWall(randInt, row, 0, 1);
			column++;
		}

	}
}
