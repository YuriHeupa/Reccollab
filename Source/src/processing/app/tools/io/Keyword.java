package processing.app.tools.io;

import processing.app.Utils;

public class Keyword {

	private String word;
	private String handleTime;
	
	public Keyword(String text) {
		word = text;
		handleTime = Utils.dateFormat();
	}

	public String getKeyword() {
		return word;
	}

	public void setKeyword(String word) {
		this.word = word;
	}

	public String getHandleTime() {
		return handleTime;
	}
}
