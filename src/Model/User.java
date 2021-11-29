package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class User {

	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String password;
	public List<Item> favouriteItems= new ArrayList<Item>();

	public User(String firstName, String lastName, String email, String phoneNum, String password)
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
	
	public User(String id,String email,String password,String firstName, String lastName,  String phoneNum)
			throws NotEmailAddressException {
		if (MailValidator.checkEmail(email)) {
			this.email = email;
		} else {
			throw new NotEmailAddressException(email);
		}
		this.id =id;
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

	

}
