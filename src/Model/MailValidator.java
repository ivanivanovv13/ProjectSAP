package Model;

import java.util.regex.Pattern;

public interface MailValidator {
	public static final Pattern VALID_MAIL = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

	+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");;

	public boolean checkEmail(String email);
}
