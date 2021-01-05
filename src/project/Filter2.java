package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Add up all the points from each week 
 * and store it in output_2 folder
 */


public class Filter2 {

	private String[] flair = {" Fluff",
			"Discussion",
			"Arts and Miscellaneous",
			"Clips and Highlights",
			"News and Announcements",
			"Guides and Tips",
			"Bugs and Reports",
			"Suggestion and Feedback",
			"Other"
			};
	private String[] type = {"Text",
			"Image",
			"Video",
			"Other"
	};
	
	private ArrayList<String> flairTypeName;
	private ArrayList<Integer> flairTypePoints;
	
	public Filter2() {
		
		flairTypeName = new ArrayList<>();
		flairTypePoints = new ArrayList<>();
		
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
		
		for(int i = 0; i < name.length; i++) {
			filter(name[i]);
		}
	}
	
	private void filter(String name) {
				
		
		try {
			
			int[] flairPoints = new int[flair.length];
			for(int i = 0;i < flairPoints.length; i++) {
				flairPoints[i] = 0;
			}
			
			int[] typePoints = new int[type.length];
			for(int i = 0;i < typePoints.length; i++) {
				typePoints[i] = 0;
			}
			
			
			BufferedReader reader = new BufferedReader(new FileReader("output_1/"+name+"Flair"+".csv"));
			String str = reader.readLine();
			str = reader.readLine();
			while(str!=null) {
				
				String[] split = str.split(",");
				flairPoints[getIndex(flair, split[1])] += Integer.parseInt(split[2]);
				str = reader.readLine();
			}
			
			reader.close();
			
			BufferedReader reader1 = new BufferedReader(new FileReader("output_1/"+name+"Type"+".csv"));
			str = reader1.readLine();
			str = reader1.readLine();
			while(str!=null) {
				
				String[] split = str.split(",");
				typePoints[getIndex(type, split[1])] += Integer.parseInt(split[2]);
				str = reader1.readLine();
			}
			
			reader.close();
			
			BufferedReader reader2 = new BufferedReader(new FileReader("output_1/"+name+"Flair&Type"+".csv"));
			str = reader2.readLine();
			str = reader2.readLine();
			while(str!=null) {
				
				String[] split = str.split(",");
				insert(split[1], Integer.parseInt(split[2]));
				str = reader2.readLine();
			}
			
			reader2.close();
			
			FileWriter writer1 = new FileWriter("output_2/"+name+"FlairCombined.csv", true);
			FileWriter writer2 = new FileWriter("output_2/"+name+"TypeCombined.csv", true);
			FileWriter writer3 = new FileWriter("output_2/"+name+"Flair&TypeCombined.csv", true);
			
			writer1.write("Flair,Points\n");
			for(int i = 0; i < flair.length; i++) {
				str = flair[i] + "," + flairPoints[i] + ",\n";
				writer1.append(str);
			}
			
			writer2.write("Type,Points\n");
			for(int i = 0; i < type.length; i++) {
				str = type[i] + "," + typePoints[i] + ",\n";
				writer2.append(str);
			}
			
			getTenMax();
			
			writer3.write("Flair&Type,Points\n");
			for(int i = 0; i < flairTypeName.size(); i++) {
				str = flairTypeName.get(i) + "," + flairTypePoints.get(i) + ",\n";
				writer3.append(str);
			}
			

			writer1.close();
			writer2.close();
			writer3.close();
			
			flairTypeName.clear();;
			flairTypePoints.clear();;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int getIndex(String[] arr, String str) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i].equals(str))
				return i;
		}
		return -1;
	}
	
	private void insert(String name, int points) {
		
		if(flairTypeName.contains(name)) {
			int x = flairTypeName.indexOf(name);
			flairTypePoints.set(x, flairTypePoints.get(x)+points);
		}
		else {
			flairTypeName.add(name);
			flairTypePoints.add(points);
		}
		
	}
	
	private void getTenMax() {
		
		int n = flairTypeName.size();
		while(n > 10) {
			int x = getMinIndex();
			flairTypeName.remove(x);
			flairTypePoints.remove(x);
			n = flairTypeName.size();
		}
	}
	
	private int getMinIndex() {
		
		int x = 0;
		int min = 100000;
		
		for(int i = 0; i < flairTypePoints.size() ; i++) {
			if((int)flairTypePoints.get(i)<min) {
				min = (int)flairTypePoints.get(i);
				x = i;
			}
		}
		
		return x;
		
	}
	public static void main(String[] args) {		
		new Filter2();
	}

}
