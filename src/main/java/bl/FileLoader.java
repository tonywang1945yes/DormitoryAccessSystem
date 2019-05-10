package bl;

import entity.BlackStudent;
import entity.Tutor;
import entity.WhiteStudent;
import exception.excelException.ExcelException;

import java.util.List;
import java.util.Map;

/**
 * 从使用者提供的路径搜索并装载需要的白名单，关注名单，以及教师与学生的匹配
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/10
 */
public abstract class FileLoader {

    public abstract Map<Tutor, List<String>> getTutorStudentMaps(String filePath, String fileName) throws ExcelException;

    public abstract List<WhiteStudent> getWhiteStudentList(String filePath, String fileName) throws ExcelException;

    public abstract List<BlackStudent> getBlackStudentList(String filePath, String fileName) throws ExcelException;

    public abstract Map<String, String> getAppLog();

}
