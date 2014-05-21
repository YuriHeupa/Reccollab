package processing.app.tools.encoder;

import processing.core.PImage;


public abstract class PIPImage {

	private PImage source;
	private PImage pip;
	private String name;
	
	public PIPImage (PImage source, PImage pip, String name) {
		this.source = source;
		this.pip = pip;
		this.name = name;
		//time = Utils.revertDateFormat(name.substring(8, name.length()-4)).getTime();
	}

	public PImage getSource() {
		return source;
	}

	public void setSource(PImage source) {
		this.source = source;
	}

	public PImage getPip() {
		return pip;
	}

	public void setPip(PImage pip) {
		this.pip = pip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
