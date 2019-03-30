package dao;

import entity.LateQueryRequirement;
import entity.PassRecord;
import entity.TimeRequirement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HBProbe {
    final SessionFactory sessionFactory;

    public HBProbe() {
        Configuration config = new org.hibernate.cfg.Configuration().configure();
        sessionFactory = config.buildSessionFactory();
    }

    public List<Integer> queryAllUserId() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select distinct p.userId from PassRecord p");
        List<Integer> idList = query.list();
        session.close();
        return idList;
    }

    public boolean judgeSpecificId(Integer id, TimeRequirement requirement) {
        Instant st = Instant.now();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("select p from PassRecord p where p.userId =" + id);
        List<PassRecord> records = query.list();
//        session.close();
        tx.commit();
        List<PassRecord> yesterday = records
                .stream()
                .filter(o1 -> o1.getPassTime().get(Calendar.DATE) == requirement.startTime.get(Calendar.DATE))
                .collect(Collectors.toList());
        Instant et = Instant.now();
        Duration duration = Duration.between(st, et);
        System.out.println("本次判断耗时: " + duration);
        if (yesterday.size() != 0 && yesterday.get(0).getDirection().equals("1")) {
            //如果前一天有进出记录(可能因为各种原因没有记录)且最后一次刷卡记录为出去，被怀疑
            return true;
        }
        return false;
    }

    public List<Integer> queryLateReturnIds(LateQueryRequirement requirement) {
        List<Integer> res = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            /*推进策略为，每一轮筛选出[startTime, timeLimit]内的记录，判断所有在该时间段内有刷卡记录的
            学生的最后一次刷卡记录是不是为外出，如果是则被怀疑。
            */
            while (!requirement.getStartTime().after(requirement.getEndTime())) {

                //查找出需筛选时间段内的记录
                Query query = session.createQuery(
                        "select p from PassRecord p where p.passTime>= ? and p.passTime<= ?")
                        .setParameter(0, format.format(requirement.getStartTime()))
                        .setParameter(1, format.format(requirement.getTimeLimit()));
                List<PassRecord> records = query.list();

                //将通过记录按照id分组
                Map<Integer, List<PassRecord>> recordMap
                        = records
                        .stream()
                        .collect(Collectors.groupingBy(PassRecord::getUserId));

                res.addAll(
                        //从各id的记录里筛选出值得怀疑的学生
                        recordMap.entrySet()
                                .stream()
                                .parallel()
                                .filter(e -> isLateReturn(e.getValue()))
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList())
                );

                //将判断参数向后推一天，重新开始循环
                requirement.getStartTime().add(Calendar.DATE, 1);
                requirement.getTimeLimit().add(Calendar.DATE, 1);
            }
            //删掉重复的id
            res = res.stream().distinct().collect(Collectors.toList());

            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private boolean isLateReturn(List<PassRecord> records) {
        records.sort(
                (o1, o2) ->
                        o1.getPassTime().after(o2.getPassTime()) ? 1 : -1
        );
        return records.get(0).getDirection() == 1;//如果最后一次刷卡记录为出去，被怀疑
    }
}
