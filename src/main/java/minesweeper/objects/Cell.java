package minesweeper.objects;

/**
 * @author ro
 *
 */

public class Cell {

	/**
	 * true if cell is open, false else
	 */
	private boolean isOpen;

	/**
	 * tells how many bombs surrounding
	 */
	private int surrBombs;

	/**
	 * tells whether is a bomb or not
	 */
	private boolean isBomb;

	/**
	 * tells whether has been marked as a bomb
	 */
	private boolean markedAsBomb = false;

	/**
	 * tells whether has been marked as already checked for being not surrounded by
	 * bombs
	 */
	private boolean beenChecked = false;

	/**
	 * Constructor
	 */
	public Cell() {
		this.surrBombs = 0;
		this.isOpen = true;
		this.isBomb = false;
	}

	/**
	 * surrBombs for the cell
	 * 
	 * @return returns amount of surrounding bombs
	 */
	public int getSurrBombs() {
		return this.surrBombs;
	}

	/**
	 * setter for surrBombs
	 * 
	 * @param bombs amount of surrounding bombs
	 */
	public void setSurrBombs(int bombs) {
		this.surrBombs = bombs;
	}

	/**
	 * getter for isBomb
	 * 
	 * @return true if is bomb
	 */
	public boolean getIsBomb() {
		return this.isBomb;
	}

	/**
	 * sets isBomb as true
	 */
	public void setIsBomb() {
		this.isBomb = true;
	}

	/**
	 * sets isBomb as false
	 */
	public void setIsNotABomb() {
		this.isBomb = false;
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
	 * setter for isOpen
	 * 
	 * @param isOpen current isOpen value
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
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
	 * setter for markedAsBomb
	 * 
	 * @param markedAsBomb
	 */
	public void setMarkedAsBomb(boolean markedAsBomb) {
		this.markedAsBomb = markedAsBomb;
	}

	/**
	 * returns value of beenChecked
	 * 
	 * @return if has already been checked while searching for cells with zero
	 *         surrounding bombs
	 */
	public boolean isBeenChecked() {
		return beenChecked;
	}

	/**
	 * setter for beenChecked
	 * 
	 * @param beenChecked
	 */
	public void setBeenChecked(boolean beenChecked) {
		this.beenChecked = beenChecked;
	}

	/**
	 * cloning function for saving purposes
	 * 
	 * @return clone of this cell
	 */
	public Cell copy() {
		Cell Ans = new Cell();
		Ans.setOpen(this.isOpen);
		if (this.isBomb) {
			Ans.setIsBomb();
		} else {
			Ans.setIsNotABomb();
		}
		Ans.setMarkedAsBomb(this.isMarkedAsBomb());
		Ans.setSurrBombs(this.getSurrBombs());
		return Ans;
	}

}
