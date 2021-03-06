package minesweeper.interfaces;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.IOException;

import minesweeper.conf.Constants;
import minesweeper.conf.JavaDownloadFileFromURL;
import minesweeper.main.Gui;
import minesweeper.objects.Game;

/**
 * @author ro
 *
 */

/*
 * The IGui interface contains
 * operations for Gui class
 */

public interface IGui {

	/**
	 * determines the constants
	 */
	static void setParam() {

		Constants.screenSize	= Toolkit.getDefaultToolkit().getScreenSize();
		Constants.Vertical		= (int) (0.85 * Constants.screenSize.getHeight());
		Constants.Horizontal	= (int) (Constants.ratio * 0.85 * Constants.screenSize.getWidth());
		Constants.Size			= Math.min(Constants.Horizontal, Constants.Vertical);

	}

	/**
	 * sets bombs randomly
	 *
	 * @param Mines the game
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
	 * counts all bombs around cell(m,n)
	 *
	 * @param m y-Coordinate
	 * @param n x-Coordinate
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
	 * determines all surrBombs for all
	 * cells
	 *
	 * @param Mines the game
	 */
	static void setSurrBombsAll(Game Mines) {
		for (int i = 0; i < Mines.getMatch().length; i++) {
			for (int j = 0; j < Mines.getMatch()[0].length; j++) {
				IGui.setSurrBombs(i, j, Mines);
			}
		}
	}

}
