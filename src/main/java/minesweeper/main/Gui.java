package minesweeper.main;

import java.awt.Color;
import java.io.IOException;

import minesweeper.conf.Constants;
import minesweeper.conf.JavaDownloadFileFromURL;
import minesweeper.interfaces.IGui;
import minesweeper.objects.Cell;
import minesweeper.objects.Game;
import processing.core.PConstants;
import processing.event.MouseEvent;

/**
 * @author ro
 *
 */

public class Gui extends processing.template.Gui implements IGui {

	final static String		mainclass	= "minesweeper.main.Gui";
	final static String		path		= "";
	public static String[]	Pics;

	/**
	 * Amount of cells along the longest
	 * side
	 */
	private static int SIZE;

	/**
	 * for saving/reloading purposes
	 */
	private static Game TmpMatch = null;

	public static Game getTmpMatch() {
		return Gui.TmpMatch;
	}

	public static void main(String[] args) {

		Gui.Pics = new String[1];
		{
			Gui.Pics[0] = "rb.png";
		}

		// Random r=new Random();
		Constants.dataString = Gui.Pics[0]; // [6]; //

		//Constants.intensity = 20;
		Gui.setSIZE(90);
		// System.out.println("hi");

		(new Gui()).run(Gui.mainclass);

	}

	/**
	 * Setter for SIZE
	 *
	 * @param size the size
	 */
	public static void setSIZE(int size) {
		Gui.SIZE = size;
	}

	public static void setTmpMatch(Game tmpMatch) {
		Gui.TmpMatch = tmpMatch;
	}

	/**
	 * static Game class
	 */
	private Game Game;

	/**
	 * Zoom factor
	 */
	private float zoom = 1;

	/**
	 * Main Gui object
	 */
	public Gui() {
	}

	/**
	 * Another constructor using the actual
	 * setup
	 *
	 * @param match game for actual Match
	 */
	public Gui(Game match) {
		setGame(match);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		final int Case = clicked();
		if (mousePressed) {
			Constants.deLay = millis();
		}
		if ((mousePressed) && (mouseButton == PConstants.RIGHT)) {
			if ((Constants.xDefault + mouseX - processing.template.Gui.getPressedPos()[1] <= 0) && (Constants.xDefault
					+ mouseX - processing.template.Gui.getPressedPos()[1]
					+ getGame().getMatch()[0].length * Constants.CellSize * getZoom() >= getGame().getMatch()[0].length
							* Constants.CellSize)) {
				Constants.xDefault += mouseX - processing.template.Gui.getPressedPos()[1];
			} else {
				if (Constants.xDefault + mouseX - processing.template.Gui.getPressedPos()[1] > 0) {
					Constants.xDefault = 0;
				}
			}
			if ((Constants.yDefault + mouseY - processing.template.Gui.getPressedPos()[0] <= 0) && (Constants.yDefault
					+ getGame().getMatch().length * Constants.CellSize * getZoom() + mouseY
					- processing.template.Gui.getPressedPos()[0] >= getGame().getMatch().length * Constants.CellSize)) {
				Constants.yDefault += mouseY - processing.template.Gui.getPressedPos()[0];
			} else {
				if (Constants.yDefault + mouseY - processing.template.Gui.getPressedPos()[0] > 0) {
					Constants.yDefault = 0;
				}
			}
			processing.template.Gui.getPressedPos()[1]	= mouseX;
			processing.template.Gui.getPressedPos()[0]	= mouseY;
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
			if (Case == 1 && mouseButton == PConstants.LEFT) {
				Constants.restart = false;
				getGame().setRunning(true);
				setGame(new Game(getGame().getMatch().length, getGame().getMatch()[0].length));
				try {
					getRandomPic();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//IGui.setRandomBombs(getGame());
				genMinefieldFromImage();
				IGui.setSurrBombsAll(getGame());
				setZoom(1);
				Constants.xDefault	= 0;
				Constants.yDefault	= 0;
				delay(1000);

			}
		} else {
			if (getGame().isRunning() == false) {
				getGame().draw(this);
				if (Case == 1 && mouseButton == PConstants.LEFT) {
					this.background(0);
					this.fill(255, 0, 0);
					textSize(30);
					this.text("You " + Constants.success + "!", (int) (0.2 * Constants.Horizontal),
							(int) (0.5 * Constants.Vertical));
					Constants.restart = true;
				}
			} else {
				this.background(255);
				switch (Case) {
					case 1:
						if (mouseButton == PConstants.LEFT) {
							Constants.success = getGame().leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
									getXIndexFromCoordinates(mouseX) - 1);
						}
						if (mouseButton == PConstants.RIGHT) {
							getGame().getMatch()[getYIndexFromCoordinates(mouseY) - 1][getXIndexFromCoordinates(mouseX)
									- 1].setMarkedAsBomb(
											!(getGame().getMatch()[getYIndexFromCoordinates(mouseY)
													- 1][getXIndexFromCoordinates(mouseX) - 1].isMarkedAsBomb()));
						}
						break;
					case 2:
						if (mouseButton == PConstants.LEFT) {
							Constants.success = getGame().leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
									getXIndexFromCoordinates(mouseX) - 1);
						}
						break;
				}
				getGame().draw(this);
			}
		}
		if (keyPressed && key == 's' && getGame().isRunning()) {
			if (Gui.getTmpMatch() == null) {
				Gui.setTmpMatch(new Game(getGame().getMatch().length, getGame().getMatch()[0].length));
			} else {
				for (int i = 0; i < getGame().getMatch().length; i++) {
					for (int j = 0; j < getGame().getMatch()[0].length; j++) {
						Gui.getTmpMatch().getMatch()[i][j] = getGame().getMatch()[i][j].copy();
					}
				}
			}
		}
		if (keyPressed && key == 'l' && getGame().isRunning()) {
			if (Gui.getTmpMatch() != null) {
				for (int i = 0; i < getGame().getMatch().length; i++) {
					for (int j = 0; j < getGame().getMatch()[0].length; j++) {
						getGame().getMatch()[i][j] = Gui.getTmpMatch().getMatch()[i][j].copy();
					}
				}
				IGui.setSurrBombsAll(Gui.getTmpMatch());
			}
		}
	}

	/**
	 * Creates minefield from a given
	 * picture
	 */
	public void genMinefieldFromImage() {

		for (int i = 0; i < getGame().getMatch().length; i++) {
			for (int j = 0; j < getGame().getMatch()[0].length; j++) {
				final Color	pixCol	= new Color(
						Constants.minefield.get((int) (j * Constants.minefield.width / (Gui.SIZE * Constants.ratio)),
								i * Constants.minefield.height / Gui.SIZE));
				final int	r		= pixCol.getRed();
				final int	g		= pixCol.getGreen();
				final int	b		= pixCol.getBlue();
				/*
				 * if (
				 * (Math.sqrt(r*r+g*g+b*b)<3*Constants.
				 * intensity) ){
				 * Match.getMatch()[i][j].setIsBomb(); }
				 */
				if (r<120 || g<120|| b<120) {
					getGame().getMatch()[i][j].setIsBomb();
				} else {
					getGame().getMatch()[i][j].setIsNotABomb();
				}
			}
		}
	}

	/**
	 * Getter for Match
	 *
	 * @return Match
	 */
	public Game getGame() {
		return Game;
	}

	public Cell[][] getMatch() {
		return getGame().getMatch();
	}

	/**
	 * Translator for X-coordinate to index
	 * n
	 *
	 * @param n x-position
	 * @return x-coordinate
	 */
	public int getXCoordinates(int n) {
		return Constants.xDefault + n * (int) (Constants.CellSize * getZoom());
	}

	/**
	 * Inverse to getXCoordinates
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

	/**
	 * Translator for Y-coordinate to index
	 * m
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

	public float getZoom() {
		return zoom;
	}

	/**
	 * checks for mouse wheel input
	 *
	 * @param event the mouse wheel event
	 */
	@Override
	public void mouseWheel(MouseEvent event) {
		final float e = event.getCount();
		zoom(e);
	}

	/**
	 * Getter for Game
	 *
	 * @param game the game
	 */
	public void setGame(Game game) {
		Game = game;
	}

	public void setMatch(Cell[][] match) {
		getGame().setMatch(match);
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

		this.background(255);
		frameRate(60);
		surface.setResizable(true);
		Constants.minefield	= this.loadImage(Constants.dataString);
		Constants.ratio		= (float) Constants.minefield.height / Constants.minefield.width;
		IGui.setParam();
		setGame(new Game(Gui.SIZE, (int) ((Gui.SIZE) * Constants.ratio)));
		IGui.setRandomBombs(getGame());
		IGui.setSurrBombsAll(getGame());
		Constants.CellSize = Math.min(Constants.Horizontal, Constants.Vertical) / Gui.SIZE;
		surface.setSize(getGame().getMatch()[0].length * Constants.CellSize,
				getGame().getMatch().length * Constants.CellSize);
		surface.setLocation(displayWidth - width >> 1, displayHeight - height >> 1);
		Constants.bomb		= this.loadImage("bomb.png");
		Constants.minefield	= this.loadImage(Constants.dataString);
		Constants.minefield.filter(PConstants.THRESHOLD, 0.25f);
		Constants.Stuck = (int) (0.2 * Constants.CellSize);
		genMinefieldFromImage();
		IGui.setSurrBombsAll(getGame());

	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
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
	
	void getRandomPic() throws IOException {
		JavaDownloadFileFromURL.downloadUsingNIO("http://picsum.photos/g/200/300/?random/?image=0","tmp.jpeg");
		Constants.minefield	= loadImage("tmp.jpeg");
		genMinefieldFromImage();
	}

}
