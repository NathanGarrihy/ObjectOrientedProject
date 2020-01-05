package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Database {
	//	Variable declaration
	//	Map for 235 languages, maps to ngram's and frequency
	private Map<Language, Map<Integer, Ngram>> db = new TreeMap<>();
	
	//	Adds kmer which is received to its Map (in form of the string's hashcode)
	public void add(CharSequence s, Language lang) {
		int kmer = s.hashCode();
		Map<Integer, Ngram> langDb = getLanguageEntries(lang);
		
		int frequency = 1;
		if (langDb.containsKey(kmer)) {
			frequency += langDb.get(kmer).getFrequency();
		}
		langDb.put(kmer, new Ngram(kmer, frequency));
		
	}
	
	//	Returns the Map, or Creates it if it doesn't exist
	private Map<Integer, Ngram> getLanguageEntries(Language lang){
		Map<Integer, Ngram> langDb = null; 
		if (db.containsKey(lang)) {
			langDb = db.get(lang);
		}else {
			langDb = new TreeMap<Integer, Ngram>();
			db.put(lang, langDb);
		}
		return langDb;
	}
	
	//	Cuts number down (to the 300 top entries)
	public void resize(int max) {
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			Map<Integer, Ngram> top = getTop(max, lang);
			db.put(lang, top);
		}
	}
	
	//	Gets the top 300 kmers
	public Map<Integer, Ngram> getTop(int max, Language lang) {
		Map<Integer, Ngram> temp = new TreeMap<>();
		List<Ngram> les = new ArrayList<>(db.get(lang).values());
		Collections.sort(les);
		
		int rank = 1;
		for (Ngram le : les) {
			le.setRank(rank);
			temp.put(le.getKmer(), le);			
			if (rank == max) break;
			rank++;
		}
		
		return temp;
	}
	
	//	Compares query vs database of languages,
	public Language getLanguage(Map<Integer, Ngram> query) {
		TreeSet<OutOfPlaceMetric> oopm = new TreeSet<>();
		
		Set<Language> langs = db.keySet();
		for (Language lang : langs) {
			oopm.add(new OutOfPlaceMetric(lang, getOutOfPlaceDistance(query, db.get(lang))));
		}
		return oopm.first().getLanguage();
	}
	
	//	Compares query map with subject map. 300 entries
	private int getOutOfPlaceDistance(Map<Integer, Ngram> query, Map<Integer, Ngram> subject) {
		int distance = 0;
		
		Set<Ngram> les = new TreeSet<>(query.values());		
		for (Ngram q : les) {
			Ngram s = subject.get(q.getKmer());
			if (s == null) {
				distance += subject.size() + 1;
			}else {
				distance += s.getRank() - q.getRank();
			}
		}
		return distance;
	}
	
	//	Out of place metric class
	private class OutOfPlaceMetric implements Comparable<OutOfPlaceMetric>{
		//		Variable declaration
		private Language lang;
		private int distance;
		
		//		Constructor, Getters and Setters
		public OutOfPlaceMetric(Language lang, int distance) {
			super();
			this.lang = lang;
			this.distance = distance;
		}

		public Language getLanguage() {
			return lang;
		}

		public int getAbsoluteDistance() {
			return Math.abs(distance);
		}
		
		//		Compulsory method overriding for implementing Comparable
		@Override
		public int compareTo(OutOfPlaceMetric o) {
			return Integer.compare(this.getAbsoluteDistance(), o.getAbsoluteDistance());
		}

		@Override
		public String toString() {
			return "[lang=" + lang + ", distance=" + getAbsoluteDistance() + "]";
		}
		
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		int langCount = 0;
		int kmerCount = 0;
		Set<Language> keys = db.keySet();
		for (Language lang : keys) {
			langCount++;
			sb.append(lang.name() + "->\n");
			 
			 Collection<Ngram> m = new TreeSet<>(db.get(lang).values());
			 kmerCount += m.size();
			 for (Ngram le : m) {
				 sb.append("\t" + le + "\n");
			 }
		}
		sb.append(kmerCount + " total k-mers in " + langCount + " languages"); 
		return sb.toString();
	}
}