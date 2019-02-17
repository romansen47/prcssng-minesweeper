package minesweeper;

import minesweeper.main.Gui;
import minesweeper.conf.Constants;

/**
 * @author ro
 *
 */

public class Main {

	public static void main(String[] args) {
		
		String[] Pics=new String[1];{
			Pics[0]="rb.png";
			
		}
		
		//Random r=new Random();
		Constants.dataString=Pics[0]; //[6];   //

		Constants.intensity=60;
		Gui.setSIZE(80);
		//System.out.println("hi");
		
		Gui GUI1=new Gui();
		GUI1.run("minesweeper.main.Gui");
		
	}

}

