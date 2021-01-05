package project;

import java.io.FileWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
 * TODO: Fix dota2 and Rainbow 6 not scraping any data, links probably
 * 		 Some links fetched directly, probably better to use the comment href
 */

public class Scraper{

	private ArrayList<String> title;
	private ArrayList<String> flair;
	private ArrayList<String> post_type; 
	private ArrayList<String> post_links;
	
	private String[] links = 
		{		"https://old.reddit.com/r/DotA2/top/?sort=top&t=week",				//Dota 2
				"https://old.reddit.com/r/wow/top/?sort=top&t=week",				//World of Warcraft
				"https://old.reddit.com/r/hearthstone/top/?sort=top&t=week",		//Hearthstone
				"https://old.reddit.com/r/GlobalOffensive/top/?sort=top&t=week", 	//CS:GO
				"https://old.reddit.com/r/Overwatch/top/?sort=top&t=week",			//Overwatch
				"https://old.reddit.com/r/FortNiteBR/top/?sort=top&t=week",			//Fortnite: Battle Royale
				"https://old.reddit.com/r/Rainbow6/top/?sort=top&t=week",			//Rainbow 6 Siege
				"https://old.reddit.com/r/PUBATTLEGROUNDS/top/?sort=top&t=week",	//Player Unknown Battleground
				"https://old.reddit.com/r/modernwarfare/top/?sort=top&t=week",		//CoDMW
				"https://old.reddit.com/r/apexlegends/top/?sort=top&t=week",		//Apex Legends
						
	};

	private String url;
	private String csv_name;
	private int week;
	
	public Scraper(int week) {
		this.week = week;
		title = new ArrayList<>();
		flair = new ArrayList<>();
		post_type = new ArrayList<>();
		post_links = new ArrayList<>();
		
	}
	
	public void start() {
		
		
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
		
		for(int i = 0;i<name.length;i++) {
			url = links[i];
			csv_name = name[i]+"_week_"+week+".csv";
			System.out.println("Scraping "+name[i]);
			scrape();
			title.clear();
			flair.clear();
			post_type.clear();
			post_links.clear();
		}
		
	}	
	
	private void scrape() {
		
		try {
			final Document document = Jsoup.connect(url).get();	
			
			ArrayList<Integer> ads = new ArrayList<>();
			
			//detecting ads
			
			int n = 1;
			for(Element row: document.select(
					"ul.buttons")) {
				if(row.select(".buttons").text().equals("")) {
					n++;
					continue;
				}else {
					String t = row.select(".buttons").text();
					if(t.contains("promoted")) {
						ads.add(n);
					}
					n++;
				}
			}
			
			//scraping title
			
			n = 1;	
			for(Element row: document.select(
					"a.title")) {
				if(row.select(".title").text().equals("")) {
					title.add("[empty]");
					n++;
					continue;
				}
				else if(ads.contains(n)) {
					n++;
					continue;
				
				}

				else {
					String t = row.select(".title").text();
					t = t.replace(",", ""); 				//to avoid the data to be divided in the csv
					title.add(t);
			
				}
				n++;
			}
			
			
			//scraping flair
			
//			n = 1;
			
			for(Element row: document.select(
					"span.linkflairlabel")) {
				if(row.select(".linkflairlabel").text().equals("")) {
					flair.add("[empty]");
//					n++;
					continue;
				}
				else {
					String t = row.select(".linkflairlabel").text();
					flair.add(t);
				
				}
//				n++;
			}
			
			//scraping post_type
			
			n = 1;
			
			for(Element row: document.select(
					"span.domain")) {
				if(row.select(".domain").text().equals("")) {
					post_type.add("[empty]");
					n++;
					continue;
				}
				else if(ads.contains(n)) {
					n++;
					continue;				
				}
				else {
					String t = row.select(".domain").text();
					post_type.add(t);
				}
				n++;
			}
			
			//get post links
			
			n=1;
			
			for(Element row: document.select(
					"li.first")) {
				if(row.select("a").attr("href").toString().equals("")) {
					post_links.add("[empty]");
					n++;
					continue;
				}

				else {
					String t = row.select("a").attr("href").toString();
					post_links.add(t);
				}
				n++;
			}
			
			
			
			FileWriter file = new FileWriter(csv_name);
			
			System.out.println(title.size()+" "+flair.size()+" "+post_type.size()+" "+post_links.size());
			
			String text = "Points,Title,Flair,Post Type,Post Links\n";
			int x = 25;
			sanitize();
			for(int i = 0;i<x;i++) {
				text += 25-i+","+title.get(i)+","+flair.get(i)+","+post_type.get(i)+","+post_links.get(i)+"\n";
			}
			file.write(text);
			file.close();
			
			
			
//			for(int i = 0;i<25;i++) {
//				System.out.println(title.get(i)+" | "+flair.get(i)+" | "+post_type.get(i)+" | "+post_links.get(i));
//			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int min() {
		int x = title.size();
		
		if(x>flair.size()) {
			if(x>flair.size() || x>post_type.size() || x>post_links.size()) {
				if(x>post_type.size() || x>post_links.size()) {
					if(x>post_links.size()) {
						x = post_links.size();
					}else {
						x = post_type.size();
					}
				}else {
					x = flair.size();
				}
			}else {
				x = flair.size();
			}
		}
		return x;
	}
	
	private void sanitize() {
		
		post_links.removeIf(links -> (links.contains("https://old.reddit.com/user")));
		
		if(title.size()<25) {
			title = fill(title);
		}
		if(flair.size()<25) {
			flair = fill(flair);
		}
		if(post_links.size()<25) {
			post_links = fill(post_links);
		}
		if(post_type.size()<25) {
			post_type = fill(post_links);
		}
		
	}
	
	private ArrayList<String> fill(ArrayList<String> arr){
		ArrayList<String> temp = arr;
		
		while(arr.size()<25) {
			arr.add("");
		}
		return temp;
		
	}
	
	public static void main(String[] args) {
		
		int week = 11;
		
		Scraper scrape = new Scraper(week);
		scrape.start();
		
	}
	
}
