package minesweeper.conf;

import java.awt.Dimension;

import processing.core.PImage;

/**
 * 
 * @author ro
 *
 */
public class Constants {

	/**
	 * dimensions of screen
	 */
	public static Dimension screenSize;
	public static int Horizontal;
	public static int Vertical;

	/**
	 * amount of cells along longest side of frame
	 */
	public static int Size;

	/**
	 * ratio of initial picture
	 */
	public static float ratio;

	/**
	 * Location of png to first minefield
	 */
	public static String dataString;

	/**
	 * Size of a cell
	 */
	public static int CellSize;

	/**
	 * 
	 */
	public static int Stuck;

	/**
	 * Restart flag
	 */
	public static boolean restart = false;

	/**
	 * 
	 */
	public static int xDefault = 0, yDefault = 0;

	/**
	 * 
	 */
	public static String success, BOMB, MINEFIELD;

	/**
	 * 
	 */
	public static PImage bomb, minefield;

	/**
	 * 
	 */
	public static int intensity;

	public static int deLay = 4000;
	public static int[] Sgst = new int[2];

}
