package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Parser implements Runnable {
	
	private Database db = null;
	private String file;
	private int k;

	public Parser(String file, int k) {
		this.file = file;
		this.k = k;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	//	Overridden Runnable method
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if (record.length != 2)
					continue;
				parse(record[0], record[1]);
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//	Parses text file
	public void parse(String text, String lang, int... ks) {
		Language l = Language.valueOf(lang);

		for (int i = 0; i < text.length() - k; i++) {
			// get 3-mer
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, l);
		}
	}

	//	Finalizes checking the language which the query file was written in
	public void analyseQuery(String file) throws IOException {

		int kmers = 0;
		int freq = 1;

		String queryFile;

		Map<Integer, Ngram> query = new HashMap<>();

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		while ((queryFile = br.readLine()) != null) {

			for (int i = 0; i <= queryFile.length() - k; i++) {
				CharSequence charSeq = queryFile.substring(i, i + k);
				kmers = charSeq.hashCode();

				if (query.containsKey(kmers)) {
					freq += query.get(kmers).getFrequency();
				}

				Ngram l = new Ngram(kmers, freq);
				query.put(kmers, l);
			}

		}	// end of while()
		Language language = db.getLanguage(query);
		System.out.println("appears to be written in :" + language);
		br.close();
	}	// end of analyseQuery

}	// end of Parser class