package processing.app.screen.managers;


public class Warn {
	
	private String time;
	private String message;
	private boolean send;
	
	public Warn(String time, String message) {
		setTime(time);
		setMessage(message);
		send = false;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSend() {
		return send;
	}

	public void Send() {
		if(!isSend())
			//Utils.Warn(message);
		send = true;
	}


}
