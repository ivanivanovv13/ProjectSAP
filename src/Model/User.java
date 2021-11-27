package Model;

import java.util.UUID;
import java.util.regex.Matcher;

public class User implements MailValidator {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;

	public User(String firstName, String lastName, String email, String phoneNum, String password)
			throws NotEmailAddressException {
		if (checkEmail(email)) {
			this.email = email;
		} else {
			throw new NotEmailAddressException();
		}
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.password = password;

	}

	public User() {
		firstName = "";
		lastName = "";
		email = "";
		phoneNum = "";
		password = "";
	}

	public String getPassword() {
		return password;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public boolean checkEmail(String email) {
		Matcher matcher = VALID_MAIL.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

}
