package bl.strategy;

import entity.*;
import enums.PairStatus;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学生在某处(宿舍内或外)停留时间过久的检测类
 * 使用"模板方法"的设计模式设计，本类中实现大部分通用逻辑，部分与学生处地有关的具体逻辑交由子类实现
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/4/26
 */
public abstract class LongStayInspector {

    static final int IN = 0;
    static final int OUT = 1;
    //    TimeRequirement requirement;
    Map<String, List<PassRecord>> recordMaps; //学生id及对应的刷卡记录列表
    List<WhiteStudent> whiteList; //白名单
    List<BlackStudent> blackList; //关注名单，命名偷了懒


    public void setRecordMaps(Map<String, List<PassRecord>> recordMaps) {
        this.recordMaps = recordMaps;
    }

    public void setWhiteList(List<WhiteStudent> whiteList) {
        this.whiteList = whiteList;
    }

    public void setBlackList(List<BlackStudent> blackList) {
        this.blackList = blackList;
    }

    /**
     * 程序使用接口，传入时间要求，筛选出刷卡记录异常的学生
     *
     * @param requirement 正常时间要求，关注名单上的学生不使用此时间要求
     * @return 被怀疑的学生列表
     */
    public List<SuspectStudent> apply(TimeRequirement requirement) {
        List<SuspectStudent> res = new ArrayList<>();

        List<String> whiteIds = whiteList.stream().map(WhiteStudent::getStudentId).collect(Collectors.toList());
        List<String> blackIds = blackList.stream().map(BlackStudent::getStudentId).collect(Collectors.toList());

        res.addAll(recordMaps.entrySet()
                .stream()
                .filter(o -> !whiteIds.contains(o.getKey()) && !blackIds.contains(o.getKey())) //选出不在黑白名单上的学生
                .map(o -> judge(o.getKey(), o.getValue(), requirement))
                .collect(Collectors.toList()));

        whiteList.forEach(o -> res.add(judgeWhite(o, recordMaps.get(o.getStudentId()), requirement)));
        blackList.forEach(o -> res.add(judgeBlack(o, recordMaps.get(o.getStudentId()), requirement)));

        return res
                .stream()
                .filter(Objects::nonNull)//除掉空值
                .collect(Collectors.toList());
    }

    /**
     * 实现判断学生是否在某地停留过久的主要逻辑
     *
     * @param studentId 学生的id
     * @param records   学生刷卡记录
     * @param req       对该学生应用的时间要求
     * @return 如果学生被怀疑，封装并返回相关异常信息，否则为null
     */
    final SuspectStudent judge(String studentId, List<PassRecord> records, TimeRequirement req) {
        SuspectStudent res = new SuspectStudent();
        res.setStudentId(studentId);

        preProcess(res, records, req);
        List<TimePair> evidence = getTargetPairs(records, req);
        handleEvidence(res, evidence, req);
        postProcess(res, records, req);

        if (res.getDoubtfulRecords().size() != 0)
            return res;
        return null;

    }

    /**
     * 判断曾在白名单上的学生在白名单有效时间之后是否有异常刷卡记录
     * 对两种情况来说，目前逻辑都是选出白名单时间之后的记录当作正常学生判断，暂不允许子类重载
     *
     * @param whiteInfo 包含学生及白名单的信息
     * @param records   该学生的刷卡记录
     * @param req       对该学生应用的时间要求
     * @return 如果学生被怀疑，封装并返回相关异常信息，否则为null
     */
    private SuspectStudent judgeWhite(WhiteStudent whiteInfo, List<PassRecord> records, TimeRequirement req) {
        return judge(whiteInfo.getStudentId(), records
                .stream()
                .filter(o -> o.getPassTime().after(req.getEndTime())) //默认之处理白名单时间之后的记录
                .collect(Collectors.toList()), req);
    }

    /**
     * 判断在关注名单上的学生是否有异常刷卡记录
     *
     * @param blackInfo 包含学生及关注名单的信息
     * @param records   该学生的刷卡记录
     * @param req       对该学生应用的时间要求
     * @return 如果学生被怀疑，封装并返回相关异常信息，否则为null
     */
    private SuspectStudent judgeBlack(BlackStudent blackInfo, List<PassRecord> records, TimeRequirement req) {
        //对于该学生应用的特别时间要求
        TimeRequirement specificReq = new TimeRequirement();
        specificReq.setInterval(req.getInterval().copy());
        specificReq.setMinBreak(Duration.ofMillis(blackInfo.getMinBreak().toMillis()));
        specificReq.setLimitationToNow(Duration.ofMillis(blackInfo.getLimitToNow().toMillis()));

        Duration reqDuration = getBlackReqDuration(blackInfo, req);
        if (req.getIntervalDuration().compareTo(reqDuration) < 0)
            specificReq.setReqDuration(Duration.ofMillis(reqDuration.toMillis()));//时间区间选择最小的
        else
            specificReq.setReqDuration(Duration.ofMillis(req.getReqDuration().toMillis()));

        SuspectStudent res = judge(blackInfo.getStudentId(), records, specificReq);
        if (res != null) {
            res.setLevel(res.getLevel() + 1);
            res.setStatus(":WASINBLACKLIST");
        }
        return res;
    }

    /**
     * 交由子类实现，选择为该学生应用的时间间隔
     *
     * @param blackInfo 关注名单信息
     * @param req       正常时间要求
     * @return 将要使用的时间间隔
     */
    abstract Duration getBlackReqDuration(BlackStudent blackInfo, TimeRequirement req);

    /**
     * hook方法，根据要求得到认为异常的时间对(进-出时间对或者出-进时间对)，供judge方法使用
     *
     * @param records 某个学生的刷卡记录
     * @param req     时间要求
     * @return 异常的时间对
     */
    abstract List<TimePair> getTargetPairs(List<PassRecord> records, TimeRequirement req);

    /**
     * 实行判断之前的处理
     *
     * @param student SuspectStudent对象
     * @param records 刷卡记录
     * @param req     时间要求
     */
    abstract void preProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req);

    /**
     * 实行判断之后的处理
     *
     * @param student SuspectStudent对象
     * @param records 刷卡记录
     * @param req     时间要求
     */
    abstract void postProcess(SuspectStudent student, List<PassRecord> records, TimeRequirement req);

    /**
     * 对于异常时间对的处理，具体判断逻辑交由子类实现
     *
     * @param student  SuspectStudent对象
     * @param evidence 异常时间对
     * @param req      时间要求
     */
    abstract void handleEvidence(SuspectStudent student, List<TimePair> evidence, TimeRequirement req);

    /**
     * 得出长时间停留的时间对
     *
     * @param records        学生划卡记录
     * @param firstDirection 时间对开始时间对应的刷卡方向
     * @param minBreak       两时间最小间隔(小于该间隔会被合并)
     * @param req            时间要求
     * @return 长时间停留的时间对
     */
    final List<TimePair> getLongStayPairs(List<PassRecord> records, int firstDirection, Duration minBreak, TimeRequirement req) {
        List<TimePair> recordPairs = getInOutTimePairs(records, firstDirection, minBreak);
        List<TimePair> evidence = recordPairs.stream()
                .filter(o -> !o.getStatus().equals(PairStatus.UNCOMPLETED.name()))
                .filter(o -> req.cover(o) && o.getDuration().compareTo(req.getReqDuration()) > 0)
                .collect(Collectors.toList());
        return evidence;
    }

    /**
     * 基本方法，将学生的所有通行记录划分为时间对的列表，以便后续判断
     * 因目前策略都以时间对来进行判断，所以抽出代码作为父类基本方法使用
     *
     * @param records        某个学生的宿舍通过记录，默认以时间先后顺序排序
     * @param firstDirection 时间对第一个时间戳所对应的方向
     * @return 时间对列表，以map形式呈现是为了带上时间对的信息(是否正常)
     */
    final List<TimePair> getInOutTimePairs(List<PassRecord> records, int firstDirection, Duration minBreak) {
        List<TimePair> res = new ArrayList<>();
        if (records.size() == 0)
            return res;

        //i2在i1前一步，时间上在i1后
        Iterator<PassRecord> i1 = records.iterator();
        Iterator<PassRecord> i2 = records.iterator();
        i2.next();

        //找到第一条方向符合firstDirection的记录。可能最后为i2无next且i1指向的记录的方向仍不为fD，此情况在下方处理
        while (i2.hasNext() && !i1.next().getDirection().equals(firstDirection))
            i2.next();


        while (true) {
            TimePair tp = new TimePair();

            //最后情况
            if (!i2.hasNext()) {
                PassRecord p = i1.next();
                //如果i1所指为时间对的前一半，加入list中并标识为不完整的，否则直接退出
                if (p.getDirection().equals(firstDirection)) {
                    tp.setT1(p.getPassTime());
                    tp.setT2(null);
                    tp.setStatus(PairStatus.UNCOMPLETED.name());
                    res.add(tp);
                }
                break;
            }

            //正常情况，可以拿到时间对
            PassRecord p1 = i1.next();
            PassRecord p2 = i2.next();
            tp.setT1(p1.getPassTime());
            tp.setT2(p2.getPassTime());

            if (!p1.getDirection().equals(p2.getDirection()) && p1.getDirection().equals(firstDirection)) {

                TimePair latest = res.get(res.size() - 1);
                if (Duration.between(latest.getT2().toInstant(), tp.getT1().toInstant()).compareTo(minBreak) < 0) {
                    //如果距上一对的距离太近，归为同一对记录
                    latest.setT2(tp.getT2());
                } else {
                    //正常情况
                    tp.setStatus(PairStatus.NORMAL.name());
                    res.add(tp);
                }

            } else if (p1.getDirection().equals(p2.getDirection())) {
                //时间对不正常
                tp.setStatus(
                        p1.getDirection().equals(IN) ? PairStatus.INASOUT.name() : PairStatus.OUTASIN.name()
                );
                res.add(tp);
            } else {
                //完全反向，在与之前的记录之间的
                TimePair latest = res.get(res.size() - 1);
                if (Duration.between(latest.getT2().toInstant(), tp.getT1().toInstant()).compareTo(minBreak) < 0)
                    latest.setT2(tp.getT2());
            }

        }

        return res;
    }


}
