package dao;

import entity.IdMap;
import entity.PassRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * simple introduction
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/21
 */
@Mapper
public interface PassRecordMapper {
    List<PassRecord> getAllRecords();

    List<IdMap> getUserIdMaps();

    List<PassRecord> getRecordByDate(String date);

    List<PassRecord> getRecordWithLimit(int limit);

    int getRecordCountByDate(Date date);
}
