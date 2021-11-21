package Model;

public class NotEmailAddressException extends Exception {
	
	public String getMessage() {
		return "Invalid email";
	}

}
