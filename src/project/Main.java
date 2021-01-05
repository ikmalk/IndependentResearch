package project;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	/*
	 * TO DO:
	 * -Create scraper for all 10 subreddits:
	 * 		-r/dota2 (done)
	 * 		-r/GlobalOffensive
	 * 		-r/wow
	 * 		-r/Overwatch
	 * 		-r/hearthstone
	 * 		-r/Rainbow6
	 * 		-r/PUBATTLEGROUNDS
	 * 		-r/DestinyTheGame
	 * 		-r/FortNiteBR
	 * 		-r/apexlegends
	 * 
	 * -create a filter for flair
	 * -create a filter for links
	 * -remove all comma from title array list
	 * 
	 */
	
	
	
	public static void main(String[] args) {
		
//		String[] flair = {" Fluff",
//				"Discussion",
//				"Arts and Miscellaneous",
//				"Clips and Highlights",
//				"News and Announcements",
//				"Guides and Tips",
//				"Bugs and Reports",
//				"Suggestion and Feedback",
//				"Other"
//				};
//		
//		String[] type = {"Text",
//				"Image",
//				"Video",
//				"Other"
//		};
//		
//		int n = 0;
//		for(int i = 0;i<flair.length;i++) {
//			
//			for(int j = 0;j<type.length;j++) {
//				System.out.print(""+i+""+j+", ");
//				n++;
//			}
//			
//		}

		int[][] Reset = {{00, 0}, {01, 0}, {02, 0}, {03, 0}, {10, 0}, {11, 0}, {12, 0}, {13, 0}, {20, 0}, {21, 0}, 
				{22, 0}, {23, 0}, {30, 0}, {31, 0}, {32, 0}, {33, 0}, {40, 0}, {41, 0}, {42, 0}, {43, 0}, {50, 0}, {51, 0}, 
				{52, 0}, {53, 0}, {60, 0}, {61, 0}, {62, 0}, {63, 0}, {70, 0}, {71, 0}, {72, 0}, {73, 0}, {80, 0}, {81, 0}, 
				{82, 0}, {83, 0}};
		
		System.out.println(Reset.length);
		System.out.println(Reset[0].length);
		
	}

}
