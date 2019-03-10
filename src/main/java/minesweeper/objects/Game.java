package minesweeper.objects;

import minesweeper.conf.Constants;
import minesweeper.interfaces.IDrawable;
import minesweeper.interfaces.IGui;
import minesweeper.main.Gui;

/**
 * 
 * @author ro
 *
 *
 * 
 */

public class Game implements IDrawable {

	/**
	 * (m,n)-matrix of cells
	 */
	private Cell[][] match;

	/**
	 * Indicator for open game
	 */
	private boolean running = true;

	/**
	 * Constructor using dimension parameters
	 * 
	 * @param dimY Height of the game field
	 * @param dimX Width of the game field
	 */
	public Game(int dimY, int dimX) {
		this.match = new Cell[dimY][dimX];
		for (int i = 0; i < dimX; i++) {
			for (int j = 0; j < dimY; j++) {
				this.match[j][i] = new Cell();
			}
		}
	}

	@Override
	public void draw(Gui gui) {
		for (int m = 0; m < gui.getGame().match.length; m++) {
			for (int n = 0; n < gui.getGame().match[0].length; n++) {
				this.getMatch()[m][n].draw(gui);
			}
		}
	}

	/**
	 * Transformator for coordintes 
	 * @param cell the cell
	 * @return the coordinates
	 */
	public int[] getCoordinates(Cell cell) {
		final Integer[] n = new Integer[2];
		n[0] = 0;
		n[1] = 0;
		for (final Cell[] row : this.getMatch()) {
			for (final Cell tmp : row) {
				if (tmp == cell) {
					final int[] ans = new int[2];
					ans[0] = n[0];
					ans[1] = n[1];
					return ans;
				}
				n[1] += 1;
			}
			n[0] += 1;
			n[1] = 0;
		}
		throw new NullPointerException("da basst was nedda");
	}

	/**
	 * Getter for match
	 * 
	 * @return match the game
	 */
	public Cell[][] getMatch() {
		return this.match;
	}

	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Function for click of left mouse button
	 * 
	 * @param gui the gui object
	 * @param m y-position of cell
	 * @param n x-position of cell
	 * @return completion for final feedback
	 */
	public String leftClicked(Gui gui, int m, int n) {
		final String ans = "";
		if (this.match[m][n].getIsBomb()) {
			this.openAll();
			this.setRunning(false);
			return "lost";
		} else {
			this.match[m][n].setOpen(false);
			if (this.match[m][n].getSurrBombs() == 0) {
				this.setZerosAsClosed(m, n);
			}
			this.setRunning(false);
			for (int i = 0; i < this.match.length; i++) {
				for (int j = 0; j < this.match[0].length; j++) {
					if (this.match[i][j].isOpen() && (this.match[i][j].getIsBomb() == false)) {
						this.setRunning(true);
					}
				}
			}
			if (this.isRunning() == false) {
				this.openAll();
				return "won the match";
			}
		}
		Constants.Sgst = gui.getGame().RandomOpenCell(gui);
		return ans;
	}

	/**
	 * Opens all cells
	 */
	public void openAll() {
		for (int i = 0; i < this.match.length; i++) {
			for (int j = 0; j < this.match[0].length; j++) {
				this.match[i][j].setOpen(false);
			}
		}
	}

	public int[] RandomOpenCell(Gui gui) {
		int[][] tmp = new int[2][0];
		int[][] ListOfOpenCells = new int[2][0];
		for (int i = 0; i < this.match.length; i++) {
			for (int j = 0; j < this.match[0].length; j++) {
				if (this.match[i][j].isOpen() && !(this.match[i][j].getIsBomb())) {
					tmp = new int[2][tmp[0].length + 1];
					for (int l = 0; l < ListOfOpenCells[0].length; l++) {
						tmp[0][l] = ListOfOpenCells[0][l];
						tmp[1][l] = ListOfOpenCells[1][l];
					}
					tmp[0][ListOfOpenCells[0].length] = i;
					tmp[1][ListOfOpenCells[1].length] = j;
					ListOfOpenCells = tmp;
				}
			}
		}
		final int[] ans = new int[2];
		final int rand = (int) gui.random(ListOfOpenCells[0].length - 1);
		ans[0] = ListOfOpenCells[0][rand];
		ans[1] = ListOfOpenCells[1][rand];
		return ans;
	}

	/**
	 * Renewer for instance
	 */
	public void renew() {
		this.reset();
		IGui.setRandomBombs(this);
		IGui.setSurrBombsAll(this);
	}

	/**
	 * Resetter for instance
	 */
	public void reset() {
		for (int i = 0; i < this.match.length; i++) {
			for (int j = 0; j < this.match[0].length; j++) {
				this.match[i][j].setOpen(true);
				this.match[i][j].setBeenChecked(false);
			}
		}
		IGui.setSurrBombsAll(this);
	}

	/**
	 * Setter for match
	 * 
	 * @param game the game to use for the match
	 */
	public void setMatch(Cell[][] game) {
		this.match = game;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Sets all cells with 0 neighbours next to cell in (m,n) to zero Recursively
	 * defined - hence bounded with respect to recursion depth
	 * 
	 * @param m y-position of cell
	 * @param n x-position of cell
	 */
	public void setZerosAsClosed(int m, int n) {
		if (!this.match[m][n].isBeenChecked()) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if ((!(i == 0) || !(j == 0)) && (m + i >= 0) && ((m + i) < this.match.length) && (n + j >= 0)
							&& ((n + j) < this.match[0].length) && this.match[m + i][n + j].isOpen()) {
						this.match[m + i][n + j].setOpen(false);
						if (!this.match[m + i][n + j].getIsBomb() && this.match[m + i][n + j].getSurrBombs() == 0
								&& !this.match[m + i][n + j].isBeenChecked()) {
							this.setZerosAsClosed(m + i, n + j);
						}
					}
				}
			}
			this.match[m][n].setBeenChecked(true);
		}
	}
}
