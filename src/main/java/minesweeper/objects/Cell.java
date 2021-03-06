package minesweeper.objects;

import minesweeper.conf.Constants;
import minesweeper.interfaces.IDrawable;
import minesweeper.main.Gui;

/**
 * @author ro
 *
 */

public class Cell implements IDrawable {

	/**
	 * tells whether has been marked as
	 * already checked for being not
	 * surrounded by bombs
	 */
	private boolean beenChecked = false;

	/**
	 * tells whether is a bomb or not
	 */
	private boolean isBomb;

	/**
	 * true if cell is open, false else
	 */
	private boolean isOpen;

	/**
	 * tells whether has been marked as a
	 * bomb
	 */
	private boolean markedAsBomb = false;

	/**
	 * tells how many bombs surrounding
	 */
	private int surrBombs;

	/**
	 * Constructor
	 */
	public Cell() {
		surrBombs	= 0;
		isOpen		= true;
		isBomb		= false;
	}

	/**
	 * cloning function for saving purposes
	 *
	 * @return clone of this cell
	 */
	public Cell copy() {
		final Cell Ans = new Cell();
		Ans.setOpen(isOpen);
		if (isBomb) {
			Ans.setIsBomb();
		} else {
			Ans.setIsNotABomb();
		}
		Ans.setMarkedAsBomb(isMarkedAsBomb());
		Ans.setSurrBombs(getSurrBombs());
		return Ans;
	}

	@Override
	public void draw(Gui gui) {

		final int[]	coordinates	= gui.getGame().getCoordinates(this);
		final int	m			= coordinates[0];
		final int	n			= coordinates[1];
		if (isOpen()) {
			gui.fill(255);
			gui.triangle(gui.getXCoordinates(n), gui.getYCoordinates(m), gui.getXCoordinates(n),
					gui.getYCoordinates(m) + Constants.CellSize * gui.getZoom(),
					gui.getXCoordinates(n) + Constants.CellSize * gui.getZoom(), gui.getYCoordinates(m));
			gui.fill(100);
			gui.triangle(gui.getXCoordinates(n) + Constants.CellSize * gui.getZoom(),
					gui.getYCoordinates(m) + Constants.CellSize * gui.getZoom(), gui.getXCoordinates(n),
					gui.getYCoordinates(m) + Constants.CellSize * gui.getZoom(),
					gui.getXCoordinates(n) + Constants.CellSize * gui.getZoom(), gui.getYCoordinates(m));
			if (gui.getMatch()[m][n].isMarkedAsBomb()) {
				gui.fill(255, 0, 0);
			} else {
				gui.fill(180);
				if (m == Constants.Sgst[0] && n == Constants.Sgst[1]) {
					if (gui.millis() - Constants.deLay > 6000 && (gui.millis() - Constants.deLay) % 1000 < 500) {
						gui.fill(0, 255, 0);
					}
				}
			}
			gui.rect(gui.getXCoordinates(n) + (int) (Constants.Stuck * gui.getZoom()),
					gui.getYCoordinates(m) + (int) (Constants.Stuck * gui.getZoom()),
					Constants.CellSize * gui.getZoom() - 2 * (int) (Constants.Stuck * gui.getZoom()),
					Constants.CellSize * gui.getZoom() - 2 * (int) (Constants.Stuck * gui.getZoom()));
		} else {
			if (gui.getMatch()[m][n].getIsBomb()) {
				gui.fill(255, 0, 0);
				gui.rect(gui.getXCoordinates(n), gui.getYCoordinates(m), Constants.CellSize * gui.getZoom(),
						Constants.CellSize * gui.getZoom());
				gui.image(Constants.bomb, gui.getXCoordinates(n), gui.getYCoordinates(m),
						Constants.CellSize * gui.getZoom(), Constants.CellSize * gui.getZoom());
			} else {
				gui.fill((int) (235 * (1.0 - gui.getMatch()[m][n].getSurrBombs() / 8.0)));
				gui.rect(gui.getXCoordinates(n), gui.getYCoordinates(m), Constants.CellSize * gui.getZoom(),
						Constants.CellSize * gui.getZoom());
				gui.textSize((int) (0.85 * Constants.CellSize * gui.getZoom()));
				if (gui.getMatch()[m][n].getSurrBombs() > 0) {
					switch (gui.getMatch()[m][n].getSurrBombs()) {
						case 1:
							gui.fill(0, 0, 255);
							break;
						case 2:
							gui.fill(0, 255, 0, 255);
							break;
						case 3:
							gui.fill(255, 0, 0, 255);
							break;
						case 4:
							gui.fill(0, 0, 155, 255);
							break;
						case 5:
							gui.fill(0, 155, 0, 255);
							break;
						case 6:
							gui.fill(155, 0, 0, 255);
							break;
						case 7:
							gui.fill(155, 0, 155, 255);
							break;
						case 8:
							gui.fill(155, 100, 155, 255);
							break;
					}
					gui.text(gui.getMatch()[m][n].getSurrBombs(),
							gui.getXCoordinates(n) + (int) (0.2 * Constants.CellSize),
							gui.getYCoordinates(m) + (int) (0.9 * Constants.CellSize * gui.getZoom()));
				}
				if (gui.getGame().isRunning() == false) {
					if (gui.getMatch()[m][n].isMarkedAsBomb()) {
						gui.line(gui.getXCoordinates(n), gui.getYCoordinates(m),
								gui.getXCoordinates(n) + Constants.CellSize * gui.getZoom(),
								gui.getYCoordinates(m) + Constants.CellSize * gui.getZoom());
						gui.line(gui.getXCoordinates(n) + Constants.CellSize * gui.getZoom(), gui.getYCoordinates(m),
								gui.getXCoordinates(n), gui.getYCoordinates(m) + Constants.CellSize * gui.getZoom());
					}
				}

			}
		}
	}

	/**
	 * getter for isBomb
	 *
	 * @return true if is bomb
	 */
	public boolean getIsBomb() {
		return isBomb;
	}

	/**
	 * surrBombs for the cell
	 *
	 * @return returns amount of surrounding
	 *         bombs
	 */
	public int getSurrBombs() {
		return surrBombs;
	}

	/**
	 * returns value of beenChecked
	 *
	 * @return if has already been checked
	 *         while searching for cells
	 *         with zero surrounding bombs
	 */
	public boolean isBeenChecked() {
		return beenChecked;
	}

	/**
	 * returns value of markedAsBomb
	 *
	 * @return treu if is marked as a bomb
	 */
	public boolean isMarkedAsBomb() {
		return markedAsBomb;
	}

	/**
	 * returns value of isOpen
	 *
	 * @return true if is open
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * setter for beenChecked
	 *
	 * @param beenChecked the value
	 */
	public void setBeenChecked(boolean beenChecked) {
		this.beenChecked = beenChecked;
	}

	/**
	 * sets isBomb as true
	 */
	public void setIsBomb() {
		isBomb = true;
	}

	/**
	 * sets isBomb as false
	 */
	public void setIsNotABomb() {
		isBomb = false;
	}

	/**
	 * setter for markedAsBomb
	 *
	 * @param markedAsBomb the value
	 */
	public void setMarkedAsBomb(boolean markedAsBomb) {
		this.markedAsBomb = markedAsBomb;
	}

	/**
	 * setter for isOpen
	 *
	 * @param isOpen current isOpen value
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * setter for surrBombs
	 *
	 * @param bombs amount of surrounding
	 *              bombs
	 */
	public void setSurrBombs(int bombs) {
		surrBombs = bombs;
	}

}
