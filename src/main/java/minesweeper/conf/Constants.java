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
	 *
	 */
	public static PImage bomb, minefield;
	/**
	 * Size of a cell
	 */
	public static int CellSize;
	/**
	 * Location of png to first minefield
	 */
	public static String dataString;

	public static int deLay = 4000;

	public static int Horizontal;

	/**
	 *
	 */
	public static int intensity;

	/**
	 * ratio of initial picture
	 */
	public static float ratio;

	/**
	 * Restart flag
	 */
	public static boolean restart = false;

	/**
	 * dimensions of screen
	 */
	public static Dimension screenSize;

	public static int[] Sgst = new int[2];

	/**
	 * amount of cells along longest side of frame
	 */
	public static int Size;

	/**
	 *
	 */
	public static int Stuck;

	/**
	 *
	 */
	public static String success, BOMB, MINEFIELD;

	public static int Vertical;
	/**
	 *
	 */
	public static int xDefault = 0, yDefault = 0;

}
