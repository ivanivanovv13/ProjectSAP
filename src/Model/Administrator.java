package Model;

import java.util.UUID;

public class Administrator {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	
	public Administrator(String firstName, String lastName, String email, String phoneNum, String password)
			throws NotEmailAddressException {
		if (MailValidator.checkEmail(email)) {
			this.email = email;
		} else {
			throw new NotEmailAddressException(email);
		}
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.password = password;

	}

	public Administrator(String id, String email, String password, String firstName, String lastName, String phoneNum)
			throws NotEmailAddressException {
		if (MailValidator.checkEmail(email)) {
			this.email = email;
		} else {
			throw new NotEmailAddressException(email);
		}
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNum = phoneNum;
		this.password = password;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
