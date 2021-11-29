package Model;

public class NotEmailAddressException extends Exception {
	String wrongEmail;

	public NotEmailAddressException(String wrongEmail) {
		this.wrongEmail = wrongEmail;
	}

	public String getMessage() {
		return "Invalid email " + wrongEmail;
	}

}
