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
    public List<PassRecord> getAllRecords();

    public List<IdMap> getUserIdMaps();

    public List<PassRecord> getRecordByDate(String date);

    public List<PassRecord> getRecordWithLimit(int limit);

    public int getRecordCountByDate(Date date);
}
