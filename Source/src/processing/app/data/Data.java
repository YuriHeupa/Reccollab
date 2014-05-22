package processing.app.data;

public class Data {
	private String node;
	private String value;

	public Data(Data data){
		this.node = data.getNode();
		this.value = data.getValue();
	}
	
	public Data(String note, String value){
		this.node = note;
		this.value = value;
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
