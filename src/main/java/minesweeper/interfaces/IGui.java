package minesweeper.interfaces;

import java.awt.Toolkit;

import minesweeper.conf.Constants;
import minesweeper.objects.Game;

/**
 * @author ro
 *
 */

/*
 * The IGui interface contains operations for Gui class
 */

public interface IGui {

	/**
	 * determines the constants
	 */
	static void setParam() {

		Constants.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Constants.Vertical = (int) (0.85 * Constants.screenSize.getHeight());
		Constants.Horizontal = (int) (Constants.ratio * 0.85 * Constants.screenSize.getWidth());
		Constants.Size = Math.min(Constants.Horizontal, Constants.Vertical);

	}

	/**
	 * 
	 * @param Mines
	 * 
	 *              sets bombs randomly
	 */
	static void setRandomBombs(Game Mines) {
		for (int i = 0; i < Mines.getMatch().length; i++) {
			for (int j = 0; j < Mines.getMatch()[0].length; j++) {
				Mines.getMatch()[i][j].setIsNotABomb();
				if (Math.random() < 0.12) {
					Mines.getMatch()[i][j].setIsBomb();
				}
			}
		}
	}

	/**
	 * 
	 * @param m y-Coordinate
	 * @param n x-Coordinate
	 * 
	 *          counts all bombs around cell(m,n)
	 */
	static void setSurrBombs(int m, int n, Game Mines) {
		int tmpSurrBombs = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if ((!((i == 0) && (j == 0))) && (m + i >= 0) && ((m + i) < Mines.getMatch().length) && (n + j >= 0)
						&& ((n + j) < Mines.getMatch()[0].length) && Mines.getMatch()[m + i][n + j].getIsBomb()) {
					tmpSurrBombs = tmpSurrBombs + 1;
				}
			}
		}
		Mines.getMatch()[m][n].setSurrBombs(tmpSurrBombs);
	}

	/**
	 * 
	 * @param Mines
	 * 
	 *              determines all surrBombs for all cells
	 */
	static void setSurrBombsAll(Game Mines) {
		for (int i = 0; i < Mines.getMatch().length; i++) {
			for (int j = 0; j < Mines.getMatch()[0].length; j++) {
				IGui.setSurrBombs(i, j, Mines);
			}
		}
	}
}
