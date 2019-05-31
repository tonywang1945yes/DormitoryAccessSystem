package enums;

/**
 * 学生被怀疑的理由
 *
 * @author Nosolution
 * @version 1.0
 * @since 2019/5/6
 */
public enum StudentStatus {
    LONG_OUT_WITH_CONFUSION("存在长时间在外情况，但是存在混淆记录"),
    LONG_OUT("存在长时间在外情况"),
    STILL_OUT("到目前仍在外未回"),
    WATCHED("在关注名单上"),
    LONG_IN_WITH_CONFUSION("长时间待在宿舍，但是存在混淆记录"),
    LONG_IN("长时间呆在宿舍"),
    NIGHT_ABSENCE("昨夜旷寝"),
    WITH_CONFUSION("存在混淆记录"),
    ABOUT_HOLIDAY("与假期有关");

    private String msg;

    public String getMsg() {
        return this.msg;
    }

    StudentStatus(String msg) {

        this.msg = msg;
    }

    public static StudentStatus getNameByMsg(String msg) {
        for (StudentStatus s : StudentStatus.values()) {
            if (msg.equals(s.getMsg()))
                return s;
        }
        return null;
    }

}
