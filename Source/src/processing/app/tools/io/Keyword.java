package processing.app.tools.io;


public class Keyword {

	private String word;
	private String handleTime;
	
	public Keyword(String text, String handleTime) {
		word = text;
		this.handleTime = handleTime;
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

	public String getInfo() {
		return "[" +getHandleTime() + "] - " + getKeyword();
	}
}
