package Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailValidator {
	public static final Pattern VALID_MAIL = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");;

	public static boolean checkEmail(String email) {
		Matcher matcher = VALID_MAIL.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
}
