package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 怀疑列表
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
@Data
public class SuspectStudent {
    String studentId;
    List<TimePair> doubtfulRecords = new ArrayList<>();
    String status = "";
    Integer level = 0;
}
