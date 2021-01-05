package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Filter {

	
	/**
	 * Converts all the raw data name and links into a categorized name and
	 * put it in output_1 folder
	 */
	
	
	/*
	 * Flair:
	1 - Fluff
	2 - Discussion
	3 - Arts and Miscellaneous
	4 - Clips and Highlights
	5 - News and Announcements
	6 - Guides and Tips
	7 - Bugs and Reports
	8 - Suggestion and Feedback
	9 - Other
	
	Type:
	1 - Text
	2 - Image
	3 - Video
	4 - Other
	
	Flair and Type:
	00 -  Fluff & Text
	01 -  Fluff & Image
	02 -  Fluff & Video
	03 -  Fluff & Other
	10 - Discussion & Text
	11 - Discussion & Image
	12 - Discussion & Video
	13 - Discussion & Other
	20 - Arts and Miscellaneous & Text
	21 - Arts and Miscellaneous & Image
	22 - Arts and Miscellaneous & Video
	23 - Arts and Miscellaneous & Other
	30 - Clips and Highlights & Text
	31 - Clips and Highlights & Image
	32 - Clips and Highlights & Video
	33 - Clips and Highlights & Other
	40 - News and Announcements & Text
	41 - News and Announcements & Image
	42 - News and Announcements & Video
	43 - News and Announcements & Other
	50 - Guides and Tips & Text
	51 - Guides and Tips & Image
	52 - Guides and Tips & Video
	53 - Guides and Tips & Other
	60 - Bugs and Reports & Text
	61 - Bugs and Reports & Image
	62 - Bugs and Reports & Video
	63 - Bugs and Reports & Other
	70 - Suggestion and Feedback & Text
	71 - Suggestion and Feedback & Image
	72 - Suggestion and Feedback & Video
	73 - Suggestion and Feedback & Other
	80 - Other & Text
	81 - Other & Image
	82 - Other & Video
	83 - Other & Other
	 */
	
	String[] flair = {" Fluff",
			"Discussion",
			"Arts and Miscellaneous",
			"Clips and Highlights",
			"News and Announcements",
			"Guides and Tips",
			"Bugs and Reports",
			"Suggestion and Feedback",
			"Other"
			};
	
	String[] type = {"Text",
			"Image",
			"Video",
			"Other"
	};
	
	//Flair
	ArrayList<String> fFluff;
	ArrayList<String> fDiscussion;
	ArrayList<String> fArts;
	ArrayList<String> fClips;
	ArrayList<String> fNews;
	ArrayList<String> fGuides;
	ArrayList<String> fBugs;
	ArrayList<String> fSuggest;
	ArrayList<String> fOther;
	
	//Type
	ArrayList<String> tText;
	ArrayList<String> tImage;
	ArrayList<String> tVideo;
	ArrayList<String> tOther;
	
	public Filter() {
		String[] name = 
			{		"Dota_2",
					"WoW",
					"HS",
					"CSGO",
					"Overwatch",
					"Fortnite",
					"R6",
					"PUBG",
					"CoDMW",
					"Apex"
			};
		
		
		fFluff = new ArrayList<>();
		fDiscussion = new ArrayList<>();
		fArts = new ArrayList<>();
		fClips = new ArrayList<>();
		fNews = new ArrayList<>();
		fGuides = new ArrayList<>();
		fBugs = new ArrayList<>();
		fSuggest = new ArrayList<>();
		fOther = new ArrayList<>();
		
		tText = new ArrayList<>();
		tImage = new ArrayList<>();
		tVideo = new ArrayList<>();
		tOther = new ArrayList<>();
		
		fillArrayList();
		printCheck();
		
		
		for(int i = 0;i<name.length;i++) {
			filterData(name[i]);
		}
		
	}
	
	private void filterData(String name) {
		
		/**					what it gonna looks like in csv
		 * 					Example:
		 * 					
		 * 					FlairFile
		 * 					|Week	|Flair	|FlairPoint		|		
		 * 					---------------------------------
		 * 					|6		|Fluff	|354			|
		 * 
		 * 					TypeFile
		 * 					|Week	|Type	|TypePoint		|		
		 * 					---------------------------------
		 * 					|6		|Image	|200			|
		 * 
		 * 					
		 * 					FLairAndTypeFile
		 * 					|Week	|Flair&Type		|Point		|		
		 * 					---------------------------------
		 * 					|6		|Image&Fluff	|146		|
		 */
		
		
		
		
		try {			
			FileWriter writer1 = new FileWriter("/data/"+name+"Flair.csv", true);
			FileWriter writer2 = new FileWriter("/data/"+name+"Type.csv", true);
			FileWriter writer3 = new FileWriter("/data/"+name+"Flair&Type.csv", true);
			
			writer1.write("Week,Flair,Flair Points\n");
			writer2.write("Week,Type,Type Points\n");
			writer3.write("Week,Flair & Type, Points\n");
			
			int[] flairPoints = new int[9];
			for(int i = 0;i<flairPoints.length;i++) {
				flairPoints[i] = 0;
			}
			
			
			int[] resetType = {0,0,0,0};
			int[] typePoints = resetType;
			
			
			//For reference, refer to the top comment
			int[][] flairTypePointsReset = {{00, 0}, {01, 0}, {02, 0}, {03, 0}, {10, 0}, {11, 0}, {12, 0}, {13, 0}, {20, 0}, {21, 0}, 
					{22, 0}, {23, 0}, {30, 0}, {31, 0}, {32, 0}, {33, 0}, {40, 0}, {41, 0}, {42, 0}, {43, 0}, {50, 0}, {51, 0}, 
					{52, 0}, {53, 0}, {60, 0}, {61, 0}, {62, 0}, {63, 0}, {70, 0}, {71, 0}, {72, 0}, {73, 0}, {80, 0}, {81, 0}, 
					{82, 0}, {83, 0}};
			
			int[][] flairTypePoints = flairTypePointsReset;
			
			
			
			for(int j = 6;j<12;j++) {
				
				//reading file
				
				BufferedReader reader = new BufferedReader(new FileReader("output_1/"+name+"_week_"+j+".csv"));
				String s = reader.readLine();
				s = reader.readLine();
				while(s!=null) {
					String[] split = s.split(",");
					int x = checkFlair(split);
					
					if(x>-1)
						flairPoints[x] += Integer.parseInt(split[0]);
					
					x = checkType(split);
					typePoints[x] += Integer.parseInt(split[0]);
					
					x = checkFlairType(split);
					if(x>-1)
						flairTypePoints[getPosition(x)][1] += Integer.parseInt(split[0]);
										
					
					s = reader.readLine();
				}
				
				
				
				//////////////////////////////////////////////////////
				//writing file
				
				for(int i = 0;i<flairPoints.length;i++) {
					String str = j+","+flair[i]+","+flairPoints[i]+",\n";
					writer1.append(str);
				}
				
				for(int i = 0;i<typePoints.length;i++) {
					String str = j+","+type[i]+","+typePoints[i]+",\n";
					writer2.append(str);
				}
				
				for(int i = 0;i<flairPoints.length;i++) {
					flairPoints[i] = 0;
				}

				for(int i = 0;i<typePoints.length;i++) {
					typePoints[i] = 0;
				}
				
				
				int[][] maxFlairType = getTenMax(flairTypePoints);
				for(int i = 0;i<maxFlairType.length;i++) {
					String str = j+","+flair[maxFlairType[i][0]/10]+" & "+type[maxFlairType[i][0]%10]+","+maxFlairType[i][1]+",\n";
					writer3.append(str);
				}
				
				for(int i = 0;i<flairTypePoints.length;i++) {
					flairTypePoints[i][1] = 0;
				}
				
				reader.close();
				
				System.out.println(typePoints[1]);
				System.out.println(flairTypePoints[2][1]);
				System.out.println(flairPoints[1]);
			}
			
			writer1.close();
			writer2.close();
			writer3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int checkType(String[] str) {
		String type = str[3];
		if(tImage.contains(type)) {
			return 1;
		}
		else if(tVideo.contains(type)) {
			return 2;
		}
		else if(type.contains("self.")) {
			return 0;
		}
		return 3;
	}
	
	private int checkFlair(String[] str) {
		
		String flair = str[2];
		
		if(fFluff.contains(flair)) {
			return 0;
		}
		else if(fDiscussion.contains(flair)) {
			return 1;
		}
		else if(fArts.contains(flair)) {
			return 2;	
			}
		else if(fClips.contains(flair)) {
			return 3;
		}
		else if(fNews.contains(flair)) {
			return 4;
		}
		else if(fGuides.contains(flair)) {
			return 5;
		}
		else if(fBugs.contains(flair)) {
			return 6;
		}
		else if(fSuggest.contains(flair)) {
			return 7;
		}
		else if(fOther.contains(flair)) {
			return 8;
		}
		return -1;
		
	}
	
	private int checkFlairType(String[] str) {
		
		int val = 10 * checkFlair(str);
		val += checkType(str);
		return val;
			
	}
	
	private int getPosition(int x) {
		
		int[] val = {00, 01, 02, 03, 10, 11, 12, 13, 20, 21, 22, 23, 30, 31, 32, 
				33, 40, 41, 42, 43, 50, 51, 52, 53, 60, 61, 62, 63, 70, 71, 72, 
				73, 80, 81, 82, 83};
		
		for(int i = 0;i<val.length;i++) {
			if(val[i] == x)
				return i;
		}
		return -1;
		
	}
	
	private int[][] getTenMax(int[][] val) {
		
		if(val.length < 10)
			return val;
		
		int[][] value = new int[10][2];
		
		for(int i = 0;i<10;i++) {
			value[i] = val[i];
		}
		
		for(int i = 10;i<val.length;i++) {
			int x = val[i][1];
			int temp = getMinPos(value);
			
			if(x>value[temp][1])
				value[temp] = val[i];
		}
		
		return value;
		
	}
	
	private int getMinPos(int[][] val) {
		int x = 10000;
		int pos = 0;
		
		for(int i = 0;i<val.length;i++) {
			if(x>val[i][1]) {
				x = val[i][1];
				pos = i;
			}
		}
		return pos;
	}
	
	
	/*
	 * Extract the all the flair names and types from the flair.csv and type.csv file to 
	 * properly label all the flair name into its categories
	 * 
	 * e.g of fluff flair name in every subreddit: Fluff, Fluff | Esports, Humor / Meme, Meme, Shitpost, Humor, HUMOR, Dark Humor
	 *
	 * 
	 */
	
	private void fillArrayList(){
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("flair3.csv"));
			int x = 1;
			String s = reader.readLine();
			while(s!=null) {
				String[] split = s.split(",");
				insert(split,x);
				x++;
				s = reader.readLine();
			}
			reader = new BufferedReader(new FileReader("type3.csv"));
			s = reader.readLine();
			while(s!=null) {
				String[] split = s.split(",");
				insert(split,x);
				x++;
				s = reader.readLine();
			}
			reader.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void insert(String[] str, int x) {
		
		switch(x) {
		case 1:{
			fFluff.addAll(Arrays.asList(str));
			break;
		}
		case 2:{
			fDiscussion.addAll(Arrays.asList(str));
			break;	
				}
		case 3:{
			fArts.addAll(Arrays.asList(str));
			break;
		}
		case 4:{
			fClips.addAll(Arrays.asList(str));
			break;
		}
		case 5:{
			fNews.addAll(Arrays.asList(str));
			break;
		}
		case 6:{
			fGuides.addAll(Arrays.asList(str));
			break;
		}
		case 7:{
			fBugs.addAll(Arrays.asList(str));
			break;	
				}
		case 8:{
			fSuggest.addAll(Arrays.asList(str));
			break;
		}
		case 9:{
			fOther.addAll(Arrays.asList(str));
			break;
		}
		case 10:{
			tImage.addAll(Arrays.asList(str));
			break;
		}
		case 11:{
			tVideo.addAll(Arrays.asList(str));
			break;
		}
		}
		
	}
	
	private void printCheck() {
		System.out.println(fFluff.toString());
		System.out.println(fDiscussion.toString());
		System.out.println(fArts.toString());
		System.out.println(fClips.toString());
		System.out.println(fNews.toString());
		System.out.println(fGuides.toString());
		System.out.println(fBugs.toString());
		System.out.println(fSuggest.toString());
		System.out.println(fOther.toString());
		System.out.println(tImage.toString());
		System.out.println(tVideo.toString());
	}
	
	public static void main(String[] args) {
		
		new Filter();
		
	}

}
