package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sanitize {

	public static void main(String[] args) {
		
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
		ArrayList<ArrayList<String>> flairList = new ArrayList<>();
		ArrayList<ArrayList<String>> typeList = new ArrayList<>();
		
		
		ArrayList<String> flair = new ArrayList<>();
		ArrayList<String> type = new ArrayList<>();
		
		try {
			FileWriter typeFile = new FileWriter("Type.csv", true);
			FileWriter flairFile = new FileWriter("Flair.csv", true);
			for(int i = 0;i<name.length;i++) {
			
				String fileName = name[i]+"_week_";
				int week = 6;
				
				flair.add(name[i]);
				type.add(name[i]);
				
				for(int j = week;j<12;j++) {
					
					BufferedReader scanner = new BufferedReader(new FileReader(fileName+j+".csv"));
	
					String s = scanner.readLine();
					s = scanner.readLine();
					while(s != null) {
						
						String[] split = s.split(",");
	//					flair.add(split[2]);
	//					type.add(split[3]);
						flair = add(flair, split[2]);
						type = add(type, split[3]);
						s = scanner.readLine();
						
					}
									
					
					scanner.close();
					
					
				}
				flairFile.append(write(flair)); 
				typeFile.append(write(type));
				flair.clear();
				type.clear();
			}
			flairFile.close();
			typeFile.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static String write(ArrayList<String> arr) {
		String str = "";
		for(int i = 0;i<arr.size();i++) {
			str += arr.get(i)+",";
		}
		str += "\n";
		return str;
	}
	
	public static ArrayList<String> add(ArrayList<String> arr, String s){
		if(!arr.contains(s)) {
			arr.add(s);
			return arr;
		}
		return arr;
	}

}
