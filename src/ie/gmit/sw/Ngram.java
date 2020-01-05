package ie.gmit.sw;

public class Ngram implements Comparable<Ngram> {
	//	Variable declaration
	private int kmer;
	private int frequency;
	private int rank;
	
	//	Constructor
	public Ngram(int kmer, int frequency) {
		super();
		this.kmer = kmer;
		this.frequency = frequency;
	}
	
	//	Getters and Setters
	public int getKmer() {
		return kmer;
	}

	public void setKmer(int kmer) {
		this.kmer = kmer;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	//	Uses implemented Comparable to compare 1 language entry (kmer/ngram) to another
	//	Compares by their frequency
	@Override
	public int compareTo(Ngram next) {
		return - Integer.compare(frequency, next.getFrequency());
	}
	
	@Override
	public String toString() {
		return "[" + kmer + "/" + frequency + "/" + rank + "]";
	}
}