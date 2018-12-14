package bl;

import java.security.GeneralSecurityException;
import java.util.List;

import util.excelUtil.People;
import util.mailUtil.*;

import util.excelUtil.ExcelReader;
import util.excelUtil.Tutor;

public class main {
	public static void main(String[] args) {
		StudentListGenerator slg = new StudentListGenerator("C:\\Users\\12509\\Desktop\\wrh\\testDAS.xlsx");
		slg.start();
		EmailSender es = new EmailSender();
		es.start();
	}

}
