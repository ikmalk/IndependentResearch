package project;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scraper_test {

	protected ArrayList<String> title;
	protected ArrayList<String> flair;
	protected ArrayList<String> post_type; 
	protected ArrayList<String> post_links;
	
	private String url;
	
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
	
	public Scraper_test() {
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
			System.out.println("----"+name[i]+"----");
			scrape();
			show();
			flair.clear();
			post_type.clear();
		}
	}
	
	public static void main(String[] args) {
		new Scraper_test().start();
	}
	
	public void scrape() {
		
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
					if(!post_type.contains(t))
						post_type.add(t);
				}
				n++;
			}
			
			n = 1;
			for(Element row: document.select(
					"span.linkflairlabel")) {
				if(row.select(".linkflairlabel").text().equals("")) {
					flair.add("[empty]");
					n++;
					continue;
				}
				
				else {
					String t = row.select(".linkflairlabel").text();
					if(!flair.contains(t))
						flair.add(t);
				}
				n++;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void show() {
		
		System.out.println("--Flair--");
		for(String str: flair)
			System.out.println(str);
		System.out.println("\n--Type--");
		for(String str: post_type)
			System.out.println(str);
		System.out.println("\n\n");
		
	}
	
}
