package minesweeper.main;

import java.awt.Color;

import minesweeper.conf.Constants;
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

	final static String mainclass = "minesweeper.main.Gui";
	final static String path = "";
	public static String[] Pics;

	/**
	 * for saving/reloading purposes
	 */
	private static Game TmpMatch = null;

	/**
	 * Amount of cells along the longest side
	 */
	private static int SIZE;

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

		Constants.intensity = 60;
		Gui.setSIZE(80);
		// System.out.println("hi");

		(new Gui()).run(Gui.mainclass);

	}

	/**
	 * Setter for {@SIZE}
	 * 
	 * @param size
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
	 * Another constructor using the actual setup
	 * 
	 * @param match game for actual Match
	 */
	public Gui(Game match) {
		this.setGame(match);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		final int Case = this.clicked();
		if (this.mousePressed) {
			Constants.deLay = this.millis();
		}
		if ((this.mousePressed) && (this.mouseButton == PConstants.RIGHT)) {
			if ((Constants.xDefault + this.mouseX - processing.template.Gui.getPressedPos()[1] <= 0)
					&& (Constants.xDefault + this.mouseX - processing.template.Gui.getPressedPos()[1]
							+ this.getGame().getMatch()[0].length * Constants.CellSize
									* this.getZoom() >= this.getGame().getMatch()[0].length * Constants.CellSize)) {
				Constants.xDefault += this.mouseX - processing.template.Gui.getPressedPos()[1];
			} else {
				if (Constants.xDefault + this.mouseX - processing.template.Gui.getPressedPos()[1] > 0) {
					Constants.xDefault = 0;
				}
			}
			if ((Constants.yDefault + this.mouseY - processing.template.Gui.getPressedPos()[0] <= 0)
					&& (Constants.yDefault + this.getGame().getMatch().length * Constants.CellSize * this.getZoom()
							+ this.mouseY
							- processing.template.Gui.getPressedPos()[0] >= this.getGame().getMatch().length
									* Constants.CellSize)) {
				Constants.yDefault += this.mouseY - processing.template.Gui.getPressedPos()[0];
			} else {
				if (Constants.yDefault + this.mouseY - processing.template.Gui.getPressedPos()[0] > 0) {
					Constants.yDefault = 0;
				}
			}
			processing.template.Gui.getPressedPos()[1] = this.mouseX;
			processing.template.Gui.getPressedPos()[0] = this.mouseY;
		}

		if (this.keyPressed) {
			switch (this.key) {
			case 'r':
				this.getGame().reset();
				this.setZoom(1);
				break;
			case 'n':
				this.getGame().renew();
				break;
			case 'q':
				this.exit();
				break;
			case '+':
				this.setZoom(this.getZoom() + (float) 0.2);
				break;
			case '-':
				this.setZoom(this.getZoom() - (float) 0.2);
				break;
			}
		}

		if (Constants.restart) {
			if (Case == 1 && this.mouseButton == PConstants.LEFT) {
				Constants.restart = false;
				this.getGame().setRunning(true);
				this.setGame(new Game(this.getGame().getMatch().length, this.getGame().getMatch()[0].length));
				IGui.setRandomBombs(this.getGame());
				IGui.setSurrBombsAll(this.getGame());
				this.setZoom(1);
				Constants.xDefault = 0;
				Constants.yDefault = 0;
				this.delay(1000);

			}
		} else {
			if (this.getGame().isRunning() == false) {
				this.getGame().draw(this);
				if (Case == 1 && this.mouseButton == PConstants.LEFT) {
					this.background(0);
					this.fill(255, 0, 0);
					this.textSize(30);
					this.text("You " + Constants.success + "!", (int) (0.2 * Constants.Horizontal),
							(int) (0.5 * Constants.Vertical));
					Constants.restart = true;
				}
			} else {
				this.background(255);
				switch (Case) {
				case 1:
					if (this.mouseButton == PConstants.LEFT) {
						Constants.success = this.getGame().leftClicked(this,
								this.getYIndexFromCoordinates(this.mouseY) - 1,
								this.getXIndexFromCoordinates(this.mouseX) - 1);
					}
					if (this.mouseButton == PConstants.RIGHT) {
						this.getGame().getMatch()[this.getYIndexFromCoordinates(this.mouseY) - 1][this
								.getXIndexFromCoordinates(this.mouseX) - 1].setMarkedAsBomb(
										!(this.getGame().getMatch()[this.getYIndexFromCoordinates(this.mouseY) - 1][this
												.getXIndexFromCoordinates(this.mouseX) - 1].isMarkedAsBomb()));
					}
					break;
				case 2:
					if (this.mouseButton == PConstants.LEFT) {
						Constants.success = this.getGame().leftClicked(this,
								this.getYIndexFromCoordinates(this.mouseY) - 1,
								this.getXIndexFromCoordinates(this.mouseX) - 1);
					}
					break;
				}
				this.getGame().draw(this);
			}
		}
		if (this.keyPressed && this.key == 's' && this.getGame().isRunning()) {
			if (Gui.getTmpMatch() == null) {
				Gui.setTmpMatch(new Game(this.getGame().getMatch().length, this.getGame().getMatch()[0].length));
			} else {
				for (int i = 0; i < this.getGame().getMatch().length; i++) {
					for (int j = 0; j < this.getGame().getMatch()[0].length; j++) {
						Gui.getTmpMatch().getMatch()[i][j] = this.getGame().getMatch()[i][j].copy();
					}
				}
			}
		}
		if (this.keyPressed && this.key == 'l' && this.getGame().isRunning()) {
			if (Gui.getTmpMatch() != null) {
				for (int i = 0; i < this.getGame().getMatch().length; i++) {
					for (int j = 0; j < this.getGame().getMatch()[0].length; j++) {
						this.getGame().getMatch()[i][j] = Gui.getTmpMatch().getMatch()[i][j].copy();
					}
				}
				IGui.setSurrBombsAll(Gui.getTmpMatch());
			}
		}
	}

	/**
	 * Creates minefield from a given picture
	 */
	public void genMinefieldFromImage() {

		for (int i = 0; i < this.getGame().getMatch().length; i++) {
			for (int j = 0; j < this.getGame().getMatch()[0].length; j++) {
				final Color pixCol = new Color(
						Constants.minefield.get((int) (j * Constants.minefield.width / (Gui.SIZE * Constants.ratio)),
								i * Constants.minefield.height / Gui.SIZE));
				final int r = pixCol.getRed();
				final int g = pixCol.getGreen();
				final int b = pixCol.getBlue();
				/*
				 * if ( (Math.sqrt(r*r+g*g+b*b)<3*Constants.intensity) ){
				 * Match.getMatch()[i][j].setIsBomb(); }
				 */
				if (r == 0 && g == 0 && b == 0) {
					this.getGame().getMatch()[i][j].setIsBomb();
				} else {
					this.getGame().getMatch()[i][j].setIsNotABomb();
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
		return this.Game;
	}

	public Cell[][] getMatch() {
		return this.getGame().getMatch();
	}

	/**
	 * Translator for X-coordinate to index n
	 * 
	 * @param n x-position
	 * @return x-coordinate
	 */
	public int getXCoordinates(int n) {
		return Constants.xDefault + n * (int) (Constants.CellSize * this.getZoom());
	}

	/**
	 * Inverso to getXCoordinates
	 * 
	 * @param x x-coordinate
	 * @return x-position
	 */
	public int getXIndexFromCoordinates(int x) {
		int i = 0;
		while (this.getXCoordinates(i) < x) {
			i = i + 1;
		}
		return i;
	}

	/**
	 * Translator for Y-coordinate to index m
	 * 
	 * @param m y-position
	 * @return y-coordinate
	 */
	public int getYCoordinates(int m) {
		return Constants.yDefault + m * (int) (Constants.CellSize * this.getZoom());
	}

	/**
	 * Inverse to getYCoordinates
	 * 
	 * @param y y-coordinate
	 * @return y-position
	 */
	public int getYIndexFromCoordinates(int y) {
		int i = 0;
		while (this.getYCoordinates(i) < y) {
			i = i + 1;
		}
		return i;
	}

	public float getZoom() {
		return this.zoom;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see processing.template.IGuiTemplate#mouseWheel(MouseEvent event)
	 */
	@Override
	public void mouseWheel(MouseEvent event) {
		final float e = event.getCount();
		this.zoom(e);
	}

	/**
	 * Getter for Match
	 * 
	 * @param match
	 */
	public void setGame(Game game) {
		this.Game = game;
	}

	public void setMatch(Cell[][] match) {
		this.getGame().setMatch(match);
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
		this.frameRate(24);
		this.surface.setResizable(true);
		Constants.minefield = this.loadImage(Constants.dataString);
		Constants.ratio = (float) Constants.minefield.height / Constants.minefield.width;
		IGui.setParam();
		this.setGame(new Game(Gui.SIZE, (int) ((Gui.SIZE) * Constants.ratio)));
		IGui.setRandomBombs(this.getGame());
		IGui.setSurrBombsAll(this.getGame());
		Constants.CellSize = Math.min(Constants.Horizontal, Constants.Vertical) / Gui.SIZE;
		this.surface.setSize(this.getGame().getMatch()[0].length * Constants.CellSize,
				this.getGame().getMatch().length * Constants.CellSize);
		this.surface.setLocation(this.displayWidth - this.width >> 1, this.displayHeight - this.height >> 1);
		Constants.bomb = this.loadImage("bomb.png");
		Constants.minefield = this.loadImage(Constants.dataString);
		Constants.minefield.filter(PConstants.THRESHOLD, 0.55f);
		Constants.Stuck = (int) (0.2 * Constants.CellSize);
		this.genMinefieldFromImage();
		IGui.setSurrBombsAll(this.getGame());

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
		if (this.getZoom() + e * 0.2 > 1) {
			this.setZoom((float) (this.getZoom() + e * 0.2));
			if (Constants.xDefault - (int) (e * 0.2 * this.mouseX) <= 0 && (Constants.xDefault
					- (int) (e * 0.2 * this.mouseX) + this.getGame().getMatch()[0].length * Constants.CellSize
							* this.getZoom() > this.getGame().getMatch()[0].length * Constants.CellSize)) {
				Constants.xDefault += -(int) (e * 0.2 * this.mouseX);
			} else {
				if (Constants.xDefault - (int) (e * 0.2 * this.mouseX) > 0) {
					Constants.xDefault = 0;
				} else {
					Constants.xDefault += -(int) (e * 0.2 * this.mouseX)
							+ this.getGame().getMatch()[0].length * Constants.CellSize * this.getZoom()
							- this.getGame().getMatch()[0].length * Constants.CellSize;
				}
			}
			if (Constants.yDefault - (int) (e * 0.2 * this.mouseY) <= 0 && (Constants.yDefault
					- (int) (e * 0.2 * this.mouseY) + this.getGame().getMatch().length * Constants.CellSize
							* this.getZoom() > this.getGame().getMatch().length * Constants.CellSize)) {
				Constants.yDefault += -(int) (e * 0.2 * this.mouseY);
			} else {
				if (Constants.yDefault - (int) (e * 0.2 * this.mouseY) > 0) {
					Constants.yDefault = 0;
				} else {
					Constants.yDefault += -(int) (e * 0.2 * this.mouseY)
							+ this.getGame().getMatch().length * Constants.CellSize * this.getZoom()
							- this.getGame().getMatch().length * Constants.CellSize;
				}
			}
		}
	}

}
