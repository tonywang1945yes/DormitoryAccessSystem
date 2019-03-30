package dao;

import entity.PassRecord;
import entity.TimeRequirement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class HibernateTest {

    @Test
    public void searchTest() {
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Calendar c = Calendar.getInstance();
        c.set(2019, 1, 22);
        TimeRequirement requirement = new TimeRequirement();
        requirement.startTime = c;

        try {
//            Query query = session.createQuery("select p from PassRecord p where p.userId = 186361 order by p.passTime DESC ");
            Query query = session.createQuery("select p from PassRecord p where p.passTime.getTime() = " + c.getTime());
            List<PassRecord> list = (List<PassRecord>) query.list();
            System.out.println(list.size());
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Test
    public void getIdListTest() {
        Configuration config = new Configuration().configure();
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Instant st = Instant.now();
            Query query = session.createQuery("select distinct p.userId from PassRecord p ");
            query.setMaxResults(100);
            List<Integer> list = query.list();
            for (Integer o : list) {
                System.out.println(o);
            }
            Instant et = Instant.now();
            Duration duration = Duration.between(st, et);
            System.out.println(duration);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void filterTest() {
        HBProbe probe = new HBProbe();
        List<Integer> list = probe.queryAllUserId();
        Calendar c = Calendar.getInstance();
        c.set(2019, 1, 22);
        TimeRequirement requirement = new TimeRequirement();
        requirement.startTime = c;
        List<Integer> result = list.stream().filter(o -> probe.judgeSpecificId(o, requirement)).collect(Collectors.toList());
        System.out.println(result.size());
    }
}
