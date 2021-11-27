package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Model.NotEmailAddressException;
import Model.User;

public class AccountController {

	private  List<User> accounts = new ArrayList<User>();

	public  void addAccount(User obj)  {
		if(obj!=null) {
			accounts.add(obj);
			System.out.println("You register successfuly!For log in press 2.");
		}
	}

	public  String logIn(String email, String password) {
		for (User temp : accounts) {
			if (temp.getEmail().equals(email)) {
				if (temp.getPassword().equals(password)) {
					return temp.getId();
				}
			}
		}
		return "false";
	}
	

}
