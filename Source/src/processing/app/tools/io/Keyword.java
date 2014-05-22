package processing.app.tools.io;

public class Keyword {

	private String word;
	private int hits;
	
	public Keyword(String text) {
		word = text;
		hits = 1;
	}

	public String getKeyword() {
		return word;
	}

	public void setKeyword(String word) {
		this.word = word;
	}

	public int getHits() {
		return hits;
	}

	public void Hit() {
		this.hits += 1;
	}
}
