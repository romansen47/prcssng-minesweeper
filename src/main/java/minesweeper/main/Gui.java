package minesweeper.main;

import java.awt.Color;

import minesweeper.conf.Constants;
import minesweeper.interfaces.IGui;
import minesweeper.objects.Cell;
import minesweeper.objects.Game;
import processing.event.MouseEvent;

/**
 * @author ro
 *
 */

public class Gui extends processing.template.Gui implements IGui {

	final static String mainclass = "minesweeper.main.Gui";
	final static String path = "";
	public static String[] Pics;

	public static void main(String[] args) {

		Pics = new String[1];
		{
			Pics[0] = "rb.png";
		}

		// Random r=new Random();
		Constants.dataString = Pics[0]; // [6]; //

		Constants.intensity = 60;
		Gui.setSIZE(80);
		// System.out.println("hi");

		(new Gui()).run(mainclass);

	}

	/**
	 * static Game class
	 */
	private Game Game;

	/**
	 * for saving/reloading purposes
	 */
	private static Game TmpMatch = null;

	/**
	 * Main Gui object
	 */
	public Gui() {
	}

	/**
	 * Another constructor using the actual setup
	 * 
	 * @param match game for actual Match
	 */
	public Gui(Game match) {
		this.setGame(match);
	}

	/**
	 * Amount of cells along the longest side
	 */
	private static int SIZE;

	/**
	 * Setter for {@SIZE}
	 * 
	 * @param size
	 */
	public static void setSIZE(int size) {
		SIZE = size;
	}

	/**
	 * Zoom factor
	 */
	private float zoom = 1;

	/**
	 * Getter for Match
	 * 
	 * @return Match
	 */
	public Game getGame() {
		return Game;
	}

	/**
	 * Getter for Match
	 * 
	 * @param match
	 */
	public void setGame(Game game) {
		Game = game;
	}

	public Cell[][] getMatch() {
		return getGame().getMatch();
	}

	public void setMatch(Cell[][] match) {
		getGame().setMatch(match);
	}

	/**
	 * Zoom function
	 * 
	 * @param e is 1.0 if mouseWheel pulled
	 */
	public void zoom(double e) {
		if (getZoom() + e * 0.2 > 1) {
			setZoom((float) (getZoom() + e * 0.2));
			if (Constants.xDefault - (int) (e * 0.2 * mouseX) <= 0 && (Constants.xDefault - (int) (e * 0.2 * mouseX)
					+ getGame().getMatch()[0].length * Constants.CellSize * getZoom() > getGame().getMatch()[0].length
							* Constants.CellSize)) {
				Constants.xDefault += -(int) (e * 0.2 * mouseX);
			} else {
				if (Constants.xDefault - (int) (e * 0.2 * mouseX) > 0) {
					Constants.xDefault = 0;
				} else {
					Constants.xDefault += -(int) (e * 0.2 * mouseX)
							+ getGame().getMatch()[0].length * Constants.CellSize * getZoom()
							- getGame().getMatch()[0].length * Constants.CellSize;
				}
			}
			if (Constants.yDefault - (int) (e * 0.2 * mouseY) <= 0 && (Constants.yDefault - (int) (e * 0.2 * mouseY)
					+ getGame().getMatch().length * Constants.CellSize * getZoom() > getGame().getMatch().length
							* Constants.CellSize)) {
				Constants.yDefault += -(int) (e * 0.2 * mouseY);
			} else {
				if (Constants.yDefault - (int) (e * 0.2 * mouseY) > 0) {
					Constants.yDefault = 0;
				} else {
					Constants.yDefault += -(int) (e * 0.2 * mouseY)
							+ getGame().getMatch().length * Constants.CellSize * getZoom()
							- getGame().getMatch().length * Constants.CellSize;
				}
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see processing.template.IGuiTemplate#mouseWheel(MouseEvent event)
	 */
	@Override
	public void mouseWheel(MouseEvent event) {
		float e = event.getCount();
		zoom(e);
	}

	@Override
	public void settings() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#setup()
	 */
	@Override
	public void setup() {

		background(255);
		frameRate(24);
		surface.setResizable(true);
		Constants.minefield = loadImage(Constants.dataString);
		Constants.ratio = (float) Constants.minefield.height / Constants.minefield.width;
		IGui.setParam();
		this.setGame(new Game(SIZE, (int) ((SIZE) * Constants.ratio)));
		IGui.setRandomBombs(getGame());
		IGui.setSurrBombsAll(getGame());
		Constants.CellSize = Math.min(Constants.Horizontal, Constants.Vertical) / SIZE;
		surface.setSize(getGame().getMatch()[0].length * Constants.CellSize,
				getGame().getMatch().length * Constants.CellSize);
		surface.setLocation(displayWidth - width >> 1, displayHeight - height >> 1);
		Constants.bomb = loadImage("bomb.png");
		Constants.minefield = loadImage(Constants.dataString);
		Constants.minefield.filter(THRESHOLD, 0.55f);
		Constants.Stuck = (int) (0.2 * Constants.CellSize);
		genMinefieldFromImage();
		IGui.setSurrBombsAll(getGame());

	}

	/**
	 * Creates minefield from a given picture
	 */
	public void genMinefieldFromImage() {

		for (int i = 0; i < getGame().getMatch().length; i++) {
			for (int j = 0; j < getGame().getMatch()[0].length; j++) {
				Color pixCol = new Color(
						Constants.minefield.get((int) (j * Constants.minefield.width / (SIZE * Constants.ratio)),
								i * Constants.minefield.height / SIZE));
				int r = pixCol.getRed();
				int g = pixCol.getGreen();
				int b = pixCol.getBlue();
				/*
				 * if ( (Math.sqrt(r*r+g*g+b*b)<3*Constants.intensity) ){
				 * Match.getMatch()[i][j].setIsBomb(); }
				 */
				if (r == 0 && g == 0 && b == 0) {
					getGame().getMatch()[i][j].setIsBomb();
				} else {
					getGame().getMatch()[i][j].setIsNotABomb();
				}
			}
		}
	}

	/**
	 * Translator for X-coordinate to index n
	 * 
	 * @param n x-position
	 * @return x-coordinate
	 */
	public int getXCoordinates(int n) {
		return Constants.xDefault + n * (int) (Constants.CellSize * getZoom());
	}

	/**
	 * Translator for Y-coordinate to index m
	 * 
	 * @param m y-position
	 * @return y-coordinate
	 */
	public int getYCoordinates(int m) {
		return Constants.yDefault + m * (int) (Constants.CellSize * getZoom());
	}

	/**
	 * Inverse to getYCoordinates
	 * 
	 * @param y y-coordinate
	 * @return y-position
	 */
	public int getYIndexFromCoordinates(int y) {
		int i = 0;
		while (getYCoordinates(i) < y) {
			i = i + 1;
		}
		return i;
	}

	/**
	 * Inverso to getXCoordinates
	 * 
	 * @param x x-coordinate
	 * @return x-position
	 */
	public int getXIndexFromCoordinates(int x) {
		int i = 0;
		while (getXCoordinates(i) < x) {
			i = i + 1;
		}
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		int Case = clicked();
		if (mousePressed) {
			Constants.deLay = this.millis();
		}
		if ((mousePressed) && (mouseButton == RIGHT)) {
			if ((Constants.xDefault + mouseX - getPressedPos()[1] <= 0)
					&& (Constants.xDefault + mouseX - getPressedPos()[1] + getGame().getMatch()[0].length
							* Constants.CellSize * getZoom() >= getGame().getMatch()[0].length * Constants.CellSize)) {
				Constants.xDefault += mouseX - getPressedPos()[1];
			} else {
				if (Constants.xDefault + mouseX - getPressedPos()[1] > 0) {
					Constants.xDefault = 0;
				}
			}
			if ((Constants.yDefault + mouseY - getPressedPos()[0] <= 0)
					&& (Constants.yDefault + getGame().getMatch().length * Constants.CellSize * getZoom() + mouseY
							- getPressedPos()[0] >= getGame().getMatch().length * Constants.CellSize)) {
				Constants.yDefault += mouseY - getPressedPos()[0];
			} else {
				if (Constants.yDefault + mouseY - getPressedPos()[0] > 0) {
					Constants.yDefault = 0;
				}
			}
			getPressedPos()[1] = mouseX;
			getPressedPos()[0] = mouseY;
		}

		if (keyPressed) {
			switch (key) {
			case 'r':
				getGame().reset();
				setZoom(1);
				break;
			case 'n':
				getGame().renew();
				break;
			case 'q':
				exit();
				break;
			case '+':
				setZoom(getZoom() + (float) 0.2);
				break;
			case '-':
				setZoom(getZoom() - (float) 0.2);
				break;
			}
		}

		if (Constants.restart) {
			if (Case == 1 && mouseButton == LEFT) {
				Constants.restart = false;
				getGame().setRunning(true);
				this.setGame(new Game(getGame().getMatch().length, getGame().getMatch()[0].length));
				IGui.setRandomBombs(getGame());
				IGui.setSurrBombsAll(getGame());
				setZoom(1);
				Constants.xDefault = 0;
				Constants.yDefault = 0;
				delay(1000);

			}
		} else {
			if (getGame().isRunning() == false) {
				getGame().draw(this);
				if (Case == 1 && mouseButton == LEFT) {
					background(0);
					fill(255, 0, 0);
					textSize(30);
					text("You " + Constants.success + "!", (int) (0.2 * Constants.Horizontal),
							(int) (0.5 * Constants.Vertical));
					Constants.restart = true;
				}
			} else {
				background(255);
				switch (Case) {
				case 1:
					if (mouseButton == LEFT) {
						Constants.success = getGame().leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
								getXIndexFromCoordinates(mouseX) - 1);
					}
					if (mouseButton == RIGHT) {
						getGame().getMatch()[getYIndexFromCoordinates(mouseY) - 1][getXIndexFromCoordinates(mouseX) - 1]
								.setMarkedAsBomb(!(getGame().getMatch()[getYIndexFromCoordinates(mouseY)
										- 1][getXIndexFromCoordinates(mouseX) - 1].isMarkedAsBomb()));
					}
					break;
				case 2:
					if (mouseButton == LEFT) {
						Constants.success = getGame().leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
								getXIndexFromCoordinates(mouseX) - 1);
					}
					break;
				}
				getGame().draw(this);
			}
		}
		if (keyPressed && key == 's' && getGame().isRunning()) {
			if (getTmpMatch() == null) {
				setTmpMatch(new Game(getGame().getMatch().length, getGame().getMatch()[0].length));
			} else {
				for (int i = 0; i < getGame().getMatch().length; i++) {
					for (int j = 0; j < getGame().getMatch()[0].length; j++) {
						getTmpMatch().getMatch()[i][j] = getGame().getMatch()[i][j].copy();
					}
				}
			}
		}
		if (keyPressed && key == 'l' && getGame().isRunning()) {
			if (getTmpMatch() != null) {
				for (int i = 0; i < getGame().getMatch().length; i++) {
					for (int j = 0; j < getGame().getMatch()[0].length; j++) {
						getGame().getMatch()[i][j] = getTmpMatch().getMatch()[i][j].copy();
					}
				}
				IGui.setSurrBombsAll(getTmpMatch());
			}
		}
	}

	public static Game getTmpMatch() {
		return TmpMatch;
	}

	public static void setTmpMatch(Game tmpMatch) {
		TmpMatch = tmpMatch;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

}
