package edu.wm.cs.cs301.thomasroche.falstad;

import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A segment is a continuous sequence of walls in the maze.
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 */
public class Seg implements Serializable {
	private static final long serialVersionUID = -3361533188862952495L;

	private int x, y, dx, dy, dist;
	private int[] col;
	private boolean partition, seen;

	/**
	 * Constructor
	 * @param psx
	 * @param psy
	 * @param pdx
	 * @param pdy
	 * @param cl
	 * @param cc
	 */
	Seg(int psx, int psy, int pdx, int pdy, int cl, int cc) {
		x = psx;
		y = psy;
		dx = pdx;
		dy = pdy;
		dist = cl;
		col = new int[3];
		seen = false;
		cl /= 4;
		int add = (dx != 0) ? 1 : 0;
		int part1 = cl & 7;
		int part2 = ((cl >> 3) ^ cc) % 6;
		int val1 = ((part1 + 2 + add) * 70)/8 + 80;
		switch (part2) {
		case 0:
			col[0] = val1;
			col[1] = 20;
			col[2] = 20;
			break;
		case 1:
			col[0] = 20;
			col[1] = val1;
			col[2] = 20;
			break;
		case 2:
			col[0] = 20;
			col[1] = 20;
			col[2] = val1;
			break;
		case 3:
			col[0] = val1;
			col[1] = val1;
			col[2] = 20;
			break;
		case 4:
			col[0] = 20;
			col[1] = val1;
			col[2] = val1;
			break;
		case 5:
			col[0] = val1;
			col[1] = 20;
			col[2] = val1;
			break;
		default:
			col[0] = 20;
			col[1] = 20;
			col[2] = 20;
			break;
		}
	}

	int getDir() {
		if (dx != 0)
			return (dx < 0) ? 1 : -1;
		return (dy < 0) ? 2 : -2;
	}
	
	void storeSeg(Document doc, Element mazeXML, int number, int i) {
		MazeFileWriter.appendChild(doc, mazeXML, "distSeg_" + number+ "_" + i, dist) ;
		MazeFileWriter.appendChild(doc, mazeXML, "dxSeg_" + number+ "_" + i, dx) ;
		MazeFileWriter.appendChild(doc, mazeXML, "dySeg_" + number+ "_" + i, dy) ;
		MazeFileWriter.appendChild(doc, mazeXML, "partitionSeg_" + number+ "_" + i, partition) ;
		MazeFileWriter.appendChild(doc, mazeXML, "seenSeg_" + number+ "_" + i, seen) ;
		MazeFileWriter.appendChild(doc, mazeXML, "xSeg_" + number+ "_" + i, x) ;
		MazeFileWriter.appendChild(doc, mazeXML, "ySeg_" + number+ "_" + i, y) ;
		MazeFileWriter.appendChild(doc, mazeXML, "colSeg_" + number+ "_" + i, GraphicsWrapper.getRGB(col)) ;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDx() {
		return dx;
	}
	
	public int getDy() {
		return dy;
	}
	
	public int getDist() {
		return dist;
	}

	public void setCol(int[] col) {
		this.col = col;
	}

	public int[] getCol() {
		return col;
	}

	public void setPartition(boolean partition) {
		this.partition = partition;
	}

	public boolean getPartition() {
		return partition;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean getSeen() {
		return seen;
	}
}
