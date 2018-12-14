package bl;

import java.security.GeneralSecurityException;
import java.util.List;
import util.mailUtil.*;

import util.excelUtil.ExcelReader;
import util.excelUtil.Tutor;

public class main {
	public static void main(String[] args) {
		List<Tutor> a = ExcelReader.readSimpleExcel("C:\\Users\\12509\\Desktop\\wrh\\haha.xlsx", "Sheet1");
		for(int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i).emailAddress);
		}

			try {
				Mail.sendMailWithAttachment("1250925329@qq.com", "nrmwztvhmdlsgfdi", "1250925329@qq.com", "测试", "C:\\Users\\12509\\Desktop\\wrh\\mail\\src\\mail\\main.java");
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
