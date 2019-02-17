package minesweeper.main;

import java.awt.Color;

import minesweeper.conf.Constants;
import minesweeper.interfaces.IGui;
import minesweeper.objects.Game;
import processing.event.MouseEvent;

/**
 * @author ro
 *
 */

public class Gui extends processing.template.Gui implements IGui {

	/**
	 * static Game class
	 */
	public Game Match;

	/**
	 * for saving/reloading purposes
	 */
	public static Game TmpMatch = null;

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
		this.setMatch(match);
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
	public float zoom = 1;

	/**
	 * Getter for Match
	 * 
	 * @return Match
	 */
	public Game getMatch() {
		return Match;
	}

	/**
	 * Getter for Match
	 * 
	 * @param match
	 */
	public void setMatch(Game match) {
		Match = match;
	}

	/**
	 * Zoom function
	 * 
	 * @param e is 1.0 if mouseWheel pulled
	 */
	public void zoom(double e) {
		if (zoom + e * 0.2 > 1) {
			zoom += e * 0.2;
			if (Constants.xDefault - (int) (e * 0.2 * mouseX) <= 0 && (Constants.xDefault - (int) (e * 0.2 * mouseX)
					+ Match.getMatch()[0].length * Constants.CellSize * zoom > Match.getMatch()[0].length
							* Constants.CellSize)) {
				Constants.xDefault += -(int) (e * 0.2 * mouseX);
			} else {
				if (Constants.xDefault - (int) (e * 0.2 * mouseX) > 0) {
					Constants.xDefault = 0;
				} else {
					Constants.xDefault += -(int) (e * 0.2 * mouseX)
							+ Match.getMatch()[0].length * Constants.CellSize * zoom
							- Match.getMatch()[0].length * Constants.CellSize;
				}
			}
			if (Constants.yDefault - (int) (e * 0.2 * mouseY) <= 0 && (Constants.yDefault - (int) (e * 0.2 * mouseY)
					+ Match.getMatch().length * Constants.CellSize * zoom > Match.getMatch().length
							* Constants.CellSize)) {
				Constants.yDefault += -(int) (e * 0.2 * mouseY);
			} else {
				if (Constants.yDefault - (int) (e * 0.2 * mouseY) > 0) {
					Constants.yDefault = 0;
				} else {
					Constants.yDefault += -(int) (e * 0.2 * mouseY)
							+ Match.getMatch().length * Constants.CellSize * zoom
							- Match.getMatch().length * Constants.CellSize;
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
		this.setMatch(new Game(SIZE, (int) ((SIZE) * Constants.ratio)));
		IGui.setRandomBombs(Match);
		IGui.setSurrBombsAll(Match);
		Constants.CellSize = Math.min(Constants.Horizontal, Constants.Vertical) / SIZE;
		surface.setSize(Match.getMatch()[0].length * Constants.CellSize, Match.getMatch().length * Constants.CellSize);
		surface.setLocation(displayWidth - width >> 1, displayHeight - height >> 1);
		Constants.bomb = loadImage("bomb.png");
		Constants.minefield = loadImage(Constants.dataString);
		Constants.minefield.filter(THRESHOLD, 0.55f);
		Constants.Stuck = (int) (0.2 * Constants.CellSize);
		genMinefieldFromImage();
		IGui.setSurrBombsAll(Match);

	}

	/**
	 * Creates minefield from a given picture
	 */
	public void genMinefieldFromImage() {

		for (int i = 0; i < Match.getMatch().length; i++) {
			for (int j = 0; j < Match.getMatch()[0].length; j++) {
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
					Match.getMatch()[i][j].setIsBomb();
				} else {
					Match.getMatch()[i][j].setIsNotABomb();
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
		return Constants.xDefault + n * (int) (Constants.CellSize * zoom);
	}

	/**
	 * Translator for Y-coordinate to index m
	 * 
	 * @param m y-position
	 * @return y-coordinate
	 */
	public int getYCoordinates(int m) {
		return Constants.yDefault + m * (int) (Constants.CellSize * zoom);
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
			if ((Constants.xDefault + mouseX - pressedPos[1] <= 0) && (Constants.xDefault + mouseX - pressedPos[1]
					+ Match.getMatch()[0].length * Constants.CellSize * zoom >= Match.getMatch()[0].length
							* Constants.CellSize)) {
				Constants.xDefault += mouseX - pressedPos[1];
			} else {
				if (Constants.xDefault + mouseX - pressedPos[1] > 0) {
					Constants.xDefault = 0;
				}
			}
			if ((Constants.yDefault + mouseY - pressedPos[0] <= 0)
					&& (Constants.yDefault + Match.getMatch().length * Constants.CellSize * zoom + mouseY
							- pressedPos[0] >= Match.getMatch().length * Constants.CellSize)) {
				Constants.yDefault += mouseY - pressedPos[0];
			} else {
				if (Constants.yDefault + mouseY - pressedPos[0] > 0) {
					Constants.yDefault = 0;
				}
			}
			pressedPos[1] = mouseX;
			pressedPos[0] = mouseY;
		}

		if (keyPressed) {
			switch (key) {
			case 'r':
				Match.reset();
				zoom = 1;
				break;
			case 'n':
				Match.renew();
				break;
			case 'q':
				exit();
				break;
			case '+':
				zoom = zoom + (float) 0.2;
				break;
			case '-':
				zoom = zoom - (float) 0.2;
				break;
			}
		}

		if (Constants.restart) {
			if (Case == 1 && mouseButton == LEFT) {
				Constants.restart = false;
				Match.setRunning(true);
				this.setMatch(new Game(Match.getMatch().length, Match.getMatch()[0].length));
				IGui.setRandomBombs(Match);
				IGui.setSurrBombsAll(Match);
				zoom = 1;
				Constants.xDefault = 0;
				Constants.yDefault = 0;
				delay(1000);

			}
		} else {
			if (Match.isRunning() == false) {
				Match.draw(this);
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
						Constants.success = Match.leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
								getXIndexFromCoordinates(mouseX) - 1);
					}
					if (mouseButton == RIGHT) {
						Match.getMatch()[getYIndexFromCoordinates(mouseY) - 1][getXIndexFromCoordinates(mouseX) - 1]
								.setMarkedAsBomb(!(Match.getMatch()[getYIndexFromCoordinates(mouseY)
										- 1][getXIndexFromCoordinates(mouseX) - 1].isMarkedAsBomb()));
					}
					break;
				case 2:
					if (mouseButton == LEFT) {
						Constants.success = Match.leftClicked(this, getYIndexFromCoordinates(mouseY) - 1,
								getXIndexFromCoordinates(mouseX) - 1);
					}
					break;
				}
				Match.draw(this);
			}
		}
		if (keyPressed && key == 's' && Match.isRunning()) {
			if (TmpMatch == null) {
				TmpMatch = new Game(Match.getMatch().length, Match.getMatch()[0].length);
			} else {
				for (int i = 0; i < Match.getMatch().length; i++) {
					for (int j = 0; j < Match.getMatch()[0].length; j++) {
						TmpMatch.getMatch()[i][j] = Match.getMatch()[i][j].copy();
					}
				}
			}
		}
		if (keyPressed && key == 'l' && Match.isRunning()) {
			if (TmpMatch != null) {
				for (int i = 0; i < Match.getMatch().length; i++) {
					for (int j = 0; j < Match.getMatch()[0].length; j++) {
						Match.getMatch()[i][j] = TmpMatch.getMatch()[i][j].copy();
					}
				}
				IGui.setSurrBombsAll(TmpMatch);
			}
		}
	}

}
