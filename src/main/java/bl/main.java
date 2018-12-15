package bl;

import java.security.GeneralSecurityException;
import java.util.List;


public class main {
	public static void main(String[] args) {
		StudentListGenerator slg = new StudentListGenerator();
		slg.start();
		EmailSender es = new EmailSender();
		es.start();
	}

}
