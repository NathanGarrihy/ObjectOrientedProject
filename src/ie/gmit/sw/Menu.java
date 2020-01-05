package ie.gmit.sw;

public class Menu {

	public Menu() {
		System.out.println("Language Detector - developed by Nathan Garrrihy");
		System.out.println("Student - Galway-Mayo Institute of Technology");
		System.out.println("---------------------------------------");
	}

	public void showMenu() throws Throwable {
		System.out.println("-----------------------File Names------------------------");
		System.out.println(Runner.getWili());
		System.out.println(Runner.getQuery());		
		System.out.println("--------------------------------------------");
		}
	
	public void choiceMenu() {
		System.out.println("--------------------------------------------");
		System.out.println("Please enter: ");
		System.out.println("--------------------------------------------");
		System.out.println("1) to Change Wili file");
		System.out.println("2) to Change the query file");
		System.out.println("3) to Change the Kmer number");
		System.out.println("4) To Re-Run the language detector");
		System.out.println("-1) to terminate the program");
		System.out.println("--------------------------------------------");
	}
}