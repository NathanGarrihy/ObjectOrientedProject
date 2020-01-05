package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;

public class Runner {
	
	private static Scanner console = new Scanner(System.in);

	private static String wili;
	private static String query;
	private static int choice =4;
	private static int nGram = 3;
	private static int nextNGram;

	public static void main(String[] args) throws Throwable {
		//	Starting Menu and file choice for user
		Menu menu = new Menu();
		setWili();
		setQuery();
		menu.showMenu();
		
		do {
			nextNGram = nGram + 1;
			if (choice == 4) {	//	Compares the files and returns the result
		Parser p = new Parser(getWili(), nGram);
		Parser p2 = new Parser(getWili(), nextNGram);

		Database db = new Database();
		Database db2 = new Database();
		p.setDb(db);
		p2.setDb(db2);
		
		Thread t = new Thread(p);
		Thread t2 = new Thread( p2);
		t.start();
		t2.start();
		t.join();
		t2.join();
		
		db.resize(300);
		db2.resize(300);
		
		System.out.println("Checking language with kmer of " + nGram);
		p.analyseQuery(getQuery());
		System.out.println("Checking language with kmer of " + nextNGram);
		p2.analyseQuery(getQuery());
			}
			//	Menu of Options for UI
			menu.choiceMenu();
			choice = console.nextInt();
			
			if(choice == 1) {
				//	Change Wili File
				setWili();
			}
			else if (choice == 2) {
				//	Change Query File
				setQuery();
			}
			else if (choice == 3) {
				//	Change Kmer Number
				setKmer();
			}
			else if (choice == 4) {
				//	Change Wili File
				choice = 4;
			}
			else {
				System.out.println("Ïnvalid choice, please try again");
			}
			
		}while(choice != -1);
		
	}	//	end of main
	
	//	Set Wili File.
	public static void setWili(){
		String input = "";
		boolean fileReal = false;
		
		System.out.println("---------------------Wili----------------------");
		
		while (!fileReal) {
			System.out.println("Please enter the Wili file name (Including .txt)\n");
			input = console.next();
			if (new File(input).isFile()) {	// file exists
				fileReal = true;
			} else {
				System.out.println("No file with that name can be found, please try again");
			}
		}
		wili = input;
	}
	
	//	Get wili File
	public static String getWili() {
		return wili;
	}
	
	//	Set Query File
	public static void setQuery() {
		String input = "";
		boolean fileReal = false;
		
		System.out.println("---------------------Query-----------------------");
		
		
		while (!fileReal) {
			System.out.println("Please enter the Query file name (Including .txt)\n");
			input = console.next();
			if (new File(input).isFile()) { // checks if file exists
				fileReal = true;
			} else {
				System.out.println("No file with that name can be found, please try again");
			}
		}
		query = input;
	}
	
	//	Get Query File
	public static String getQuery() {
		return query;
	}
	
	//	Get Kmer
	public static int getKmer() {
		return nGram;
	}

	//	Set Kmer
	public static void setKmer() {
		int i=0;
		while (i <2 && i > 6) {
		System.out.println("Enter new kMer number: (2-6)");
		i = console.nextInt();
		}
		nGram = i;
	}
}